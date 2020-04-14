package com.lx.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * 客户端
 */
public class GroupChatClient {

    private SocketChannel socketChannel;
    private static final String IP = "localhost";
    private static final int PORT = 9999;

    public GroupChatClient(){
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(IP, PORT));
            socketChannel.configureBlocking(false);
            System.out.println("客户端 " + socketChannel.getLocalAddress() + " 已连接");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToOtherClients(){
        Scanner scanner = new Scanner(System.in);

//        System.out.println("请输入要发送的消息：");
        if (scanner.hasNextLine()) {
            String msg = scanner.next();
            try {
                // 写到缓冲区中
                socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void readFromOtherClients(){
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(byteBuffer);
            if (read > 0){
                // 从缓冲区读取其他客户端发送的消息
                System.out.println(new String(byteBuffer.array()));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {

        GroupChatClient chatClient = new GroupChatClient();

        new Thread(() -> {
            while (true){
                chatClient.readFromOtherClients();
            }
        }).start();

        while (true){
            chatClient.writeToOtherClients();
        }
    }
}
