package com.gilxyj.netty.util;

import java.util.UUID;

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
 * @create: 2020-10-19 12:14
 **/
public class IDUtil {
    public static String randomId(){
        return UUID.randomUUID().toString().split("-")[0];
    }
}
