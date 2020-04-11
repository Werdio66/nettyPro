package com.lx.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {
    public static void main(String[] args) throws Exception {
        String str = "hello,啦啦啦";
        // 获取文件输出流
        FileOutputStream outputStream = new FileOutputStream("f:\\file1.txt");
        // 得到通道
        FileChannel fileChannel = outputStream.getChannel();
        // 缓存的数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(str.getBytes().length);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        // 将 byteBuffer 写入 fileChannel 中
        int write = fileChannel.write(byteBuffer);
        outputStream.close();

        // ============================ 从文件中读取数据 ===============================
        File file = new File("f:\\file1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel inputStreamChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(((int) file.length()));
        int read = inputStreamChannel.read(byteBuffer1);
        fileInputStream.close();
        System.out.println(new String(byteBuffer1.array()));
    }
}
