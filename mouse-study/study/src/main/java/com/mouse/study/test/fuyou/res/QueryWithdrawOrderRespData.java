package com.mouse.study.test.fuyou.res;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author lwf
 * @date 2018/6/5
 * use:
 */
@XmlRootElement(name = "qrytransrsp")
@Setter
public class QueryWithdrawOrderRespData {

    private String ret;

    private String memo;

    private List<Trans> transList;

    @XmlElement(name = "ret")
    public String getRet() {
        return ret;
    }

    @XmlElement(name = "memo")
    public String getMemo() {
        return memo;
    }

    @XmlElement(name = "trans")
    public List<Trans> getTransList() {
        return transList;
    }
}

@Setter
class Trans{

    private String merdt;

    private String orderNo;

    private String accntNo;

    private String accntNm;

    private Integer amt;

    private String entSeq;

    private String memo;

    private String state;

    private String result;

    private String resson;

    @XmlElement(name = "merdt")
    public String getMerdt() {
        return merdt;
    }

    @XmlElement(name = "orderno")
    public String getOrderNo() {
        return orderNo;
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

    @XmlElement(name = "state")
    public String getState() {
        return state;
    }

    @XmlElement(name = "result")
    public String getResult() {
        return result;
    }

    @XmlElement(name = "reason")
    public String getResson() {
        return resson;
    }
}