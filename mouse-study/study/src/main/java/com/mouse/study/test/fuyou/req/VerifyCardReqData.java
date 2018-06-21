package com.mouse.study.test.fuyou.req;

import com.fuiou.util.MD5;
import com.mouse.study.test.fuyou.Constants;
import com.mouse.study.test.fuyou.XmlBeanUtils;
import lombok.Setter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/6/21
 * use:
 */
@Setter
@XmlRootElement(name = "FM")
public class VerifyCardReqData {

    private String mchntCd;

    private String ver;

    private String oSsn;

    private String cardNo;

    private String cardName;

    private String cardType;

    private String idCardNo;

    private String telNo;

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
    public String getCardNo() {
        return cardNo;
    }

    @XmlElement(name = "Onm")
    public String getCardName() {
        return cardName;
    }

    @XmlElement(name = "OCerTp")
    public String getCardType() {
        return cardType;
    }

    @XmlElement(name = "OCerNo")
    public String getIdCardNo() {
        return idCardNo;
    }

    @XmlElement(name = "Mno")
    public String getTelNo() {
        return telNo;
    }

    @XmlElement(name = "Sign")
    public String getSign() {
        return sign;
    }

    public String buildXml() {
        try {
            this.sign();
            return XmlBeanUtils.convertBean2Xml(this, "UTF-8", false);
        } catch (JAXBException e) {
        }
        return null;
    }

    public String sign() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.mchntCd).append("|");
        builder.append(this.ver).append("|");
        builder.append(this.oSsn).append("|");
        builder.append(this.cardNo).append("|");
        builder.append(this.cardType).append("|");
        builder.append(this.idCardNo).append("|");
        builder.append(Constants.API_MCHNT_KEY_2);
        this.sign = MD5.MD5Encode(builder.toString());
        return this.sign;
    }

}
