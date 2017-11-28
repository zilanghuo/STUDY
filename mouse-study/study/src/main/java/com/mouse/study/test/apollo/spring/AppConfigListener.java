package com.mouse.study.test.apollo.spring;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lwf on 2017/8/8.
 * use to do:
 */
@Component
public class AppConfigListener {

    /*   @Autowired
       private RefreshScope refreshScope;
   */
    @Autowired
    private ConfigBean configBean;

    private static final Logger logger = LoggerFactory.getLogger(AppConfigListener.class);

    @ApolloConfigChangeListener("application")
    public void onChange(ConfigChangeEvent changeEvent) {
        logger.info("Changes for namespace {}", changeEvent.getNamespace());
        for (String key : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(key);
            logger.info("Change - key: {}, oldValue: {}, newValue: {}, changeType: {}",
                    change.getPropertyName(), change.getOldValue(), change.getNewValue(),
                    change.getChangeType());
            // refreshScope.refresh("configBean");
        }
    }


}
