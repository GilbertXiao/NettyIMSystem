package com.gilxyj.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @program: nettylearn
 * @description:
 * @作者 飞码录
 * @微信公众号 飞码录
 * @网站 http://www.codesboy.cn
 * @国际站 http://www.codesboy.com
 * @微信 gilbertxy
 * @GitHub https://github.com/GilbertXiao
 * @Gitee https://gitee.com/gilbertxiao
 * @create: 2020-09-30 00:01
 **/
public class NettyClient {

    public static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                //1.指定线程模型
                .group(workerGroup)
                //2.指定IO类型为NIO
                .channel(NioSocketChannel.class)
                //3.IO处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                    }
                });
        //4.建立连接
        /*bootstrap.connect("127.0.0.1", 80).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else {
                System.err.println("连接失败！");
            }
        });*/
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("连接成功");
                    } else {
                        //第几次连接
                        int order = (MAX_RETRY - retry) + 1;
                        //还剩多少次连接
                        int leftRetry = retry - 1;
                        if (leftRetry == 0) {
                            System.err.println("重试次数已经用完，放弃连接");
                        }
                        //2的order次方
                        int delay = 1 << order;

                        System.err.println(new Date() + ":连接失败，第" + order + "次重连...");

                        bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, leftRetry), delay, TimeUnit.SECONDS);
                    }
                });
    }
}
