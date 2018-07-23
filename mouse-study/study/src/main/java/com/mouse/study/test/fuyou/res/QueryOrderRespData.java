package com.mouse.study.test.fuyou.res;

import com.mouse.study.test.fuyou.Constants;
import com.mouse.study.test.fuyou.XmlBeanUtils;
import com.mouse.study.utils.MD5Util;
import lombok.Setter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/5/30
 * use:
 */
@XmlRootElement(name = "RESPONSE")
@Setter
public class QueryOrderRespData {

    private String version;

    private String responseCode;

    private String responseMsg;

    private String mchntOrderId;

    private String amt;

    private String orderDate;

    private String bankCard;

    private String sign;

    @XmlElement(name = "VERSION")
    public String getVersion() {
        return version;
    }

    @XmlElement(name = "RESPONSECODE")
    public String getResponseCode() {
        return responseCode;
    }

    @XmlElement(name = "MCHNTORDERID")
    public String getMchntOrderId() {
        return mchntOrderId;
    }

    @XmlElement(name = "AMT")
    public String getAmt() {
        return amt;
    }

    @XmlElement(name = "ORDERDATE")
    public String getOrderDate() {
        return orderDate;
    }

    @XmlElement(name = "BANKCARD")
    public String getBankCard() {
        return bankCard;
    }

    @XmlElement(name = "SIGN")
    public String getSign() {
        return sign;
    }

    @XmlElement(name = "RESPONSEMSG")
    public String getResponseMsg() {
        return responseMsg;
    }

    public String buildXml() throws JAXBException {
        this.sign = sign();
        return XmlBeanUtils.convertBean2Xml(this, "UTF-8", false);
    }

    public String sign() {
        StringBuffer temp = new StringBuffer();
        temp.append(this.version).append("|")
                .append(this.responseCode).append("|")
                .append(this.responseMsg).append("|")
                .append(this.mchntOrderId).append("|")
                .append(Constants.API_MCHNT_KEY);
        return MD5Util.MD5Encode(temp.toString());
    }

}
