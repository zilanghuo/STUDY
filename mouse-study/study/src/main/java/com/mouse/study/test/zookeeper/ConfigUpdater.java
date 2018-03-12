package com.mouse.study.test.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author lwf
 * @date 2017/11/16
 * use:
 */
public class ConfigUpdater {

    public static final String PATH = "/config";
    private ActiveKeyValueStore store;
    private Random random = new Random();
    public ConfigUpdater(String hosts) throws IOException, InterruptedException {
        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public void run() throws InterruptedException, KeeperException {
        while (true) {
            String value = random.nextInt(100) + "";
            store.write(PATH, value);
            System.out.printf("Set %s to %s\n", PATH, value);
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        }
    }

    public static void main(String[] args) throws Exception {
            ConfigUpdater configUpdater = new ConfigUpdater(Const.ZK_URL);
        configUpdater.run();
    }

}