package com.lx.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;

public class ByteBuf01 {

    public static void main(String[] args) {

//        ByteBuf byteBuf = Unpooled.buffer(10);
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello", StandardCharsets.UTF_8);
        byte[] array = byteBuf.array();
        System.out.println(byteBuf.arrayOffset());
        System.out.println(byteBuf.capacity());
        System.out.println(byteBuf.readableBytes());
    }
}
