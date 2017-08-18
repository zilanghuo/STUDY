package com.mouse.study.test.growing;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * Created by lwf on 2017/8/18.
 * use to do:
 * AI   b93a6913b8d3edb9
 *公钥  b8t77weRaMeDruMAtrAhUPU8AjaphaVe
 *私钥  BesparUPrayegEd4faReTAVUjuqAka7u
 *
 * 项目ID：b93a6913b8d3edb9
 * 17bde4522a25ff50b95050cffb81ecf5a8587913c4efc3da1eb8d9b3508e0681
 *
 * uid:1P6VAXPd
 */
public class Test {

    public static void main(String[] args) throws Exception{

        System.out.println(new Date().getTime());
   /*     HttpUtils httpUtils = new HttpUtils();
        String url = "https://www.growingio.com/insights/:ai/:date.json";
        Map parameterMap  = new HashMap();
        parameterMap.put("ai","b93a6913b8d3edb9");
        parameterMap.put("date","2017081708");
        parameterMap.put("expire","2");

        StringBuffer stringBuffer = httpUtils.URLGet(url, parameterMap);
        System.out.println(stringBuffer);
*/

    }




    public static String authToken(String secret, String project, String ai, Long tm) throws Exception {
        String message = "POST\n/auth/token\nproject="+project+"&ai="+ai+"&tm="+tm;
        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signature = hmac.doFinal(message.getBytes("UTF-8"));
        return Hex.encodeHexString(signature);
    }
}
