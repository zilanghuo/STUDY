package com.mouse.study.test.thread.run;

import lombok.Data;

import java.util.Arrays;

/**
 * @author laiwufa
 * @date 2018/9/14
 * use:
 */
public class Bank {

    private final double[] accounts;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }


    public void transferAmount(Integer from, Integer to, Double amount) {
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
    }


}

@Data
class BankCount {
    private Integer amount;
}


