package ru.ergakov.gb;

import java.io.IOException;
import java.net.ServerSocket;

public class Program {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4500);
            Server server = new Server(serverSocket);
            server.runServer();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }

    }

}