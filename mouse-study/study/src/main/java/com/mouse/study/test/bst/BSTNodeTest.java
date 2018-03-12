package com.mouse.study.test.bst;

/**
 * @author lwf
 * @date 2018/3/12
 * use:二叉树
 */
public class BSTNodeTest {


    public Node insertNode(Node<String> insertNode, Node<String> root) {
        //insertNode：待插入的节点
        //初始化节点：root位于最顶端
        Node<String> parent = null;
        while (true){
            if (root == null){
                break;
            }
            parent = root;
            if (insertNode.value.compareTo(root.value) > 0){
                root = root.right;
            }else{
                root = root.left;
            }
        }
        if (parent != null){
            if (insertNode.value.compareTo(parent.value) > 0){
                parent.right = insertNode;
            }else{
                parent.left = insertNode;
            }
        }
        return null;
    }

    /**
     * 获取节点
     *
     * @param key
     * @return
     */
    public Node getNodeValue(String key) {
        Node<String> root = initBstTree();
        while (true) {
            if (root == null) {
                break;
            }
            if (root.value.equals(key)) {
                return root;
            } else if (key.compareTo(root.value) < 0) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return root;
    }

    private Node<String> initBstTree() {

        Node<String> root = new Node();
        //初始化略
        return root;
    }

}
