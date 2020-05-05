package com.lx.netty.protocol_tcp;

/**
 * 发送的消息
 */
public class MessageProtocol {

    private int length;

    private byte[] bytes;

    public MessageProtocol(int length, byte[] bytes) {
        this.length = length;
        this.bytes = bytes;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
