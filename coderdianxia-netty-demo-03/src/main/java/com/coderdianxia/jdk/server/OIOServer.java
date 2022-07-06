package com.coderdianxia.jdk.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName: OIOServer
 * @Description:使用javaapi实现阻塞服务端
 * @Author:coderDianxia
 * @Date: 2022/6/30 14:40
 */
public class OIOServer {

    private final  int port;

    public OIOServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws IOException {
        new OIOServer(9992).start();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true) {
                System.out.println("Server waitting connect....");
                Socket clientSocket = serverSocket.accept();
                System.out.println("connected from...."+clientSocket.getLocalAddress());
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        OutputStream outputStream = null;
                        try {
                            outputStream = clientSocket.getOutputStream();
                            DataOutputStream stream = new DataOutputStream(outputStream);
                            stream.writeUTF("Server resp message.....");
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException ioException) {

                            }
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
