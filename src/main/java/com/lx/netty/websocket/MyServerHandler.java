package com.lx.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class MyServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "连接成功");
        ctx.writeAndFlush(ctx.channel().remoteAddress() + "连接成功");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "断开连接");
        ctx.writeAndFlush(ctx.channel().remoteAddress() + "断开连接");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        System.out.println(ctx.channel().remoteAddress() + " : " + msg.text());

        ctx.writeAndFlush(new TextWebSocketFrame("客户端 " + ctx.channel().remoteAddress() + " : " + msg.text()));
    }
}
