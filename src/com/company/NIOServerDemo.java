package com.company;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NIOServerDemo {
    static private int BLOCK = 4096;
    static private int PORT = 8888;
    Map<Integer, ByteBuffer> buffers = new HashMap();
    private Selector selector;
    private String received;

    public NIOServerDemo() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(PORT));
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server Start: " + PORT);
    }

    public static void main(String[] args) throws IOException {
        NIOServerDemo server = new NIOServerDemo();
        server.execute();
    }

    private void execute() throws IOException {
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                //this is the event loop
                SelectionKey key = iterator.next();
                iterator.remove();
                handleEvent(key);
            }
        }
    }

    private void handleEvent(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel server;
        SocketChannel client;
        String receiveText;
        int count;
        if (selectionKey.isAcceptable()) {
            server = (ServerSocketChannel) selectionKey.channel();
            client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            buffers.putIfAbsent(client.socket().hashCode(), ByteBuffer.allocate(BLOCK));
        } else if (selectionKey.isReadable()) {
            client = (SocketChannel) selectionKey.channel();
            ByteBuffer receiveBuffer = buffers.get(Integer.valueOf(client.socket().hashCode()));
            receiveBuffer.clear();
            count = client.read(receiveBuffer);
            if (count > 0) {
                receiveBuffer.flip();
                receiveText = new String(receiveBuffer.array(), 0, count);
                System.out.println("Received :" + receiveText);
                received = receiveText;
                client.register(selector, SelectionKey.OP_WRITE);
            }
            else {
                System.out.println("Socket closed");
                buffers.remove(Integer.valueOf(client.socket().hashCode()));
                client.close();
                selectionKey.cancel();
            }
        } else if (selectionKey.isWritable()) {
            client = (SocketChannel) selectionKey.channel();
            ByteBuffer sendBuffer = buffers.get(Integer.valueOf(client.socket().hashCode()));
            client.write(sendBuffer);
            client.register(selector, SelectionKey.OP_READ);
        }
    }
} 