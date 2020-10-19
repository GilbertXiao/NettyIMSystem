package com.gilxyj.netty.client;


import com.gilxyj.netty.client.console.ConsoleCommandManager;
import com.gilxyj.netty.client.console.LoginConsoleCommand;
import com.gilxyj.netty.client.handler.CreateGroupResponseHandler;
import com.gilxyj.netty.client.handler.LoginResponseHandler;
import com.gilxyj.netty.client.handler.LogoutResponseHandler;
import com.gilxyj.netty.client.handler.MessageResponseHandler;
import com.gilxyj.netty.codec.PacketDecoder;
import com.gilxyj.netty.codec.PacketEncoder;
import com.gilxyj.netty.codec.Spliter;
import com.gilxyj.netty.protocol.request.LoginRequestPacket;
import com.gilxyj.netty.protocol.request.MessageRequestPacket;
import com.gilxyj.netty.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
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
 * @create: 2020-10-03 15:47
 **/
public class NettyClient {

    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //socketChannel.pipeline().addLast(new FirstClientHandler());
                        //socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        socketChannel.pipeline().addLast(new Spliter());
                        socketChannel.pipeline().addLast(new PacketDecoder());
                        socketChannel.pipeline().addLast(new LoginResponseHandler());
                        socketChannel.pipeline().addLast(new LogoutResponseHandler());
                        socketChannel.pipeline().addLast(new MessageResponseHandler());
                        socketChannel.pipeline().addLast(new CreateGroupResponseHandler());
                        socketChannel.pipeline().addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            }else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                //还剩多少次连接
                int leftRetry = retry - 1;
                if (leftRetry == 0) {
                    System.err.println("重试次数已经用完，放弃连接");
                }
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {

        Scanner scanner = new Scanner(System.in);
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                }else{
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }


}
