package org.example.test;

import javax.security.auth.login.AccountExpiredException;

public class Test {
    public static void main(String[] args) {
//        for (Class<?> clazz = AccountExpiredException.class; clazz != null; clazz = clazz.getSuperclass()) {
//            System.out.println(clazz.getName());
//        }
        if (String.class.isPrimitive()) {
            System.out.println("String is primitive");
        }
    }
}
