package com.mouse.study.test.fuyou.res;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author lwf
 * @date 2018/6/5
 * use:
 */
@XmlRootElement(name = "payforrsp")
@Setter
public class WithdrawRespData {

    private String ret;

    private String memo;

    private String transStatusDesc;

    @XmlElement(name = "ret")
    public String getRet() {
        return ret;
    }

    @XmlElement(name = "memo")
    public String getMemo() {
        return memo;
    }

    @XmlElement(name = "transStatusDesc")
    public String getTransStatusDesc() {
        return transStatusDesc;
    }
}
