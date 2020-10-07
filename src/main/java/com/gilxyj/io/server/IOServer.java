package com.gilxyj.io.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: nettylearn
 * @description: Tradition Socket Server
 * @作者 飞码录
 * @微信公众号 飞码录
 * @网站 http://www.codesboy.cn
 * @国际站 http://www.codesboy.com
 * @微信 gilbertxy
 * @GitHub https://github.com/GilbertXiao
 * @Gitee https://gitee.com/gilbertxiao
 * @create: 2020-09-27 00:20
 **/
public class IOServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8000);

        new Thread(()->{
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(()->{
                        try {
                            int len;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println(new String(data,0,len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }

}
