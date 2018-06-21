package com.mouse.study.test.fuyou.req;

import com.fuiou.util.MD5;
import com.mouse.study.test.fuyou.XmlBeanUtils;
import lombok.Setter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/6/21
 * use: 卡bin查询
 */
@Setter
@XmlRootElement(name = "FM")
public class QueryCardBinReqData {

    private String mchntCd;

    private String ono;

    private String sign;

    public String buildXml(String key) throws JAXBException {
        StringBuffer temp = new StringBuffer();
        temp.append(this.mchntCd).append("|")
                .append(this.ono).append("|")
                .append(key);
        this.sign = MD5.MD5Encode(temp.toString());
        return XmlBeanUtils.convertBean2Xml(this, "UTF-8", false);
    }

    @XmlElement(name = "MchntCd")
    public String getMchntCd() {
        return mchntCd;
    }


    @XmlElement(name = "Ono")
    public String getOno() {
        return ono;
    }

    @XmlElement(name = "Sign")
    public String getSign() {
        return sign;
    }


}
