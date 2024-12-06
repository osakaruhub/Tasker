package mtd.tasker;

import socketio.ServerSocket;
import socketio.Socket;
import java.io.IOException;
import java.util.ArrayList;
import mtd.tasker.protocol.*;

public class Server {
    private static final int port = 1234;
    private static final String host = "localhost";
    private static String ADMIN_PASS = "test";
    private static ServerSocket sSocket;
    private static ArrayList<Clientp> clients = new ArrayList<>();
    private static Boolean close = false;

    public Server() {
        while (true) {
            try {
                sSocket = new ServerSocket(port);
                System.out.println("starting server at " + host + ":" + port);
                System.out.println("listening for clients...");
                while (!close) {
                    Socket socket = sSocket.accept();
                    Thread clientThread = new Thread(new ClientThread(socket));
                    System.out.println("New client connected!");
                    clientThread.start();
                    clients.add(new Clientp(socket, clientThread)); 
                }
            } catch (IOException e) {
                System.out.println("creating socket at " + host + ":" + port + " failed. trying again in 5 seconds...");
                continue;
            }
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}

class ClientThread implements Runnable {
    Socket socket;
    static int ID = 0;
    private int id;
    Boolean running = true;
    private Boolean admin = false;

    public ClientThread(Socket socket) {
        this.socket = socket;
        id = ++ID;
    }

    public void run() {
        while (running) {
            String msg = socket.readLine();
            if (msg == null) {
                continue;
            }
            for (String request: msg.split(";")) { 
              handleRequest(request.split(" "));
            }
        }
    }

    void handleRequest(Request request) {
        // TODO: Handle Client Requests
        switch (request.getRequestCode()) {
            case RequestCode.ADD:
            socket.write(addEvent(request.getContent())?StatusCode.OK:StatusCode.INTERNAL_SERVER_ERROR);
                break;
            case RequestCode.REMOVE:
            try {
                Response response = remove(request.getContent());
            } catch (Exception e) {status = new Response(StatusCode.NOT_FOUND);}
            write(status);
            break;
            case RequestCode.SYNC:
            sync();
            break;
            case RequestCode.GET:
            String msg = request.getContent();
            get();
            break;
            case RequestCode.SU:
            if (request.getContent().equals(ADMIN_PASS)) {
                admin = true;
            } else {
                socket.write(new Status(PERMISSION_ERROR));
            }
            case RequestCode.LIST:
            if (admin) {
                socket.write(new Status(OK, listClients()));
            } else {
                socket.write(new Status(PERMISSION_ERROR));
            }
            break;
            case RequestCode.KICK:
            if (admin) {
                try {
                kick();
                    socket.write(StatusCode.OK);
                } catch (Exception e) {
                    socket.write(StatusCode.INTERNAL_SERVER_ERROR);
                }
            }
            break;
            default:
            break;
        }
    }

    public Boolean sync() {
        return true;
    }

    private Boolean addEvent(String content) {
        event = content.split(":");
        try {
            events.add(new Event(event[0], event[1], Double.parseDouble(event[2]), Double.parseDouble(event[3]), event[4]));
            multicast(StatusCode.ADD + content, id);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void kick(int id) throws IOException {
        if (id < Csockets.size() - 1 && id >= 0) {
            Csockets.get(id).close();
        }
    }

    private Status remove(String event) throws NumberFormatException{
        int id = Integer.parseInt(event);
        if (id >= 0 && Integer.parseInt(event) < handler.events.size()) {
            events.remove(id);
            return new Response(StatusCode.OK);
        }
    }

    private void multicast(Request req, String content) {
        for (Socket socket : Csockets) {
            socket.write(req + " " + content + ";");
        }
    }

    String listClients() {
        return "stub";
    } 

    @Override
    public String toString() {
        return id + " - " + ip;
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
            System.exit(0);
        }
    }
}
