package com.mouse.study.test.fuyou.res;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/6/4
 * use:
 */
@Setter
@XmlRootElement(name = "RESPONSE")
public class LimitQueryRespData {

    private String mchntCd;

    private String responseCode;

    private String responseMsg;

    private String insCd;

    private String cardType;

    private Integer amtLimitTime;

    private Integer amtLimitTimeLow;

    private Integer amtLimitDay;

    private Integer amtLimitMonth;

    private String sign;

    @XmlElement(name = "MCHNTCD")
    public String getMchntCd() {
        return mchntCd;
    }

    @XmlElement(name = "RESPONSECODE")
    public String getResponseCode() {
        return responseCode;
    }

    @XmlElement(name = "RESPONSEMSG")
    public String getResponseMsg() {
        return responseMsg;
    }

    @XmlElement(name = "Sign")
    public String getInsCd() {
        return insCd;
    }

    @XmlElement(name = "Sign")
    public String getCardType() {
        return cardType;
    }

    @XmlElement(name = "Sign")
    public Integer getAmtLimitTime() {
        return amtLimitTime;
    }

    @XmlElement(name = "Sign")
    public Integer getAmtLimitTimeLow() {
        return amtLimitTimeLow;
    }

    @XmlElement(name = "Sign")
    public Integer getAmtLimitDay() {
        return amtLimitDay;
    }

    @XmlElement(name = "Sign")
    public Integer getAmtLimitMonth() {
        return amtLimitMonth;
    }

    @XmlElement(name = "SIGN")
    public String getSign() {
        return sign;
    }
}
