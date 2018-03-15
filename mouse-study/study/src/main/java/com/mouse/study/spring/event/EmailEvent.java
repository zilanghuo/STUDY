package com.mouse.study.spring.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @author lwf
 * @date 2018/3/15
 * use:
 */
@Data
public class EmailEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    //属性
    private String address;
    private String text;
    //构造方法
    public EmailEvent(Object source) {
        super(source);
    }
    public EmailEvent(Object source, String address, String text) {
        super(source);
        this.address = address;
        this.text = text;
    }

}
