package org.example;

import socketio.ServerSocket;
import socketio.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {
    private static final int port = 1234;
    private static final String host = "localhost";
    //private ArrayList<Clients> clients;
    private ServerSocket sSocket;
    private ArrayList<java.net.Socket> Csockets;
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

    public void broadcast(Request req, String content) {
        for (Socket socket : Csockets) {
            socket.write(req + " " + content +";");
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

    public void listClients() {
        String str;
        for (Socket socket : Csockets) {
            str += socket.getInetAddress() + "\n";
        }
        System.out.println(str);
    }

    public void kick(int id) throws IOException {
        if (id < Csockets.size() - 1) {
            Csockets.get(id).close();
        }
    }

    public static void main(String[] args) {
        new Thread(Server::listenForCommands).start();
        new Server();
    }
}
