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
@XmlRootElement(name = "payforreq")
public class WithdrawReqData {

    private String ver = "1.0";

    private String merdt;

    private String orderNo;

    private String bankNo;

    private String cityNo;

    private String branchNm;

    private String accntNo;

    private String accntNm;

    private Integer amt;

    private String entSeq;

    private String memo;

    private String mobile;

    private String addDesc;


    @XmlElement(name = "ver")
    public String getVer() {
        return ver;
    }

    @XmlElement(name = "merdt")
    public String getMerdt() {
        return merdt;
    }

    @XmlElement(name = "orderno")
    public String getOrderNo() {
        return orderNo;
    }

    @XmlElement(name = "bankno")
    public String getBankNo() {
        return bankNo;
    }

    @XmlElement(name = "cityno")
    public String getCityNo() {
        return cityNo;
    }

    @XmlElement(name = "branchnm")
    public String getBranchNm() {
        return branchNm;
    }

    @XmlElement(name = "accntno")
    public String getAccntNo() {
        return accntNo;
    }

    @XmlElement(name = "accntnm")
    public String getAccntNm() {
        return accntNm;
    }

    @XmlElement(name = "amt")
    public Integer getAmt() {
        return amt;
    }

    @XmlElement(name = "entseq")
    public String getEntSeq() {
        return entSeq;
    }

    @XmlElement(name = "memo")
    public String getMemo() {
        return memo;
    }

    @XmlElement(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    @XmlElement(name = "addDesc")
    public String getAddDesc() {
        return addDesc;
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
        builder.append("payforreq").append("|");
        builder.append(buildXml());
        return MD5Util.MD5Encode(builder.toString());
    }

}
