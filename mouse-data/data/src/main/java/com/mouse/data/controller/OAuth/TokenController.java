package com.mouse.data.controller.OAuth;

import com.mouse.data.common.OAuth.ConstantKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Irving on 2014/11/22.
 */
@Slf4j
@RestController
@Scope("singleton")
@RequestMapping("/oauth2")
public class TokenController {

    private Cache cache ;
    @Autowired
    public TokenController(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("oauth2-cache");
    }

    /**
     * 认证服务器申请令牌(AccessToken) [验证client_id、client_secret、auth code的正确性或更新令牌 refresh_token]
     * @param request
     * @param response
     * @return
     * @url http://localhost:8080/oauth2/access_token?client_id={AppKey}&client_secret={AppSecret}&grant_type=authorization_code&redirect_uri={YourSiteUrl}&code={code}
     */
    @RequestMapping(value = "/access_token",method = RequestMethod.POST)
    public void access_token(HttpServletRequest request, HttpServletResponse response)
            throws IOException, OAuthSystemException {
            PrintWriter out = null;
            OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        try {
            out = response.getWriter();
            //构建oauth2请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
            //验证redirecturl格式是否合法 (8080端口测试)
            if (!oauthRequest.getRedirectURI().contains(":8080")&&!Pattern.compile("^[a-zA-Z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\s*)?$").matcher(oauthRequest.getRedirectURI()).matches()) {
                OAuthResponse oauthResponse = OAuthASResponse
                                              .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                              .setError(OAuthError.CodeResponse.INVALID_REQUEST)
                                              .setErrorDescription(OAuthError.OAUTH_ERROR_URI)
                                              .buildJSONMessage();
                out.write(oauthResponse.getBody());
                out.flush();
                out.close();
                return;
            }
            //验证appkey是否正确
            if (!validateOAuth2AppKey(oauthRequest)){
                OAuthResponse oauthResponse = OAuthASResponse
                                              .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                              .setError(OAuthError.CodeResponse.ACCESS_DENIED)
                                              .setErrorDescription(OAuthError.CodeResponse.UNAUTHORIZED_CLIENT)
                                              .buildJSONMessage();
                out.write(oauthResponse.getBody());
                out.flush();
                out.close();
                return;
            }
            //验证客户端安全AppSecret是否正确
            if (!validateOAuth2AppSecret(oauthRequest)) {
                OAuthResponse oauthResponse = OAuthASResponse
                                              .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                              .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                                              .setErrorDescription(ConstantKey.INVALID_CLIENT_SECRET)
                                              .buildJSONMessage();
                out.write(oauthResponse.getBody());
                out.flush();
                out.close();
                return;
            }
            String authzCode = oauthRequest.getCode();
            //验证AUTHORIZATION_CODE , 其他的还有PASSWORD 或 REFRESH_TOKEN (考虑到更新令牌的问题，在做修改)
            if (GrantType.AUTHORIZATION_CODE.name().equalsIgnoreCase(oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE))) {
                if (cache.get(authzCode) == null) {
                    OAuthResponse oauthResponse = OAuthASResponse
                                                  .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                                  .setError(OAuthError.TokenResponse.INVALID_GRANT)
                                                  .setErrorDescription(ConstantKey.INVALID_CLIENT_GRANT)
                                                  .buildJSONMessage();
                    out.write(oauthResponse.getBody());
                    out.flush();
                    out.close();
                    return;
                }
            }
            //生成token
            final String accessToken = oauthIssuerImpl.accessToken();
            String refreshToken = oauthIssuerImpl.refreshToken();
            //cache.put(accessToken,cache.get(authzCode).get());
            cache.put(refreshToken,accessToken);
            log.info("accessToken : "+accessToken +"  refreshToken: "+refreshToken);
            //清除授权码 确保一个code只能使用一次
            cache.evict(authzCode);
            //构建oauth2授权返回信息
            OAuthResponse oauthResponse = OAuthASResponse
                                          .tokenResponse(HttpServletResponse.SC_OK)
                                          .setAccessToken(accessToken)
                                          .setExpiresIn("3600")
                                          .setRefreshToken(refreshToken)
                                          .buildJSONMessage();
            response.setStatus(oauthResponse.getResponseStatus());
            out.print(oauthResponse.getBody());
            out.flush();
            out.close();
        } catch(OAuthProblemException ex) {
            OAuthResponse oauthResponse = OAuthResponse
                                          .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                                          .error(ex)
                                          .buildJSONMessage();
            response.setStatus(oauthResponse.getResponseStatus());
            out.print(oauthResponse.getBody());
            out.flush();
            out.close();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        finally
        {
            if (null != out){ out.close();}
        }
    }


    /**
     * 刷新令牌
     * @param request
     * @param response
     * @throws IOException
     * @throws OAuthSystemException
     * @url http://localhost:8080/oauth2/refresh_token?client_id={AppKey}&grant_type=refresh_token&refresh_token={refresh_token}
     */
    @RequestMapping(value = "/refresh_token",method = RequestMethod.POST)
    public void refresh_token(HttpServletRequest request, HttpServletResponse response)
            throws IOException, OAuthSystemException {
        PrintWriter out = null;
        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        try {
            out = response.getWriter();
            //构建oauth2请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
            //验证appkey是否正确
            if (!validateOAuth2AppKey(oauthRequest)){
                OAuthResponse oauthResponse = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setError(OAuthError.CodeResponse.ACCESS_DENIED)
                        .setErrorDescription(OAuthError.CodeResponse.UNAUTHORIZED_CLIENT)
                        .buildJSONMessage();
                out.write(oauthResponse.getBody());
                out.flush();
                out.close();
                return;
            }
            //验证是否是refresh_token
            if (GrantType.REFRESH_TOKEN.name().equalsIgnoreCase(oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE))) {
                OAuthResponse oauthResponse = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setError(OAuthError.TokenResponse.INVALID_GRANT)
                        .setErrorDescription(ConstantKey.INVALID_CLIENT_GRANT)
                        .buildJSONMessage();
                out.write(oauthResponse.getBody());
                out.flush();
                out.close();
                return;
            }
            /*
            * 刷新access_token有效期
             access_token是调用授权关系接口的调用凭证，由于access_token有效期（目前为2个小时）较短，当access_token超时后，可以使用refresh_token进行刷新，access_token刷新结果有两种：
             1. 若access_token已超时，那么进行refresh_token会获取一个新的access_token，新的超时时间；
             2. 若access_token未超时，那么进行refresh_token不会改变access_token，但超时时间会刷新，相当于续期access_token。
             refresh_token拥有较长的有效期（30天），当refresh_token失效的后，需要用户重新授权。
            * */
            Object cache_refreshToken=cache.get(oauthRequest.getRefreshToken());
            //access_token已超时
            if (cache_refreshToken == null) {
                //生成token
                final String access_Token = oauthIssuerImpl.accessToken();
                String refresh_Token = oauthIssuerImpl.refreshToken();
                cache.put(refresh_Token,access_Token);
                log.info("access_Token : "+access_Token +"  refresh_Token: "+refresh_Token);
                //构建oauth2授权返回信息
                OAuthResponse oauthResponse = OAuthASResponse
                        .tokenResponse(HttpServletResponse.SC_OK)
                        .setAccessToken(access_Token)
                        .setExpiresIn("3600")
                        .setRefreshToken(refresh_Token)
                        .buildJSONMessage();
                response.setStatus(oauthResponse.getResponseStatus());
                out.print(oauthResponse.getBody());
                out.flush();
                out.close();
                return;
            }
            //access_token未超时
            cache.put(oauthRequest.getRefreshToken(),cache_refreshToken.toString());
            //构建oauth2授权返回信息
            OAuthResponse oauthResponse = OAuthASResponse
                    .tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(cache_refreshToken.toString())
                    .setExpiresIn("3600")
                    .setRefreshToken(oauthRequest.getRefreshToken())
                    .buildJSONMessage();
            response.setStatus(oauthResponse.getResponseStatus());
            out.print(oauthResponse.getBody());
            out.flush();
            out.close();
        } catch(OAuthProblemException ex) {
            OAuthResponse oauthResponse = OAuthResponse
                    .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .error(ex)
                    .buildJSONMessage();
            response.setStatus(oauthResponse.getResponseStatus());
            out.print(oauthResponse.getBody());
            out.flush();
            out.close();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        finally
        {
            if (null != out){ out.close();}
        }
    }


    /**
     * 验证ClientID 是否正确
     * @param oauthRequest
     * @return
     */
    public boolean validateOAuth2AppKey(OAuthTokenRequest oauthRequest) {
        //客户端Appkey
        ArrayList arrayKeys = new  ArrayList();
        arrayKeys.add("fbed1d1b4b1449daa4bc49397cbe2350");
        arrayKeys.add("a85b033590714fafb20db1d11aed5497");
        arrayKeys.add("d23e06a97e2d4887b504d2c6fdf42c0b");
        return arrayKeys.contains(oauthRequest.getClientId());
    }


    /**
     * 验证AppSecret 是否正确
     * @param oauthRequest
     * @return
     */
    public boolean validateOAuth2AppSecret(OAuthTokenRequest oauthRequest) {
        //客户端AppSecret
        ArrayList arrayKeys = new  ArrayList();
        arrayKeys.add("fbed1d1b4b1449daa4bc49397cbe2350");
        arrayKeys.add("a85b033590714fafb20db1d11aed5497");
        arrayKeys.add("d23e06a97e2d4887b504d2c6fdf42c0b");
        return arrayKeys.contains(oauthRequest.getClientSecret());
    }
}