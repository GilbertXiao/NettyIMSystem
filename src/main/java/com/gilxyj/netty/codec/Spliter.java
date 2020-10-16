package com.gilxyj.netty.codec;

import com.gilxyj.netty.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @program: nettyIMSystem
 * @description:
 * @作者 飞码录
 * @微信公众号 飞码录
 * @网站 http://www.codesboy.cn
 * @国际站 http://www.codesboy.com
 * @微信 gilbertxy
 * @GitHub https://github.com/GilbertXiao
 * @Gitee https://gitee.com/gilbertxiao
 * @create: 2020-10-15 07:44
 **/
public class Spliter extends LengthFieldBasedFrameDecoder {

    //1.在我们的自定义协议中，我们的长度域在整个数据包的哪个地方，专业术语来说就是长度域相对整个数据包的偏移量是多少，这里显然是 4+1+1+1=7。
    //2.另外需要关注的就是，我们长度域的长度是多少，这里显然是 4。 有了长度域偏移量和长度域的长度，我们就可以构造一个拆包器。

    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;


    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //if (in.getInt(in.readerIndex())!= PacketCodeC.MAGIC_NUMBER)
        if (in.getInt(0)!= PacketCodeC.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
