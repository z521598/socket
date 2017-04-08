package com.baidu.first;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by langshiquan on 17/4/8.
 */
public class ServerMain {
    //搭建服务器端
    public static void main(String[] args) throws IOException {
        ServerMain socketService = new ServerMain();
        //1、a)创建一个服务器端Socket，即SocketService
        socketService.oneServer();
    }

    public void oneServer() {
        try {
            ServerSocket server = null;
            try {
                server = new ServerSocket(5209);
                //b)指定绑定的端口，并监听此端口。
                System.out.println("服务器启动成功");
                System.out.println("按\"0\"退出服务");
                //创建一个ServerSocket在端口5209监听客户请求
            } catch (Exception e) {
                System.out.println("没有启动监听：" + e);
                //出错，打印出错信息
            }
            Socket socket = null;
            try {
                socket = server.accept();
                //2、调用accept()方法开始监听，等待客户端的连接
                //使用accept()阻塞等待客户请求，有客户请求到来则产生一个Socket对象，并继续执行
            } catch (Exception e) {
                System.out.println("Error." + e);
                //出错，打印出错信息
            }
            //3、获取输入流，并读取客户端信息
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
                writer.println(line + new Date());   // 向"服务端"发送消息
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
            server.close(); //关闭ServerSocket
        } catch (Exception e) {//出错，打印出错信息
            e.printStackTrace();
        }
    }
}
