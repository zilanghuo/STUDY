package com.mouse.study.test.fuyou.req;

import com.fuiou.util.MD5;
import com.mouse.study.test.fuyou.XmlBeanUtils;
import lombok.Setter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/5/29
 * use:
 */
@Setter
@XmlRootElement(name = "ORDER")
public class OrderReqData {

    private String mchntCd;

    private String type;

    private String version;

    private String logoTp;

    private String mchntOrderId;

    private String userId;

    private Integer amt;

    private String bankCard;

    private String name;

    private String idType;

    private String idNo;

    private String rem1;

    private String rem2;

    private String rem3;

    private String signTp;

    private String sign;

    private String backUrl;

    private String reUrl;

    private String homeUrl;

    @XmlElement(name = "MCHNTCD")
    public String getMchntCd() {
        return mchntCd;
    }

    @XmlElement(name = "TYPE")
    public String getType() {
        return type;
    }

    @XmlElement(name = "VERSION")
    public String getVersion() {
        return version;
    }

    @XmlElement(name = "LOGOTP")
    public String getLogoTp() {
        return logoTp;
    }

    @XmlElement(name = "MCHNTORDERID")
    public String getMchntOrderId() {
        return mchntOrderId;
    }

    @XmlElement(name = "USERID")
    public String getUserId() {
        return userId;
    }

    @XmlElement(name = "AMT")
    public Integer getAmt() {
        return amt;
    }

    @XmlElement(name = "BANKCARD")
    public String getBankCard() {
        return bankCard;
    }

    @XmlElement(name = "NAME")
    public String getName() {
        return name;
    }

    @XmlElement(name = "IDTYPE")
    public String getIdType() {
        return idType;
    }

    @XmlElement(name = "IDNO")
    public String getIdNo() {
        return idNo;
    }

    @XmlElement(name = "REM1")
    public String getRem1() {
        return rem1;
    }

    @XmlElement(name = "REM2")
    public String getRem2() {
        return rem2;
    }

    @XmlElement(name = "REM3")
    public String getRem3() {
        return rem3;
    }

    @XmlElement(name = "SIGNTP")
    public String getSignTp() {
        return signTp;
    }

    @XmlElement(name = "SIGN")
    public String getSign() {
        return sign;
    }

    @XmlElement(name = "BACKURL")
    public String getBackUrl() {
        return backUrl;
    }

    @XmlElement(name = "REURL")
    public String getReUrl() {
        return reUrl;
    }

    @XmlElement(name = "HOMEURL")
    public String getHomeUrl() {
        return homeUrl;
    }

    public String buildXml(){
        try {
            StringBuffer temp = new StringBuffer();
            temp.append(this.type).append("|")
                    .append(this.version).append("|")
                    .append(this.mchntCd).append("|")
                    .append(this.mchntOrderId).append("|")
                    .append(this.userId).append("|")
                    .append(this.amt).append("|")
                    .append(this.bankCard).append("|")
                    .append(this.backUrl).append("|")
                    .append(this.name).append("|")
                    .append(this.idNo).append("|")
                    .append(this.idType).append("|")
                    .append(this.logoTp).append("|")
                    .append(this.homeUrl).append("|")
                    .append(this.reUrl).append("|")
                    .append("5old71wihg2tqjug9kkpxnhx9hiujoqj");
            this.sign = MD5.MD5Encode(temp.toString());
            return XmlBeanUtils.convertBean2Xml(this, "UTF-8", true);
        } catch (JAXBException e) {
        }
        return null;
    }
}
