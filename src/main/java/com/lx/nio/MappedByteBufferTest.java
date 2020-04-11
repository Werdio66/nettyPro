package com.lx.nio;

import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  MappedByteBuffer 对内存中的字节进行修改
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception {

        RandomAccessFile outputStream = new RandomAccessFile("src/main/resources/1.txt", "rw");

        FileChannel channel = outputStream.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, ((byte) 'v'));

    }
}
