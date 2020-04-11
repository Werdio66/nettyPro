package com.lx.nio;

import java.nio.IntBuffer;

public class BufferTest {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(4);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i + 1);
        }

//        intBuffer.flip();
        intBuffer.position(0);
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
