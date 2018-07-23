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
 * @date 2018/6/5
 * use:
 */
@Setter
@XmlRootElement(name = "qrytransreq")
public class QueryWithdrawOrderReqData {

    private String ver = "1.0";

    private String busiCd = "AC01";

    private String orderNo;

    private String startDt;

    private String endDt;

    private String transSt;

    @XmlElement(name = "ver")
    public String getVer() {
        return ver;
    }

    @XmlElement(name = "busicd")
    public String getBusiCd() {
        return busiCd;
    }

    @XmlElement(name = "orderno")
    public String getOrderNo() {
        return orderNo;
    }

    @XmlElement(name = "startdt")
    public String getStartDt() {
        return startDt;
    }

    @XmlElement(name = "enddt")
    public String getEndDt() {
        return endDt;
    }

    @XmlElement(name = "transst")
    public String getTransSt() {
        return transSt;
    }

    public String buildXml() {
        try {
            return XmlBeanUtils.convertBean2Xml(this, "UTF-8", false);
        } catch (JAXBException e) {
        }
        return null;
    }

    public String sign() {
        StringBuilder builder = new StringBuilder();
        builder.append(Constants.API_MCHNT_CD).append("|");
        builder.append(Constants.API_MCHNT_KEY).append("|");
        builder.append("qrytransreq").append("|");
        builder.append(buildXml());
        return MD5Util.MD5Encode(builder.toString());
    }
}
