package com.test.netty;

import io.netty.util.NettyRuntime;

public class Test {

    @org.junit.Test
    public void test1(){
        System.out.println(NettyRuntime.availableProcessors());
    }
}
