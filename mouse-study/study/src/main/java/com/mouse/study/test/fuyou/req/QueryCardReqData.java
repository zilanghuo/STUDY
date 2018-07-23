package com.mouse.study.test.fuyou.req;

import com.mouse.study.test.fuyou.Constants;
import com.mouse.study.test.fuyou.XmlBeanUtils;
import com.mouse.study.utils.MD5Util;
import lombok.Setter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/5/31
 * use:
 */
@Setter
@XmlRootElement(name = "FM")
public class QueryCardReqData {

    private String mchntCd;

    private String ver;

    private String oSsn;

    private String ono;

    private String onm;

    private String oCerTp;

    private String oCerNo;

    private String mno;

    private String signTp;

    private String sign;

    @XmlElement(name = "MchntCd")
    public String getMchntCd() {
        return mchntCd;
    }

    @XmlElement(name = "Ver")
    public String getVer() {
        return ver;
    }

    @XmlElement(name = "OSsn")
    public String getoSsn() {
        return oSsn;
    }

    @XmlElement(name = "Ono")
    public String getOno() {
        return ono;
    }

    @XmlElement(name = "Onm")
    public String getOnm() {
        return onm;
    }

    @XmlElement(name = "OCerTp")
    public String getoCerTp() {
        return oCerTp;
    }

    @XmlElement(name = "OCerNo")
    public String getoCerNo() {
        return oCerNo;
    }

    @XmlElement(name = "Mno")
    public String getMno() {
        return mno;
    }

    @XmlElement(name = "SignTp")
    public String getSignTp() {
        return signTp;
    }

    @XmlElement(name = "Sign")
    public String getSign() {
        return sign;
    }

    public String buildXml() throws JAXBException {
        StringBuffer temp = new StringBuffer();
        temp.append(this.mchntCd).append("|")
                .append(this.ver).append("|")
                .append(this.oSsn).append("|")
                .append(this.ono).append("|")
                .append(this.oCerTp).append("|")
                .append(this.oCerNo).append("|")
                .append(Constants.API_MCHNT_KEY);
        System.out.println("验签明文：" + temp.toString());
        this.sign = MD5Util.MD5Encode(temp.toString());

        return XmlBeanUtils.convertBean2Xml(this, "UTF-8", false);
    }

}
