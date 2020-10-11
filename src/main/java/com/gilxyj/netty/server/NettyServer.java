package com.gilxyj.netty.server;

import com.gilxyj.netty.server.handler.inbound.InBoundHandlerA;
import com.gilxyj.netty.server.handler.inbound.InBoundHandlerB;
import com.gilxyj.netty.server.handler.inbound.InBoundHandlerC;
import com.gilxyj.netty.server.handler.outbound.OutBoundHandlerA;
import com.gilxyj.netty.server.handler.outbound.OutBoundHandlerB;
import com.gilxyj.netty.server.handler.outbound.OutBoundHandlerC;
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
                        //inBound 处理读数据的逻辑链
                        nioSocketChannel.pipeline().addLast(new InBoundHandlerA());
                        nioSocketChannel.pipeline().addLast(new InBoundHandlerB());
                        nioSocketChannel.pipeline().addLast(new InBoundHandlerC());

                        //outBound 处理写数据的逻辑链
                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerA());
                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerB());
                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerC());

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
