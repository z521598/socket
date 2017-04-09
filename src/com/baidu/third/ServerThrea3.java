package com.baidu.third;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.baidu.common.Var;

/**
 * Created by langshiquan on 17/4/8.
 */
public class ServerThrea3 extends Thread {


    Socket socket = null;

    public ServerThrea3(Socket socket) {
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
            synMsg(line);
            //4、获取输出流，响应客户端的请求
            while (!line.equals("0")) {
                //如果该字符串为 "0"，则停止循环
                writer.println(line + new Date());   // 向"服务端"发送消息
                //向客户端输出该字符串
                writer.flush();
                synMsg(line);
                //刷新输出流，使Client马上收到该字符串
                //在系统标准输出上打印读入的字符串
                String clientMsg = in.readLine();   // 阻塞，监听，等待"客户端"信息
                synMsg(clientMsg);
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

    /**
     * 将msg同步给监听器
     * @param msg
     * @throws IOException
     */

    public synchronized void synMsg(final String msg) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket ltnSocket = new Socket(Var.IP, Var.LISTEN_PORT);
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                            ltnSocket.getOutputStream()));
                    System.out.println("与监听器连接已建立");
                    System.out.println(Thread.currentThread().getId() + "同步消息给监听器");
                    bufferedWriter.write(msg);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    ltnSocket.close();
                    System.out.println(Thread.currentThread().getId() + ": " + msg + "消息已发出");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
