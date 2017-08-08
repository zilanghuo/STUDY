package com.mouse.study.test.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lwf on 2017/8/8.
 * use to do:
 */
@Slf4j
public class ApolloService {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        //Config config = ConfigService.getAppConfig(); //config instance is singleton for each namespace and is never null
        try {
            Config config = ConfigService.getConfig("assets");
            String someKey = "jdbc.url";
            String someDefaultValue = "------";
            String value = config.getProperty(someKey, someDefaultValue);
            System.out.println("----------------------"+value);
            log.info("---apollo-------------------"+value);
        }catch (Exception e){
            log.info("{},{}",e,e.getMessage());
        }

    }
}
