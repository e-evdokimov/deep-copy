package org.example.test;

import java.util.Objects;

public class Empty {

    private static final int a = 1;

    private static Boolean b = Boolean.TRUE;
    private String c = "c";
    private final double d = 0.d;


    public static class InnerStaticEmpty {
        public static InnerStaticEmpty getInstance() {
            return new InnerStaticEmpty();
        }
    }
//
//    public class InnerEmpty {
//        public Empty getEnclosing() {
//            return Empty.this;
//        }
//    }


    public static Boolean getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empty empty = (Empty) o;
        return Objects.equals(c, empty.c);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(c);
    }
}
