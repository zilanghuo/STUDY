package com.mouse.study.test.java8.List;

import com.mouse.study.utils.JackJsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author lwf
 * @date 2018/5/25
 * use:
 */
public class TestMain {

    public static void main(String[] args) {
        User one = new User(3, "阿豪");
        User two = new User(2, "特性");
        User three = new User(1, "本来");
        User four = new User(4, "购买");
        User five = new User(0, "连连");
        TreeSet<User> set = new TreeSet();
        set.add(one);
        set.add(two);
        set.add(three);
        set.add(four);
        set.add(five);

        Iterator<User> iterator = set.iterator();
        while (iterator.hasNext()) {
            User next = iterator.next();
            System.out.println(JackJsonUtil.objToStr(next));
        }


    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User implements Comparable {

    private static final long serialVersionUID = 1171110650373330115L;

    private Integer id;

    private String name;


    @Override
    public int compareTo(Object o) {
        User user = (User) o;
        return this.name.hashCode() - user.getName().hashCode();
    }
}
