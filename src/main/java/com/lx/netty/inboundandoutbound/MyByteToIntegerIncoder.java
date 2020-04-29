package com.lx.netty.inboundandoutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 *  客户端写出，出栈
 */
public class MyByteToIntegerIncoder extends MessageToByteEncoder<Integer> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) throws Exception {
        System.out.println("incode 方法");
        System.out.println("msg = " + msg);
        out.writeInt(msg);
    }
}
