package com.zeyuan.learn.learnnetty.ch2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

/**
 * 乖乖写文件注释
 *
 * @author zeyuan.jzy
 * @date 2021/7/4 19:44
 */
@Slf4j
public class Server {
    private ServerSocket serverSocket;

    public Server(int port ) {
        try {
            this.serverSocket = new ServerSocket(port);
            log.info("server started successfully. port={}", port  );
        } catch (IOException e) {
            log.error("server started failed.", e);
        }
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doStart();
            }
        }).start();
    }

    private void doStart() {
        while (true) {
            try {
                // accept()是阻塞方法
                Socket client = serverSocket.accept();
                new ClientHandler(client).start();
            } catch (IOException e) {
                log.error("服务端异常");
            }

        }
    }
}
