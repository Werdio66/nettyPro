package com.lx.netty.protocol_tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        byte[] bytes = msg.getBytes();

        System.out.println("服务端 => " + new String(bytes, StandardCharsets.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 发送 10 个数据包
        for (int i = 0; i < 10; i++) {
            String msg = " hello server " + i;
            byte[] bytes = msg.getBytes();
            MessageProtocol messageProtocol = new MessageProtocol(bytes.length, bytes);
            ctx.writeAndFlush(messageProtocol);
        }
    }
}
