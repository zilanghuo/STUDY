package com.mouse.study.test.fuyou.req;

import com.mouse.study.test.fuyou.XmlBeanUtils;
import com.mouse.study.utils.MD5Util;
import lombok.Setter;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/6/4
 * use:
 */
@Setter
@XmlRootElement(name = "FM")
public class LimitQueryReqData {

    private String mchntCd;

    private String insCd;

    private String sign;

    @XmlElement(name = "MCHNTCD")
    public String getMchntCd() {
        return mchntCd;
    }

    @XmlElement(name = "INSCD")
    public String getInsCd() {
        return insCd;
    }

    @XmlElement(name = "SIGN")
    public String getSign() {
        return sign;
    }

    public String buildXml(String key) {
        try {
            StringBuffer temp = new StringBuffer();
            temp.append(this.mchntCd).append("|")
                    .append(this.insCd).append("|")
                    .append(key);
            this.sign = MD5Util.MD5Encode(temp.toString());
            System.out.println("验签数据：" + this.sign);
            return XmlBeanUtils.convertBean2Xml(this, "UTF-8", false);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
