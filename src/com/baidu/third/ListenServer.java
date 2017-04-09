package com.baidu.third;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.baidu.common.Var;

/**
 * Created by langshiquan on 17/4/9.
 */

// 监听器
public class ListenServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Var.LISTEN_PORT);
            System.out.println("监听服务已初始化完毕");
            while (true) {
                System.out.println("等待信息");
                Socket socket = serverSocket.accept();
                System.out.println("正在打开通道...");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("正在打印信息...");
                String msg = bufferedReader.readLine();
                System.out.println(new Date());
                System.out.println(socket.getInetAddress() + ":" + socket.getPort());
                System.out.println("message: " + msg);
                System.out.println("=======================");
                bufferedReader.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
