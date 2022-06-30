package com.coderdianxia.bio;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName: BioServerSocket
 * @Description:TODO
 * @Author:wengzx
 * @Date: 2022/6/29 9:36
 */
public class BioServerSocket {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9999);

            System.out.println("Waiting for client on port " +
                    serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("Just connected to "
                    + server.getRemoteSocketAddress());
            DataInputStream in =
                    new DataInputStream(server.getInputStream());
            System.out.println(in.readUTF());
            DataOutputStream out =
                    new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to "
                    + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
