package com.mouse.study.test.fuyou.res;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author lwf
 * @date 2018/7/10
 * use:
 */
@Setter
@XmlRootElement(name = "qrytransrsp")
public class QueryDpayOrderRespData {

    private String ret;

    private String memo;

    private List<TransResData> trans;

    @XmlElement(name = "ret")
    public String getRet() {
        return ret;
    }

    @XmlElement(name = "memo")
    public String getMemo() {
        return memo;
    }

    @XmlElement(name = "trans")
    public List<TransResData> getTrans() {
        return trans;
    }
}

@Setter
class TransResData{

    private String merDt;

    private String orderNo;

    private String accntNo;

    private String accntNm;

    private Long amt;

    private String entSeq;

    private String memo;

    private String state;

    private String result;

    private String reason;

    @XmlElement(name = "merdt")
    public String getMerDt() {
        return merDt;
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
    public Long getAmt() {
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
    public String getReason() {
        return reason;
    }
}
