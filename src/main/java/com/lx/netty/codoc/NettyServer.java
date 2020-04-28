package com.lx.netty.codoc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

public class NettyServer {

    public static void main(String[] args) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 增加 protoBuf 的解码器
                            pipeline.addLast(new ProtobufDecoder(StudentProto.Student.getDefaultInstance()));
                            // 增加处理器
                            pipeline.addLast(new ServerHandler());
                        }
                    });

            // 绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind("localhost", 8888).sync();
            System.out.println("服务器启动，端口 : 8888");
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            // 优雅的关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }



    }
}
