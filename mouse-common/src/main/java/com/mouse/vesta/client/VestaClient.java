package com.mouse.vesta.client;

import com.robert.vesta.service.intf.IdService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lwf on 2017/6/12.
 * use to do:
 */
public class VestaClient {

    private static IdService idService;

    public VestaClient() {
        if (null == idService) {
            idService = initClient();
        }
    }

    public static IdService initClient() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("vestaClient/vesta-client-sample.xml");
        idService = (IdService) ac.getBean("idService");
        return idService;
    }

    public static String generatorId() {
        long id = idService.genId();
        //Id ido = idService.expId(id);
        return id + "";
    }
}
