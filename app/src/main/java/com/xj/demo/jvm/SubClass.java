package com.xj.demo.jvm;

public class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init!");
        //成功
        A = 1;

    }

    public static int A = 3;

    public static void main(String[] args) {
        System.out.println(SubClass.A);
    }
}
