package com.mouse.study.test.fuyou.res;

import com.mouse.study.utils.MD5Util;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/5/31
 * use:
 */
@XmlRootElement(name = "FM")
@Setter
public class QueryCardBinRespData {

    private String rcd;

    private String rDesc;

    private String ctp;

    private String cnm;

    private String insCd;

    private String sign;

    public String sign(String key){
        StringBuffer temp = new StringBuffer();
        temp.append(this.rcd).append("|").append(key);
        return MD5Util.MD5Encode(temp.toString());
    }

    @XmlElement(name = "Rcd")
    public String getRcd() {
        return rcd;
    }

    @XmlElement(name = "RDesc")
    public String getrDesc() {
        return rDesc;
    }

    @XmlElement(name = "Ctp")
    public String getCtp() {
        return ctp;
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
}
