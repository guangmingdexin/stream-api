package com.guang.homework.question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guangyong.deng
 * @date 2021-12-02 17:47
 */
public class Question3 {

    private static List<User> users = new ArrayList<>();

    static {
        User user1 = new User("超级管理员,实验室管理员,超市管理员", "重庆实验室,深圳实验室",
                "周黑鸭,溜溜梅,凤梨");
        User user2 = new User("香肠管理员,超市管理员,男人管理员", "非洲实验室,撒哈拉实验室",
                "周黑鸭,苹果,香蕉");
        User user3 = new User("超级管理员,太空管理员,神奇管理员", "祖特实验室,祖安实验室",
                "黑凤梨,麻辣烫,重庆小面");
        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    public static void main(String[] args) {

        users.stream().collect(
                Collectors.groupingBy(User::getRoleRight)
        );
    }
}
