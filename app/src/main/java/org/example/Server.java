package org.example;

import socketio.ServerSocket;
import socketio.Socket;
import java.util.Scanner;

public class Server {
    private static final int port = 1234;
    private static final String host = "localhost";
    private ArrayList<Clients> clients;
    private static Boolean close = false;

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

    public void close() {}
    public void listClients() {}
    public void kick(String client) {}

    public static void main(String[] args) {
        new Thread(Server::listenForCommands).start();
        do {
            try (ServerSocket sSocket = new ServerSocket(port)){
                System.out.println("starting server at " + host + ":" + port);
                System.out.println("listening for clients...");
                while (!close) {
                    Socket socket = sSocket.accept();
                    System.out.println("New client connected: " + socket.getInetAddress());
                }
            } catch (IOException e) {
                System.out.println("creating socket at " + host + ":" + port + " failed. trying again in 5 seconds...");
                continue;
            }
            break;
        } while (condition);
    }
}
