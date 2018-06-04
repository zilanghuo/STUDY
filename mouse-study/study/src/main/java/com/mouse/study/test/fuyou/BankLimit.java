package com.mouse.study.test.fuyou;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/6/4
 * use:
 */
@Setter
public class BankLimit {

    private String insCd;

    private String cardType;

    private Long amtLimitTime;

    private Long amtLimitDay;

    private Long amtLimitMonth;

    @XmlElement(name = "INSCD")
    public String getInsCd() {
        return insCd;
    }

    @XmlElement(name = "CARDTYPE")
    public String getCardType() {
        return cardType;
    }

    @XmlElement(name = "AMTLIMITTIME")
    public Long getAmtLimitTime() {
        return amtLimitTime;
    }

    @XmlElement(name = "AMTLIMITDAY")
    public Long getAmtLimitDay() {
        return amtLimitDay;
    }

    @XmlElement(name = "AMTLIMITMONTH")
    public Long getAmtLimitMonth() {
        return amtLimitMonth;
    }
}
