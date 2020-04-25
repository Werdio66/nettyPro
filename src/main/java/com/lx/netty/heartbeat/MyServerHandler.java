package com.lx.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

            String msg = "";
            switch (idleStateEvent.state()){
                case READER_IDLE:
                    msg = "读空闲";
                    break;
                case WRITER_IDLE:
                    msg = "写空闲";
                    break;
                case ALL_IDLE:
                    msg = "读写空闲";
                    break;
                default:
                    break;
            }

            System.out.println(ctx.channel().remoteAddress() + " : " + msg);
        }
    }
}
