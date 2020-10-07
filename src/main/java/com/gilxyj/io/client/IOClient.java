package com.gilxyj.io.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @program: nettylearn
 * @description: Socket Client
 * @作者 飞码录
 * @微信公众号 飞码录
 * @网站 http://www.codesboy.cn
 * @国际站 http://www.codesboy.com
 * @微信 gilbertxy
 * @GitHub https://github.com/GilbertXiao
 * @Gitee https://gitee.com/gilbertxiao
 * @create: 2020-09-27 00:33
 **/
public class IOClient {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    Socket socket = new Socket("127.0.0.1", 8000);
                    while (true) {
                        try {
                            socket.getOutputStream().write((new Date() + ":hello world").getBytes());
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();
        }

    }
}
