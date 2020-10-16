package com.gilxyj.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

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
 * @create: 2020-10-03 11:59
 **/
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //收数据逻辑
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + ":客户端读到的数据 -> " + byteBuf.toString(Charset.forName("utf-8")));


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ":客户端写出数据");

        for (int i = 0; i < 1000; i++) {
            //1.获取数据
            ByteBuf buf = getByteBuf(ctx);

            //2.写数据
            ctx.channel().writeAndFlush(buf);
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        //1.获取二进制抽象ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        //2.准备数据，执行字符串的字符集
        byte[] bytes = "Hello,Java大佬,我要向您好好学习!".getBytes(Charset.forName("utf-8"));
        //3.填充数据到ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
