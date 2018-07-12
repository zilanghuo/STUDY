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
 * @date 2018/7/10
 * use:
 */
@Setter
@XmlRootElement(name = "qrytransreq")
public class QueryDpayOrderReqData {

    private String ver;

    private String busiCd;

    private String orderNo;

    private String startDt;

    private String endDt;

    private String transSt;

    public QueryDpayOrderReqData() {
        this.ver = "1.0";
        this.busiCd = "AC01";
    }

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

    public String buildXml() throws JAXBException {
        return XmlBeanUtils.convertBean2Xml(this, "UTF-8", false);
    }

    public String sign() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(Constants.API_MCHNT_CD).append("|");
        builder.append(Constants.API_MCHNT_KEY).append("|");
        builder.append("qrytransreq").append("|");
        builder.append(buildXml());
        return MD5.MD5Encode(builder.toString());
    }

}
