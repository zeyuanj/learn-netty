package com.zeyuan.learn.learnnetty.ch2;

import java.io.IOException;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

/**
 * 乖乖写文件注释
 *
 * @author zeyuan.jzy
 * @date 2021/7/4 19:57
 */
@Slf4j
public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    private static final int SLEEP_TIME = 5000;

    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket(HOST, PORT);
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("客户端启动成功!");
                while (true) {
                    try {
                        String message = "Hello, world!";
                        log.info("客户端发送数据: {}", message);
                        clientSocket.getOutputStream().write(message.getBytes());
                    } catch (IOException e) {
                        log.error("客户端写socket报错");
                    }

                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

    }
}
