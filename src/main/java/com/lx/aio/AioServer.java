package com.lx.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class AioServer {

    public static void main(String[] args) throws IOException {

        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 9999));
        while (true){
            Future<AsynchronousSocketChannel> channelFuture = serverSocketChannel.accept();

        }
    }
}
