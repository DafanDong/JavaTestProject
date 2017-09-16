package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    }
}
