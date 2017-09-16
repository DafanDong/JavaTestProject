package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dafadong on 8/23/17.
 */

public class ThreadPoolServer {
    public static void main(String args[]) throws Exception {
        ServerSocket serverSock = new ServerSocket(5678);
        System.out.println("Listening");
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            Socket socket = serverSock.accept();
            System.out.println("Connected");
            executorService.execute(() ->
            {
                System.out.println("Invoke -> " + Thread.currentThread().getName());
                try {
                    InputStream in = socket.getInputStream();
                    OutputStream out = socket.getOutputStream();
                    byte[] b = new byte[4 * 1024];
                    int len;
                    while ((len = in.read(b)) >= 0) {
                        out.write(b, 0, len);
                    }
                    System.out.println("Remote client close");
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            });
        }
    }
}


