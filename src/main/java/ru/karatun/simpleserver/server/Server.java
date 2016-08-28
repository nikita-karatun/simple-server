package ru.karatun.simpleserver.server;

import ru.karatun.simpleserver.concurrent.ThreadPool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class Server {

    public Server(int port) {

        ThreadPool threadPool = new ThreadPool(40);

        try {
            ServerSocket serverSocket = new ServerSocket(port, 10);
            for (; ; ) {
                Socket socket = serverSocket.accept();

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                threadPool.execute(() -> {
                    RequestHandler.getInstance().handleRequest(inputStream, outputStream);
                });
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Server(8080);
    }


}
