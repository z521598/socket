package com.baidu.third;

import java.net.ServerSocket;
import java.net.Socket;

import com.baidu.common.Var;

/**
 * Created by langshiquan on 17/4/8.
 */
public class ServerMain3 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Var.PORT);
            System.out.println("服务端启动成功");
            Socket socket = null;
            int count = 0;//记录客户端的数量
            while (true) {
                socket = serverSocket.accept();
                ServerThrea3 serverThrea3 = new ServerThrea3(socket);   // 新建线程处理业务
                serverThrea3.start();
                count++;
                System.out.println("客户端连接的数量：" + count);
            }
        } catch (Exception e) {
            System.out.println("服务端启动失败");
            e.printStackTrace();
        }
    }
}
