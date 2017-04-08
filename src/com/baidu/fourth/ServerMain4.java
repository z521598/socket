package com.baidu.fourth;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by langshiquan on 17/4/8.
 */
public class ServerMain4 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5209);
            System.out.println("服务端启动成功");
            Socket socket = null;
            int count = 0;//记录客户端的数量
            while (true) {
                socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket);   // 新建线程处理业务
                serverThread.start();
                count++;
                System.out.println("客户端连接的数量：" + count);
            }
        } catch (Exception e) {
            System.out.println("服务端启动失败");
            e.printStackTrace();
        }
    }
}
