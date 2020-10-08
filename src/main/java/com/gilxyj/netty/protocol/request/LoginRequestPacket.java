package com.gilxyj.netty.protocol.request;


import com.gilxyj.netty.protocol.Packet;
import lombok.Data;

import static com.gilxyj.netty.protocol.command.Command.LOGIN_REQUEST;


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
 * @create: 2020-10-05 17:37
 **/
@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;


    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
