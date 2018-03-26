package com.mouse.study.test.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author lwf
 * @date 2018/1/22
 * use:
 */
public class SubNodeTest {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(Const.ZK_URL);
        zkClient.subscribeDataChanges("/testNode", new IZkDataListener() {

            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("000000000000");
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("-------------------");
            }
        });

        try {
            Thread.sleep(100*10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
