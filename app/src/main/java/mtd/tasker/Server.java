package mtd.tasker;

import socketio.ServerSocket;
import socketio.Socket;
import java.io.IOException;
import java.util.ArrayList;

import mtd.tasker.protocol.*;
import mtd.tasker.Serialisation;

public class Server {
    private static final int port = 1234;
    private static final String host = "localhost";
    public static String ADMIN_PASS = "test";
    public static ServerSocket sSocket;
    public static ArrayList<Clientp> clients = new ArrayList<>();
    public static Boolean close = false;

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
                    socket.write("connected!\n");
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
            byte[] msg = null;
            try {
                int len = socket.dataAvailable();
                if (len <= 0) {
                    continue;
                }
                int msgLen = socket.read(msg, len);
                if (msgLen == -1) {
                    continue;
                }
            } catch (IOException e) {
                System.out.println("Client " + id + " disconnected!: " + e.getMessage());
                running = false;
            }
            try {
                handleRequest((Request) Serialisation.deserialize(msg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // BUG: too many try catches to bother, dont wanna do it rn
    // also maybe put refactor more into Serverhandle, looks hideous rn
    void handleRequest(Request request) throws IOException {
        // TODO: Handle Client Requests
        byte[] response;
        switch (request.getRequestCode()) {
            case RequestCode.ADD:
            response = Serialisation.serialize(addEvent(request.getContent())?new Response(StatusCode.OK):new Response(StatusCode.SERVER_ERROR));
            socket.write(response, response.length);
                break;
            case RequestCode.DELETE:
            Response resp;
            try {
                resp = remove(request.getContent());
            } catch (Exception e) {resp = new Response(StatusCode.NOT_FOUND);}
            response = Serialisation.serialize(resp); 
            socket.write(response, response.length);
            break;
            case RequestCode.SYNC:
                response = Serialisation.serialize(sync());
                socket.write(response, response.length);
                break;
            case RequestCode.GET:
            String msg = request.getContent();
            get();
            break;
            case RequestCode.SU:
            if (ServerHandle.su(request.getContent())) {
                response = Serialisation.serialize(new Response(StatusCode.OK));
                socket.write(response, response.length);
                admin = true;
            } else {
                response = Serialisation.serialize(new Response(StatusCode.PERMISSION_ERROR));
                socket.write(response, response.length);
            }
            case RequestCode.LIST:
            if (admin) {
                response = Serialisation.serialize(new Response(StatusCode.OK, listClients()));
                socket.write(response, response.length);
                } else {
                    response = Serialisation.serialize(new Response(StatusCode.PERMISSION_ERROR));
                    socket.write(response, response.length);
                }
                break;
            case RequestCode.KICK:
                if (admin) {
                    try {
                        if (kick(Integer.parseInt(request.getContent()))) {
                        response = Serialisation.serialize(new Response(StatusCode.OK, listClients()));
                            socket.write(response, response.length);
                        } else {}
                    } catch (Exception e) {
                    response = Serialisation.serialize(new Response(StatusCode.PERMISSION_ERROR));
                        socket.write(response, response.length);
                    }
                }
                break;
            case RequestCode.EXIT:
                socket.close();
                running = false;
            case RequestCode.CLOSE:
                if (!admin) {
                    response = Serialisation.serialize(new Response(StatusCode.PERMISSION_ERROR));
                } else {
                multicast(new Request(RequestCode.EXIT));
                close();
            }
            default:
            response = Serialisation.serialize(new Response(StatusCode.BAD_REQUEST));
            socket.write(response, response.length);
            break;
        }
    }

    public Boolean sync() {
        return true;
    }

    public Boolean get() {return true;}

    private Boolean addEvent(String content) {
        String[] event = content.split(":");
        try {
            ServerHandle.events.add(new Event(event[0], event[1], Double.parseDouble(event[2]), Double.parseDouble(event[3]), event[4]));
            multicast(new Request(RequestCode.ADD, content));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private Boolean kick(int id) throws IOException {
        if (id < ServerHandle.events.size() - 1 && id >= 0) {
            Server.clients.get(id).socket.close();
            return true;
        } else {
            return false;
        }
    }

    private Response remove(String event) throws NumberFormatException{
        int id = Integer.parseInt(event);
        if (id >= 0 && Integer.parseInt(event) < ServerHandle.events.size()) {
            ServerHandle.events.remove(id);
            return new Response(StatusCode.OK);
        } else {
            return new Response(StatusCode.NOT_FOUND);
        }
    }

    private void multicast(Request req) {
        byte[] request = null;
        try {
        request = Serialisation.serialize(req);
        } catch (Exception e) {
        }
        for (Clientp client : Server.clients) {
            try {
            client.socket.write(request, request.length);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("couldnt send to client: " + client.id);
            }
        }
    }

    String listClients() {
        return "stub";
    } 

    private void close() {
        try {
            System.out.println("closing all clients...");
            for (Clientp client : Server.clients) {
                client.socket.close();
            }
            System.out.println("closing the server...");
            Server.sSocket.close();
            Server.close = true;
        } catch (Exception e) {
            System.err.println("couldn't close safely");
            System.exit(0);
        }
    }
}
