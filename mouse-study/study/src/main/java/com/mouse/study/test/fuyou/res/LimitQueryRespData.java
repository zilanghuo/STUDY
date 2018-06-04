package com.mouse.study.test.fuyou.res;

import com.mouse.study.test.fuyou.BankLimit;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

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

    private List<BankLimit> bankLimits;

    private String sign;

    @XmlElement(name = "BANKLMT")
    public List<BankLimit> getBankLimits() {
        return bankLimits;
    }

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

    @XmlElement(name = "SIGN")
    public String getSign() {
        return sign;
    }
}
