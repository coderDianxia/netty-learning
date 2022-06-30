package com.coderdianxia.bio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName: BioServerSocket
 * @Description:TODO
 * @Author:wengzx
 * @Date: 2022/6/29 9:36
 */
public class BioServerSocket1 implements Runnable{
    private  ServerSocket serverSocket;

    public BioServerSocket1(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            new Thread(new BioServerSocket1(serverSocket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("Just connected to "
                        + server.getRemoteSocketAddress());
                DataInputStream in =
                        new DataInputStream(server.getInputStream());
                String s = in.readUTF();
                if(s.equals("stop")) {
                    break;
                }
                System.out.println("receive client data:"+s);
                DataOutputStream out =
                        new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for connecting to "
                        + server.getLocalSocketAddress() + "\nGoodbye!");
            }catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
