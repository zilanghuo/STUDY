package com.zdmoney.data;

import ch.qos.logback.ext.spring.LogbackConfigurer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by rui on 15/9/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext.xml")
@Transactional
@Slf4j
public abstract class InitDBTestEnvironment extends DataSourceBasedDBTestCase {

    @Resource
    @Getter
    private DataSource dataSource;

    public InitDBTestEnvironment() {
        String testClassPath = this.getClass().getResource("/").getPath().replace("test-classes", "classes");
        System.setProperty("global.config.path", testClassPath + "/config/");
        //增加日志打印
        try {
            LogbackConfigurer.initLogging("classpath:config/logback.xml");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        String file = getClass().getSimpleName() + ".xml";
        return new XmlDataSet(this.getClass().getResourceAsStream(file));
    }
}
