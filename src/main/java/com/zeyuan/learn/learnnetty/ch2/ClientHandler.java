package com.zeyuan.learn.learnnetty.ch2;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

/**
 * 服务端在客户端有传入数据时的动作
 *
 * @author zeyuan.jzy
 * @date 2021/7/4 19:50
 */
@Slf4j
public class ClientHandler {
    private static final int MAX_DATA_LEN = 1024;
    private final Socket socket;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    public void start() {
        log.info("新客户端接入");
        new Thread(new Runnable() {

            @Override
            public void run() {
                doStart();
            }
        }).start();
    }

    private void doStart() {
        try {
            InputStream inputStream = socket.getInputStream();
            while (true) {
                byte[] bytes = new byte[MAX_DATA_LEN];
                int len;
                while ((len = inputStream.read(bytes)) != -1) {
                    String message = new String(bytes, 0, len);
                    log.info("客户端传来消息" + message);
                    socket.getOutputStream().write(bytes);
                }
            }
        } catch (IOException e) {
            log.error("socket.getInputStream() failed.", e);
        }
    }
}
