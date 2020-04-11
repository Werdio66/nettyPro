package com.lx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws IOException {
        // 创建 serverSocket 连接
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置 socket 地址
        SocketAddress socketAddress = new InetSocketAddress(7777);
        // 绑定地址到 serversocket
        serverSocketChannel.bind(socketAddress);
        System.out.println("服务器已经启动，端口：7777");
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(4);
        byteBuffers[1] = ByteBuffer.allocate(3);
        // 等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        while (true){
            // 读取字符序列到缓冲区
            long read = socketChannel.read(byteBuffers);
            System.out.println("从通道中读取的数据是：");
            // 将所有的缓冲区翻转
            Arrays.asList(byteBuffers).forEach(Buffer::flip);
            Arrays.asList(byteBuffers).forEach(System.out::println);

            Arrays.stream(byteBuffers).filter(Buffer::hasRemaining).map(ByteBuffer::array).forEach(bytes -> System.out.print(new String(bytes)));

//            Arrays.asList(byteBuffers).forEach(Buffer::flip);

//            // 从给定的缓冲区向此通道写入字节序列。输出到客户端
//            long write = socketChannel.write(byteBuffers);

            // 清除缓冲区
            Arrays.asList(byteBuffers).forEach(Buffer::clear);

        }
    }
}
