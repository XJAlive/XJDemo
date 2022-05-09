package com.xj.demo.designmodel;

public class SingleInstance {

    private SingleInstance() {
    }

    public String field;
    public static volatile SingleInstance instance;
    //饿汉式,利用类初始化机制保证线程同步
    public static SingleInstance sInstance = new SingleInstance();

    //懒汉式
    public static SingleInstance getInstance() {
        if (instance == null) {
            instance = new SingleInstance();
        }
        return instance;
    }

    //线程安全,适用于多线程
    public static synchronized SingleInstance getSyncInstance() {
        if (instance == null) {
            instance = new SingleInstance();
        }
        return instance;
    }

    //DCL,instance必须申明volatile，防止指令重排
    public static SingleInstance getCheckInstance() {
        if (instance == null) {//防止多次进入同步代码块，进行非必要阻塞
            synchronized (SingleInstance.class) {
                if (instance == null) {//其他线程进入不需要再次初始化
                    instance = new SingleInstance();
                }
            }
        }
        return instance;
    }

    private static class InstanceHolder {

        private static final SingleInstance mInstance = new SingleInstance();
    }

    //静态内部类,利用类初始化机制保证线程同步,在使用时才加载
    public static SingleInstance getInternalInstance() {
        return InstanceHolder.mInstance;
    }

    //枚举
    enum Singleton {
        INSTANCE;

        int value = 3;

        public void log() {
            System.out.println(value);
        }
    }

}
