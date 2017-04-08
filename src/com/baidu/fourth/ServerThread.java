package com.baidu.fourth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

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
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 请求的文件名
            String fileName = in.readLine();
            System.out.println(Thread.currentThread().getId() + " request for fileName");
            // 用于回传的流
            File reuqestedFile = new File(fileName);
            if (reuqestedFile.exists()) {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                FileInputStream fileInputStream = new FileInputStream(reuqestedFile);
                int length = 0;
                byte[] bytes = new byte[1024];
                System.out.println("开始传输文件");
                long size = reuqestedFile.length();
                double temp = 0.0;
                while ((length = fileInputStream.read(bytes, 0, bytes.length)) > 0) {
                    dataOutputStream.write(bytes, 0, length);
                    dataOutputStream.flush();
                    temp = temp + length;
                    System.out.println("已传输" + (temp / size) * 100 + "%");
                }
                System.out.println("传输文件结束");
                dataOutputStream.close();
                fileInputStream.close();
            } else {
                System.out.println("no such file");
            }
            in.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

}
