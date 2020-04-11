package com.lx.nio;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 *  transferFrom
 */
public class TransferFormTest {
    public static void main(String[] args) throws Exception {

        StringBuilder sb = new StringBuilder("src/main/resources/");

        FileInputStream inputStream = new FileInputStream(new File(sb.append("a.txt").toString()));
        FileOutputStream outputStream = new FileOutputStream(sb.append("b.txt").toString());

        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();

        // 将输入流通道中的数据转移到输出流通道中
        outputStreamChannel.transferFrom(inputStreamChannel, 0, inputStreamChannel.size());

    }
}
