package com.mouse.study.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author lwf
 * @date 2018/3/15
 * use:
 */
public class EmailNotifier implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof EmailEvent) {
            EmailEvent emailEvent = (EmailEvent) event;
            System.out.println("----------------email's address:" + emailEvent.getAddress());
            System.out.println("----------------email's text:" + emailEvent.getText());
        } else {
            System.out.println("----------------the Spring's event:" + event);
        }
    }
}
