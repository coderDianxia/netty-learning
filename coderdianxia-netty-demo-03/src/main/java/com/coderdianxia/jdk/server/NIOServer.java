package com.coderdianxia.jdk.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName: NIOServer
 * @Description: 使用javaapi实现非阻塞服务端
 * @Author:coderDianxia
 * @Date: 2022/6/30 14:40
 */
public class NIOServer {
    private final  int port;

    public NIOServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new NIOServer(9991).start();
    }

    public void start() throws Exception {

        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket serverSocket = serverChannel.socket();

        InetSocketAddress socketAddress = new InetSocketAddress(port);
        serverSocket.bind(socketAddress);

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer byteBuffer = ByteBuffer.wrap("JDK NIO Server resp msg".getBytes(StandardCharsets.UTF_8));
        while(true){
            int select = selector.select();
            System.out.println("select:"+select);

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            while(selectionKeyIterator.hasNext()){
                SelectionKey selectionKey = selectionKeyIterator.next();
                selectionKeyIterator.remove();

                if(selectionKey.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
                    SocketChannel clientSocket = server.accept();
                    clientSocket.configureBlocking(false);
                    clientSocket.register(selector, SelectionKey.OP_WRITE |
                            SelectionKey.OP_READ, byteBuffer.duplicate());    //7
                    System.out.println(
                            "Accepted connection from " + clientSocket);
                }
                if(selectionKey.isWritable())
                {
                    SocketChannel client =
                            (SocketChannel)selectionKey.channel();
                    ByteBuffer buffer =
                            (ByteBuffer)selectionKey.attachment();
                    while (buffer.hasRemaining()) {
                        if (client.write(buffer) == 0) {        //9
                            break;
                        }
                    }
                    client.close();
                }

            }


        }
    }

}
