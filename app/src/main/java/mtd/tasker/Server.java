package mtd.tasker;

import socketio.ServerSocket;
import socketio.Socket;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import mtd.tasker.protocol.*;

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
                    String ip = socket.readLine();
                    System.out.println("New client (" + ip + ") connected!");
                    clientThread.start();
                    clients.add(new Clientp(socket, clientThread, ip)); 
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
                if (socket.read(msg,len) == -1) {
                    continue;
                }
            } catch (IOException e) {
                System.out.println("Client " + id + " disconnected!: " + e.getMessage());
                running = false;
            }
            Request req = (Request) Serialisation.deserialize(msg);
            System.out.println("command gotten: " + req.toString());
            try {
                handleRequest(req);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // BUG: too many try catches to bother, dont wanna do it rn
    // also maybe refactor more into Serverhandle, looks hideous rn
    // of course when database is not yet ready
    void handleRequest(Request request) throws IOException {
        // TODO: Handle Client Requests
        byte[] response;
        switch (request.getRequestCode()) {
            case RequestCode.ADD:
                // NOTE: change this when changing to a database
                response = Serialisation.serialize(addEvent(request.getContent())?new Response(StatusCode.OK):new Response(StatusCode.SERVER_ERROR));
                socket.write(response, response.length);
            break;
            case RequestCode.DELETE:
                Response resp;
                // NOTE: change this when changing to a database
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
                String cmd[] = msg.split(";");
                // NOTE: change this when changing to a database
                switch (cmd[0]) {
                    case "person":
                        response = Serialisation.serialize(ServerHandle.getByPerson(cmd[1]));
                    break;
                    case "tag":
                        response = Serialisation.serialize(ServerHandle.getByTag(cmd[1]));
                    break;
                    default:
                        response = Serialisation.serialize(ServerHandle.getByPerson(cmd[1]));
                    break;
                }
                socket.write(response, response.length);
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
            break;
            case RequestCode.LIST:
            // NOTE: change this when changing to a database
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
            break;
            case RequestCode.CLOSE:
                if (!admin) {
                    response = Serialisation.serialize(new Response(StatusCode.PERMISSION_ERROR));
                } else {
                multicast(new Request(RequestCode.EXIT));
                close();
            }
            break;
            default:
                response = Serialisation.serialize(new Response(StatusCode.BAD_REQUEST));
                socket.write(response, response.length);
            break;
        }
    }

    public Boolean sync() {
        return true;
    }

    private Boolean addEvent(String content) {
        String[] event = content.split(":");
        try {
            EventHandler.addEvent(new Event(event[0], event[1], event[2], event[3]));
        } catch (ParseException e) {
            return false;
        }
        multicast(new Request(RequestCode.ADD, content));
        return true;
    }

    private Boolean kick(int id) throws IOException {
        if (id < ServerHandle.events.size() - 1 && id >= 0) {
            Server.clients.get(id).getSocket().close();
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
            client.getSocket().write(request, request.length);
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
                client.getSocket().close();
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

class HeartbeatChecker implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); // Check every second
                checkThreads();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void checkThreads() {
        // Iterate through the list and remove dead threads
        Server.clients.removeIf(client -> !client.getThread().isAlive());
    }
}
