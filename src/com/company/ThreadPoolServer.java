package com.company;

import java.io.IOException;
import java.io.PrintStream;
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
                    PrintStream stream = new PrintStream(socket.getOutputStream());
                    for (int i = 10; i >= 0; i--) {
                        stream.println(i + " bottles of beer on the wall");
                    }
                    stream.close();
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            });
        }
    }
}


