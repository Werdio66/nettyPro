package com.lx.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 群聊系统服务端
 */
public class GroupChatServer {

    private Selector clientSelector;
    private Selector serverSelector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 9999;

    public GroupChatServer(){
        try {
            clientSelector = Selector.open();
            serverSelector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            // 绑定端口
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            // 设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            // 将 serverSocketChannel 注册到 serverSelector 上
            serverSocketChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器已启动");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen(){
        while (true){
            try {
                // 客户端连接或者客户端有读写操作
                if (serverSelector.select(1000) > 0 || clientSelector.select(1000) > 0) {
                    handlerOnLine();
                    handlerClients();

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handlerClients() {
        // 获取所有的 socketKey
        Iterator<SelectionKey> keyIterator = clientSelector.selectedKeys().iterator();

        while (keyIterator.hasNext()){
            SelectionKey selectionKey = keyIterator.next();
            // 读事件
            if (selectionKey.isReadable()){

                readMsg(selectionKey);
            }
            keyIterator.remove();
        }
    }

    private void handlerOnLine() {
        // 获取所有的 socketKey
        Iterator<SelectionKey> keyIterator = serverSelector.selectedKeys().iterator();

        while (keyIterator.hasNext()) {
            SelectionKey selectionKey = keyIterator.next();
            // 连接事件
            if (selectionKey.isAcceptable()) {
                try {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // 注册到 clientSelector 上
                    socketChannel.register(clientSelector, SelectionKey.OP_READ);
                    System.out.println(socketChannel.getRemoteAddress() + " 上线");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                keyIterator.remove();
            }
        }
    }

    private void readMsg(SelectionKey selectionKey) {
        // 获取当前通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        // 获取缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            // 将通道中的数据读取到缓冲区
            socketChannel.read(byteBuffer);
            String msg = new String(byteBuffer.array());
            byteBuffer.clear();
            System.out.println(socketChannel.getRemoteAddress() + " : " + msg);
            // 转发给其他客户端
            writeToOtherClients(socketChannel.getRemoteAddress() + " : " + msg, socketChannel);
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + " 下线");
                // 取消注册
                selectionKey.cancel();
                socketChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void writeToOtherClients(String msg, SocketChannel socketChannel) {
        Set<SelectionKey> selectionKeys = clientSelector.keys();
        if (selectionKeys.size() <= 1){
            return;
        }

        for (SelectionKey selectionKey : selectionKeys) {
            SocketChannel channel = (SocketChannel) selectionKey.channel();
            // 排除自己
            if (channel.equals(socketChannel)){
                continue;
            }
            try {
                // 发送写入其他通道的缓冲区中
                channel.write(ByteBuffer.wrap(msg.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.listen();
    }
}
