package com.lx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 服务器端
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        // 获取服务器通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(6666));
        System.out.println("服务器已经启动");
        // 获取选择器
        Selector selector = Selector.open();
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 将服务器通道注册到选择器中
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("将服务器通道注册到选择器中");
        while (true){
            // 没有事件发生
            if (selector.select(1000) == 0) {
                continue;
            }
            // 获取所有的已选择的事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 拿到 selectionKeys 的迭代器
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
//            keyIterator.forEachRemaining();
            while (keyIterator.hasNext()){
                SelectionKey selectionKey = keyIterator.next();
                // 是连接事件
                if (selectionKey.isAcceptable()){
                    // 获取客户端通道
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    // 将客户端通道注册到选择器上
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接成功");
                }
                // 读取事件
                if (selectionKey.isReadable()){
                    // 获取当前事件的缓冲区对象
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    // 获取当前事件对应的通道
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    System.out.println("读取之前缓冲区的数据：" + new String(byteBuffer.array()));
                    // 将通道中的数据读取到缓冲区中
                    channel.read(byteBuffer);
                    System.out.println("读取之后缓冲区的数据：" + new String(byteBuffer.array()));
                    byteBuffer.clear();
                }

                keyIterator.remove();
            }
        }
    }
}
