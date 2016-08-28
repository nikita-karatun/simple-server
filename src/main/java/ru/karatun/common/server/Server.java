package ru.karatun.common.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class Server {

    private boolean stopped;

    public Server(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port, 10);
            for (;;) {
                Socket socket = serverSocket.accept();

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                RequestHandler.getInstance().handleRequest(inputStream, outputStream);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Server(8080);
    }



}
