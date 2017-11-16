package com.mouse.study.test.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * @author lwf
 * @date 2017/11/16
 * use:
 */
public class DeleteGroup extends ConnectionWatcher {

    public void delete(String groupName) throws KeeperException,
            InterruptedException {
        String path = "/" + groupName;
        try {
            List<String> children = zk.getChildren(path, false);
            for (String child : children) {
                zk.delete(path + "/" + child, -1);
            }
            zk.delete(path, -1);
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        }
    }
    public static void main(String[] args) throws Exception {
        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect(Const.ZK_URL);
        deleteGroup.delete("test02");
        deleteGroup.close();
    }
}
