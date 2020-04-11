package com.lx.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {

    public static void main(String[] args) throws IOException {
        // 创建 server
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器启动");
        while (true){
            // 主线程等待客户端的连接
            System.out.println(Thread.currentThread().getName() + " 等待客户端连接");
            // 等待客户端连接
            Socket socket = serverSocket.accept();
            // 创建一个线程处理当前请求
            ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
            newCachedThreadPool.execute(() -> {
                // 处理连接
                try {
                    System.out.println("客户端 " + Thread.currentThread().getName() + " 已连接");
                    handler(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    private static void handler(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        System.out.println("等待客户端" + Thread.currentThread().getName() + "发送数据");
        int read = inputStream.read(bytes);
        System.out.println("客户端 " + Thread.currentThread().getName() + " 发送数据");
        while (read != -1){
            System.out.println(new String(bytes));
            read = inputStream.read(bytes);
        }
    }
}
