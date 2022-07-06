package com.coderdianxia.jdk.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @ClassName: JdkClientSocket
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/6/30 14:32
 */
public class OioClientSocket {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9992);
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream outputStream1 = new DataOutputStream(outputStream);
            outputStream1.writeUTF("客户端发送数据");

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String s = dataInputStream.readUTF();
            System.out.println("客户端接收服务端数据"+s);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
