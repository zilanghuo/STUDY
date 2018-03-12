package com.mouse.study.test.bst;

import lombok.Data;

/**
 * @author lwf
 * @date 2018/3/12
 * use:数据结构节点
 */
@Data
public class Node<T> {
    public T value;
    public Node<T> parent;
    public Node<T> left;
    public Node<T> right;
}
