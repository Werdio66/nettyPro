package com.lx.netty.protocol_tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMsgDecoder extends ReplayingDecoder<MessageProtocol> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("将数据解码");

        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        MessageProtocol messageProtocol = new MessageProtocol(length, bytes);

        out.add(messageProtocol);
    }
}
