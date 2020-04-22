package com.lx.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("当前线程：" + Thread.currentThread().getName());
        System.out.println("通道地址：" + channel.remoteAddress());
        EventLoop eventLoop = ctx.channel().eventLoop();

        eventLoop.execute(() -> {
            try {
                Thread.sleep(5000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello client", CharsetUtil.UTF_8));
                System.out.println("通道 ：" + channel.hashCode());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println("客户端 => " + byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已经收到消息", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
    }
}
