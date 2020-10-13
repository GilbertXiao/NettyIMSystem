package com.gilxyj.netty.server;

import com.gilxyj.netty.codec.PacketDecoder;
import com.gilxyj.netty.codec.PacketEncoder;
import com.gilxyj.netty.server.handler.LoginRequestHandler;
import com.gilxyj.netty.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
 * @create: 2020-10-03 11:49
 **/
public class NettyServer {

    public static final int BEGIN_PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(new LoginRequestHandler());
                        nioSocketChannel.pipeline().addLast(new MessageRequestHandler());
                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    }
                });
        bind(serverBootstrap,BEGIN_PORT);

    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("端口【" + port + "】绑定成功！");
                    } else {
                        System.out.println("端口【" + port + "】绑定失败！");
                        bind(serverBootstrap, port + 1);
                    }
                });
    }

}
