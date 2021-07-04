package com.zeyuan.learn.learnnetty.ch2;

/**
 * 乖乖写文件注释
 *
 * @author zeyuan.jzy
 * @date 2021/7/4 19:43
 */
public class  ServerBoot {
    private static final int PORT = 8000;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.start();
    }
}
