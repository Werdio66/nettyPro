package com.lx.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  将一个文件拷贝一份
 */
public class FileChannelCopy {
    public static void main(String[] args) throws Exception {

        String fileName = "src/main/resources/1.txt";
        String targetName = "src/main/resources/2.txt";
        FileInputStream fileInputStream = new FileInputStream(new File(fileName));
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream(targetName);
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        // 循环从文件中读取，并写入另一个文件
        while (true){
            int read = fileInputStreamChannel.read(byteBuffer);
            System.out.println(new String(byteBuffer.array()));
            System.out.println("---------------------------");
            byteBuffer.flip();
            fileOutputStreamChannel.write(byteBuffer);
            if (read == -1){
                break;
            }
            byteBuffer.clear();
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
