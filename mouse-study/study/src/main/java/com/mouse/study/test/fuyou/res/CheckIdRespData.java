package com.mouse.study.test.fuyou.res;

import com.fuiou.util.MD5;
import com.mouse.study.test.fuyou.Constants;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 身份证验证返回结果
 * 
 * @author xiaohao@fuiou.com
 *
 */
@XmlRootElement (name = "RESPONSE")
public class CheckIdRespData
{
	private String	version="";

	private String	responseCode="";

	private String	responseMsg="";

	private String	mchntOrderid="";
	
	private String userId="";

	private String	sign="";

	public String getVersion()
	{
		return version;
	}

	@XmlElement (name = "VERSION")
	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getResponseCode()
	{
		return responseCode;
	}

	@XmlElement (name = "RESPONSECODE")
	public void setResponseCode(String responseCode)
	{
		this.responseCode = responseCode;
	}

	public String getResponseMsg()
	{
		return responseMsg;
	}

	@XmlElement (name = "RESPONSEMSG")
	public void setResponseMsg(String responseMsg)
	{
		this.responseMsg = responseMsg;
	}

	public String getUserId()
	{
		return userId;
	}

	@XmlElement (name = "USERID")
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getMchntOrderid()
	{
		return mchntOrderid;
	}

	@XmlElement (name = "MCHNTORDERID")
	public void setMchntOrderid(String mchntOrderid)
	{
		this.mchntOrderid = mchntOrderid;
	}

	public String getSign()
	{
		return sign;
	}

	@XmlElement (name = "SIGN")
	public void setSign(String sign)
	{
		this.sign = sign;
	}

	/**
	 * 校验签名
	 * @throws Exception 
	 */
	public void verifySign(boolean isY) throws Exception
	{
		StringBuffer temp = new StringBuffer();
		temp.append(this.version).append("|").append(this.responseCode).append("|").append(this.responseMsg)
				.append("|").append(this.mchntOrderid);
		if(isY)
		{
			temp.append("|").append(this.userId);
		}
		temp.append("|").append(Constants.API_MCHNT_KEY);
		System.out.println("返回结果签名明文："+temp.toString());
		if(!MD5.MD5Encode(temp.toString()).equals(this.sign))
		{
			throw new Exception("返回结果验签失败~");
		}
	}
}
