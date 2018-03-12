package com.mouse.study.test.bst;

import lombok.Data;

/**
 * @author lwf
 * @date 2018/3/12
 * use: 红黑树结构
 */
@Data
public class RBNode<T> {

    public T value;
    public RBNode<T> parent;
    public boolean isRed;
    public RBNode<T> left;
    public RBNode<T> right;
}
