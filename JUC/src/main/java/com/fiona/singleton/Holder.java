package com.fiona.singleton;

//静态内部类
public class Holder {
    private Holder(){

    }

    public static class InerClass{
        private static final Holder HOLDER = new Holder();

    }

    public static Holder getInstance(){
        return InerClass.HOLDER;
    }

}
