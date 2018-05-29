package com.mouse.study.test.fuyou.req;

import com.fuiou.util.MD5;
import com.mouse.study.test.fuyou.XmlBeanUtils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ORDER")
public class CheckIdReqData {
    private String version;
    private String typeId;
    private String mchntCd;
    private String mchntOrderid;
    private String name;
    private String idNo;
    private String sign;
    private String rem1;
    private String rem2;
    private String rem3;

    public String getVersion() {
        return version;
    }

    @XmlElement(name = "VERSION")
    public void setVersion(String version) {
        this.version = version;
    }

    public String getTypeId() {
        return typeId;
    }

    @XmlElement(name = "TYPEID")
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getMchntCd() {
        return mchntCd;
    }

    @XmlElement(name = "MCHNTCD")
    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    public String getMchntOrderid() {
        return mchntOrderid;
    }

    @XmlElement(name = "MCHNTORDERID")
    public void setMchntOrderid(String mchntOrderid) {
        this.mchntOrderid = mchntOrderid;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "NAME")
    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    @XmlElement(name = "IDNO")
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getSign() {
        return sign;
    }

    @XmlElement(name = "SIGN")
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRem1() {
        return rem1;
    }

    @XmlElement(name = "REM1")
    public void setRem1(String rem1) {
        this.rem1 = rem1;
    }

    public String getRem2() {
        return rem2;
    }

    @XmlElement(name = "REM2")
    public void setRem2(String rem2) {
        this.rem2 = rem2;
    }

    public String getRem3() {
        return rem3;
    }

    @XmlElement(name = "REM3")
    public void setRem3(String rem3) {
        this.rem3 = rem3;
    }

    public String buildXml() throws JAXBException {
        this.setMchntCd("0002900F0096235");
        StringBuffer temp = new StringBuffer();
        temp.append(this.version).append("|").append(this.typeId).append("|").append(this.mchntCd).append("|").append(this.mchntOrderid).append("|")
                .append(this.name).append("|").append(this.idNo).append("|").append("5old71wihg2tqjug9kkpxnhx9hiujoqj");
        System.out.println("验签明文：" + temp.toString());
        this.sign = MD5.MD5Encode(temp.toString());
        return XmlBeanUtils.convertBean2Xml(this, "UTF-8", true);
    }
}
