package com.coderdianxia.jdk.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: JdkClientSocket
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/6/30 14:32
 */
public class NioClientSocket {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();

            boolean connect = socketChannel.connect(new InetSocketAddress("localhost", 9991));
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ);
            if(connect)
            {
                ByteBuffer byteBuffer = ByteBuffer.wrap("nio client send Msg".getBytes(StandardCharsets.UTF_8));
                socketChannel.write(byteBuffer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
