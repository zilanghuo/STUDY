package com.mouse.study.test.fuyou.res;

import com.fuiou.util.MD5;
import com.mouse.study.test.fuyou.Constants;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/6/21
 * use:
 */
@Setter
@XmlRootElement(name = "FM")
public class VerifyCardRespData {

    private String rcd;

    private String rDesc;

    private String oSsn;

    private String cardNo;

    private String mchntCd;

    private String ver;

    private String cnm;

    private String insCd;

    private String sign;

    @XmlElement(name = "Rcd")
    public String getRcd() {
        return rcd;
    }

    @XmlElement(name = "RDesc")
    public String getrDesc() {
        return rDesc;
    }

    @XmlElement(name = "OSsn")
    public String getoSsn() {
        return oSsn;
    }

    @XmlElement(name = "CardNo")
    public String getCardNo() {
        return cardNo;
    }

    @XmlElement(name = "MchntCd")
    public String getMchntCd() {
        return mchntCd;
    }

    @XmlElement(name = "Ver")
    public String getVer() {
        return ver;
    }

    @XmlElement(name = "Cnm")
    public String getCnm() {
        return cnm;
    }

    @XmlElement(name = "InsCd")
    public String getInsCd() {
        return insCd;
    }

    @XmlElement(name = "Sign")
    public String getSign() {
        return sign;
    }

    public String sign() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.rcd).append("|")
                .append(this.oSsn).append("|")
                .append(this.cardNo).append("|")
                .append(this.mchntCd).append("|")
                .append(this.ver).append("|")
                .append(Constants.API_MCHNT_KEY_2);
        return MD5.MD5Encode(buffer.toString());
    }
}
