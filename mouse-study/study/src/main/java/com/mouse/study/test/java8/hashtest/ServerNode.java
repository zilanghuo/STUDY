package com.mouse.study.test.java8.hashtest;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lwf
 * @date 2018/3/19
 * use:
 */
@Data
@AllArgsConstructor
public class ServerNode {

    private String serverNodeName;
    private long  serverNodeHash;
}
