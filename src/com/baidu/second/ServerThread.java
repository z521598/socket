package com.baidu.second;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by langshiquan on 17/4/8.
 */
public class ServerThread extends Thread {

    Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            String line;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //由Socket对象得到输入流，并构造相应的BufferedReader对象
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            //由Socket对象得到输出流，并构造PrintWriter对象
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            //由系统标准输入设备构造BufferedReader对象
            System.out.println("Client:" + in.readLine());  // 阻塞，监听，等待"客户端"信息
            //在标准输出上打印从客户端读入的字符串
            line = br.readLine();// 阻塞，等待"服务端"输入消息
            //从标准输入读入一字符串
            //4、获取输出流，响应客户端的请求
            while (!line.equals("0")) {
                //如果该字符串为 "0"，则停止循环
                writer.println(line);   // 向"服务端"发送消息
                //向客户端输出该字符串
                writer.flush();
                //刷新输出流，使Client马上收到该字符串
                //在系统标准输出上打印读入的字符串
                String clientMsg = in.readLine();   // 阻塞，监听，等待"客户端"信息
                System.out.println("Client:" + clientMsg);
                //从Client读入一字符串，并打印到标准输出上
                line = br.readLine();
                //从系统标准输入读入一字符串
            } //继续循环

            //5、关闭资源
            writer.close(); //关闭Socket输出流
            in.close(); //关闭Socket输入流
            socket.close(); //关闭Socket
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
