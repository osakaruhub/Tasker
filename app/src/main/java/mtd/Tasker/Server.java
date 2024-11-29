package org.example;

import socketio.ServerSocket;
import socketio.Socket;
import java.io.IOException;
import java.util.ArrayList;

public class Server {
    private static final int port = 1234;
    private static final String host = "localhost";
    //private ArrayList<Clients> clients;
    private static ServerSocket sSocket;
    private Socket admin;
    private static ArrayList<Socket> Csockets;
    private static ArrayList<Thread> threads;
    private static Boolean close = false;

    public Server(){
        do {
            try {
                sSocket = new ServerSocket(port);
                System.out.println("starting server at " + host + ":" + port);
                System.out.println("listening for admin...");
                admin = sSocket.accept();
                // FIX: how do i make this multithreaded?
                AdminThread adminThread = new AdminThread(admin);
                adminThread.start();
            } catch (IOException e) {
                System.out.println("creating socket at " + host + ":" + port + " failed. trying again in 5 seconds...");
                continue;
            }
            System.out.println("listening for clients...");
            while (!close) {
                Socket socket = sSocket.accept();
                Thread clientThread = new ClientThread(socket);
                System.out.println("New client connected!");
                Csockets.add(socket);
                clientThread.start();
                threads.add(clientThread);
            }
        } while (condition);
    }

    public static void main(String[] args) {
        new Server();
    }
}

class ClientThread implements Runnable {
    Socket socket;
    static Boolean running = true;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public run() {
        while (running) {
            msg = socket.read();
            if (msg == null) {
                continue;
            }
            request = new Request(msg);
            if (request == null) {
                continue;
            }
            handleRequest(request);
        }
    }

    public void handleRequest(Request request) {
        //TODO: Handle Client Requests
        switch (request) {
            case Requests.ADD:
            //Event event = new Event(request.getContent());
            break;
            case Requests.REMOVE:

            break;
            case Requests.SYNC:

            break;
            case Requests.GET:

            break;
            default:
            break;
        }
    }

    void multicast(Request req, String content) {
        for (Socket socket : Csockets) {
            socket.write(req + " " + content +";");
        }
    }

    void close() {
        try {
            System.out.println("closing all clients...");
            for (Socket socket : Csockets) {
                socket.close();
            }
            System.out.println("closing the server...");
            sSocket.close();
            close = true;
        } catch (Exception e) {
            System.err.println("couldn't close safely");
            System.exit();
        }
        
    }

}

class AdminThread extends ClientThread {

    @Override
    public void handleRequest(Request request) {
        //TODO: Handle admin requests
        switch (request) {
            case Requests.ADD:
            Event event = new Event(request.getContent());
            break;
            case Requests.REMOVE:

            break;
            case Requests.SYNC:

            break;
            case Requests.GET:

            break;
            case Requests.GET:

            break;
            case Requests.LIST:
                listClients();
            break;
            case Requests.KICK:

            break;
            default:
            break;
        }
    }

    public void listClients() {
    }

    public void kick(int id) throws IOException {
        if (id < Csockets.size() - 1) {
            Csockets.get(id).close();
        }
    }
}
