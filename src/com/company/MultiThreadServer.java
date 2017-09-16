package com.company;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer implements Runnable {
    private Socket socket;
    MultiThreadServer(Socket socket) {
        this.socket = socket;
    }
    public static void main(String args[]) throws Exception {
        ServerSocket serverSock = new ServerSocket(1234);
        System.out.println("Listening");

        while (true) {
            Socket sock = serverSock.accept();
            System.out.println("Connected");
            new Thread(new MultiThreadServer(sock)).start();
        }
    }
    public void run() {
        System.out.println("Invoke -> " + Thread.currentThread().getName());
        try {
            PrintStream stream = new PrintStream(socket.getOutputStream());
            for (int i = 100; i >= 0; i--) {
                stream.println(i + " bottles of beer on the wall");
            }
            stream.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
