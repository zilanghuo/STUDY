package com.mouse.study.test.bst;

import lombok.Data;

/**
 * @author lwf
 * @date 2018/3/12
 * use:
 */
@Data
public class RBTreeNode<T extends Comparable<T>> {

    private T value;//node value
    private RBTreeNode<T> left;//left child pointer
    private RBTreeNode<T> right;//right child pointer
    private RBTreeNode<T> parent;//parent pointer
    private boolean red;//color is red or not red

    boolean isRed() {
        return red;
    }
    boolean isBlack(){
        return !red;
    }

    public RBTreeNode() {
    }

    public RBTreeNode(T value) {
        this.value = value;
    }

    public RBTreeNode(T value, boolean isRed) {
        this.value = value;
        this.red = isRed;
    }

    boolean isLeaf() {
        return left == null && right == null;
    }

    void makeRed() {
        red = true;
    }

    void makeBlack() {
        red = false;
    }

    @Override
    public String toString(){
        return value.toString();
    }

}
