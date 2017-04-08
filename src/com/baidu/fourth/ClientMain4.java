package com.baidu.fourth;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by langshiquan on 17/4/8.
 */
public class ClientMain4 {
    // 搭建客户端
    public static void main(String[] args) throws IOException {
        try {
            // 1、创建客户端Socket，指定服务器地址和端口
            // 向本机的52000端口发出客户请求
            Socket socket = new Socket("192.168.1.6", 5209);
            System.out.println("客户端启动成功");
            System.out.println("0.退出服务");
            Scanner scanner = new Scanner(System.in);
            // 由系统标准输入设备构造BufferedReader对象
            // 2、获取输出流，向服务器端发送信息
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter write = new PrintWriter(socket.getOutputStream());
            // 由Socket对象得到输出流，并构造PrintWriter对象
            //3、获取输入流，并读取服务器端的响应信息
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 由Socket对象得到输入流，并构造相应的BufferedReader对象
            String readline;
            System.out.println("请输入您要获取的文件全目录：");
            readline = br.readLine(); // 从系统标准输入读入一字符串
            if (!readline.equals("0")) {
                // 若从标准输入读入的字符串为 "end"则停止循环
                write.println(readline);
                // 将从系统标准输入读入的字符串输出到Server
                write.flush();
                // 刷新输出流，使Server马上收到该字符串
                // 用于装从服务器获取到的流
                File file = new File(readline + ".dbl");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                int len = 0;
                byte[] bytes = new byte[1024];
                System.out.println("开始接受数据");
                while ((len = dataInputStream.read(bytes, 0, bytes.length)) > 0) {
                    fileOutputStream.write(bytes, 0, len);
                }
                System.out.println("接受完毕");
                fileOutputStream.close();
                dataInputStream.close();
            } // 继续循环
            //4、关闭资源
            write.close(); // 关闭Socket输出流
            in.close(); // 关闭Socket输入流
            socket.close(); // 关闭Socket
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
