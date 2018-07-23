package com.mouse.study.test.fuyou.res;

import com.mouse.study.test.fuyou.Constants;
import com.mouse.study.utils.MD5Util;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/5/31
 * use:
 */
@Setter
@XmlRootElement(name = "FM")
public class QueryCardRespData {

    private String rcd;

    private String rDesc;

    private String oSsn;

    private String cardNo;

    private String mchntCd;

    private String ver;

    private String cnm;

    private String insCd;

    private String signTp;

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

    @XmlElement(name = "SignTp")
    public String getSignTp() {
        return signTp;
    }

    @XmlElement(name = "Sign")
    public String getSign() {
        return sign;
    }

    public String sign() {
        StringBuffer temp = new StringBuffer();
        temp.append(this.rcd).append("|")
                .append(this.oSsn).append("|")
                .append(this.cardNo).append("|")
                .append(this.mchntCd).append("|")
                .append(this.ver).append("|")
                .append(Constants.API_MCHNT_KEY);
        return MD5Util.MD5Encode(temp.toString());
    }
}
