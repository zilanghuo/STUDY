package com.mouse.study.test.mode.templete;

/**
 * Created by lwf on 2017/8/4.
 * use to do:
 */
public class Main {
    public static void main(String[] args) {
        TemplateAbstract stringDisplay = new StringDisplay();

        TemplateAbstract integerDisplay = new IntegerDisplay();

        stringDisplay.display();

        integerDisplay.display();

        System.out.println("尊敬的刘大鹏，您投资订单编号为819756的产品9%稀缺产品962910期将收回本息104,463.01元，其中本金100,000元，利息4,463.01元，预计1-2个工作日到账，请及时在“我的—我的投资”中查看，欢迎继续投资。".length());
    }

}
