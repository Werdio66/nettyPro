package com.lx.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     *  读取通道事件
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("当前线程：" + Thread.currentThread().getName());
        System.out.println("客户端发送的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("当发送消息的客户端是：" + channel.remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 发送数据
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server...", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常：" + cause.getMessage());
    }
}
