package com.mouse.study.test.java8;

/**
 * @author lwf
 * @date 2017/12/1
 * use:
 */
public class TestDemo {

    @org.junit.Test
    public void testTen(){
        System.out.println("30".startsWith("3001"));
        System.out.println("success".indexOf("success"));
    }

    @org.junit.Test
    public void testNight(){
        int num = 2147483647 ;
        long temp = num + 2L ;
        System.out.println(num) ;
        System.out.println(temp) ;
    }

    @org.junit.Test
    public void testEight(){
        int num = 100 ;
        long x = num + 2 ;
        System.out.println(x) ;
    }

    @org.junit.Test
    public void testSeven(){
        char c = 'A' ;
        int num = 10 ;
        switch(c) {
            case 'B' :
                num ++ ;
            case 'A' :
                num ++ ;
            case 'C' :
                num ++ ;
            case 'Y' :
                num ++ ;
                break ;
            default :
                num -- ;
        }
        System.out.println(num) ;
    }

    @org.junit.Test
    public void testSix() {
        int num = 68;
        char c = (char) num;
        System.out.println(c);
        System.out.println((char) 97);
        System.out.println((char) 65);

    }

    @org.junit.Test
    public void testFive() {
        int num = 2147483647 ;
        num += 2L ;
        System.out.println(num);
        System.out.println('a');
        /**
         * 　01111111 11111111 11111111 11111111
         ＋00000000 00000000 00000000 00000001
         ＝10000000 00000000 00000000 00000000(=-2147483648，因為溢位，變成了負數。)
         */
    }

    @org.junit.Test
    public void testFour() {
        int sum = 0;
        for (int x = 0; x < 10; x++) {
            sum += x;
            if (x % 3 == 0) {
                break;
            }
        }
        System.out.println(sum);
    }

    @org.junit.Test
    public void testOne() {
        int num = 50;
        num = ++num * 2; // num = (num +1)*2
        System.out.println(num);
    }

    @org.junit.Test
    public void testTwo() {
        int num = 50;
        num = num++ * 2; // (num * 2)
        System.out.println(num);
    }

    @org.junit.Test
    public void testThree() {
        int i = 1;
        int j = i++;
        if ((i == (++j)) && ((i++) == j)) {
            System.out.println("--" + i);
            System.out.println("--" + j);
            i += j;
        }
        System.out.println("i = " + i);
    }


}
