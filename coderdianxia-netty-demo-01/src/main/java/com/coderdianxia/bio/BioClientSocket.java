package com.coderdianxia.bio;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: BioClientSocket
 * @Description:TODO
 * @Author:wengzx
 * @Date: 2022/6/29 9:36
 */
public class BioClientSocket {

    public static void main(String[] args) throws IOException, InterruptedException {

        Socket client = new Socket("127.0.0.1", 9999);
        OutputStream outToServer = client.getOutputStream();
        DataOutputStream out =
                new DataOutputStream(outToServer);

        out.writeUTF("Hello from "
                + client.getLocalSocketAddress());
        InputStream inFromServer = client.getInputStream();

        DataInputStream in =
                new DataInputStream(inFromServer);
        System.out.println("Server says " + in.readUTF());

        client.close();
    }
}
