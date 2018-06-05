package com.mouse.study.test.fuyou.req;

import com.fuiou.util.MD5;
import com.mouse.study.test.fuyou.Constants;
import com.mouse.study.test.fuyou.XmlBeanUtils;
import com.mouse.study.utils.MD5UtilsTemp;
import lombok.Setter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/5/30
 * use:
 */
@Setter
@XmlRootElement(name = "ORDER")
public class QueryOrderReqData {

    private String version;

    private String mchntCd;

    private String mchntOrderId;

    private String sign;

    private String rem1;

    private String rem2;

    private String rem3;

    @XmlElement(name = "VERSION")
    public String getVersion() {
        return version;
    }

    @XmlElement(name = "MCHNTCD")
    public String getMchntCd() {
        return mchntCd;
    }

    @XmlElement(name = "MCHNTORDERID")
    public String getMchntOrderId() {
        return mchntOrderId;
    }

    @XmlElement(name = "SIGN")
    public String getSign() {
        return sign;
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

    public String buildXml() throws JAXBException {
        StringBuffer temp = new StringBuffer();
        temp.append(this.version).append("|")
                .append(this.mchntCd).append("|")
                .append(this.mchntOrderId).append("|")
                .append(Constants.API_MCHNT_KEY_2);
        System.out.println("验签明文：" + temp.toString());
        this.sign = MD5.MD5Encode(temp.toString());
        System.out.println(MD5.MD5Encode(temp.toString()));
        System.out.println(MD5UtilsTemp.encode(temp.toString(),"UTF-8"));

        return XmlBeanUtils.convertBean2Xml(this, "UTF-8", false);
    }

}
