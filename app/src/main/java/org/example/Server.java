package org.example;

import socketio.ServerSocket;
import socketio.Socket;
import java.util.Scanner;
import java.util.ArrayList;

public class Server {
    private static final int port = 1234;
    private static final String host = "localhost";
    //private ArrayList<Clients> clients;
    private ServerSocket sSocket;
    private ArrayList<Socket> Csockets;
    private static Boolean close = false;

    public Server(){
        do {
            try {
                sSocket = new ServerSocket(port);
                System.out.println("starting server at " + host + ":" + port);
                System.out.println("listening for clients...");
                while (!close) {
                    Socket socket = sSocket.accept();
                    System.out.println("New client connected: " + socket.getInetAddress());
                    Csockets.add(socket);
                }
            } catch (IOException e) {
                System.out.println("creating socket at " + host + ":" + port + " failed. trying again in 5 seconds...");
                continue;
            }
            break;
        } while (condition);
    }    

    public void listenForCommands() {
        String command;
        Scanner sc = new Scanner(System.in);
        while (running) {
            command = sc.next();
            if (command != null) {
                if (command.equals("close")) {
                    close();
                } else if (command.equals("clients")) {
                    listClients();
                } else if (command.substring(0,4).equals(("kick"))) {
                    kick(command.split(' ', 2)[1]);
                }
            }
        }
    }

    public void close() {
        close = true;
        try {
            System.out.println("closing all clients...");
            for (Socket socket : Csockets) {
                socket.close();
            }
            System.out.println("closing the server...");
            sSocket.close();
        } catch (Exception e) {
            System.err.println("couldn't close safely");
            System.exit();
        }
        
    }
    public void listClients() {}
    public void kick(String client) {}

    public static void main(String[] args) {
        new Thread(Server::listenForCommands).start();
        new Server();
    }
}
