package com.mouse.study.test.fuyou.res;

import com.mouse.study.test.fuyou.Constants;
import com.mouse.study.utils.MD5Util;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/5/29
 * use:
 */
@Setter
@XmlRootElement(name = "RESPONSE")
public class DisableOrderRespData {

    private String responseCode;

    private String responseMsg;

    private String mchntCd;

    private String mchntOrderId;

    private String orderId;

    private Integer amt;

    private String sign;

    public String sign() {
        StringBuffer temp = new StringBuffer();
        temp.append(this.responseCode).append("|")
                .append(this.mchntCd).append("|")
                .append(this.mchntOrderId).append("|")
                .append(this.orderId).append("|")
                .append(this.amt).append("|")
                .append(Constants.API_MCHNT_KEY);
        return MD5Util.MD5Encode(temp.toString());
    }

    @XmlElement(name = "RESPONSECODE")
    public String getResponseCode() {
        return responseCode;
    }

    @XmlElement(name = "RESPONSEMSG")
    public String getResponseMsg() {
        return responseMsg;
    }

    @XmlElement(name = "MCHNTCD")
    public String getMchntCd() {
        return mchntCd;
    }

    @XmlElement(name = "MCHNTORDERID")
    public String getMchntOrderId() {
        return mchntOrderId;
    }

    @XmlElement(name = "ORDERID")
    public String getOrderId() {
        return orderId;
    }

    @XmlElement(name = "AMT")
    public Integer getAmt() {
        return amt;
    }

    @XmlElement(name = "SIGN")
    public String getSign() {
        return sign;
    }
}
