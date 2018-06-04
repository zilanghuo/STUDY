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
 * @date 2018/5/29
 * use:
 */
@Setter
@XmlRootElement(name = "FM")
public class DisableOrderReqData {

    private String mchntCd;

    private String mchntOrderId;

    private String orderId;

    private Integer amt;

    private String sign;


    @XmlElement(name = "MCHNTCD")
    public String getMchntCd() {
        return mchntCd;
    }

    @XmlElement(name = "MCHNTORDERID")
    public String getMchntOrderId() {
        return mchntOrderId;
    }

    @XmlElement(name = "ORDERID")
    public String getOrderId() {
        return orderId;
    }

    @XmlElement(name = "AMT")
    public Integer getAmt() {
        return amt;
    }

    @XmlElement(name = "SIGN")
    public String getSign() {
        return sign;
    }

    public String buildXml() {
        try {
            StringBuffer temp = new StringBuffer();
            temp.append(this.mchntCd).append("|")
                    .append(this.mchntOrderId).append("|")
                    .append(this.orderId).append("|")
                    .append(this.amt).append("|")
                    .append(Constants.API_MCHNT_KEY);
            this.sign = MD5Util.MD5Encode(temp.toString());
            System.out.println("验签数据：" + this.sign);
            return XmlBeanUtils.convertBean2Xml(this, "UTF-8", false);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
