package mtd.tasker;

import socketio.ServerSocket;
import socketio.Socket;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import mtd.tasker.protocol.*;

public class Server {
    private static final String DEF_HOST = "localhost";
    private static final int DEF_PORT = 1234;
    private int port;
    private String host;
    public static String ADMIN_PASS = "test";
    public static ServerSocket sSocket;
    public static ArrayList<Clientp> clients = new ArrayList<>();
    public static Boolean close = false;

    public Server(String host, int port) throws InterruptedException {
        this.host = host;
        this.port = port;
        while (true) {
            try {
                sSocket = new ServerSocket(this.port);
                System.out.println("starting server at " + this.host + ":" + this.port);
                System.out.println("listening for clients...");
                new Thread(new HeartbeatChecker()).start();
                while (!close) {
                    Socket socket = sSocket.accept();
                    Thread clientThread = new Thread(new ClientThread(socket));
                    String ip = socket.readLine();
                    System.out.println("New client (" + ip + ") connected!");
                    clientThread.start();
                    clients.add(new Clientp(socket, clientThread, ip)); 
                }
            } catch (IOException e) {
                System.out.println("creating socket at " + host + ":" + port + " failed. trying again in 5 seconds...");
                Thread.sleep(5000);
                continue;
            }
        }
    }

    /**
     * create Client with default values
     */
    public Server() throws InterruptedException {
        this(DEF_HOST, DEF_PORT);
    }


    public static void main(String[] args) throws InterruptedException {
        Server S;
        try {
            S = (args.length == 2 && args[0] != null && args[1] != null)? new Server(args[0], Integer.parseInt(args[1])):new Server();
        } catch (NumberFormatException e) {
            System.out.println("not a port: " + args[1]);
            System.exit(1);
        }
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
            //byte[] msg = new byte[1024];
            try {
                //    int len = socket.dataAvailable();
                //    if (len > 0) {
                //        int bytesRead = socket.read(msg, Math.min(len, msg.length));
                //        if (bytesRead == -1) {
                //            continue; // End of stream
                //        }
                //        Request req = (Request) Serialisation.deserialize(msg);
                String[] msg = socket.readLine().trim().split(" ");
                Request req = new Request(RequestCode.fromCode(msg[0]), msg[2]);
                System.out.println("Command gotten: " + req.toString());
                handleRequest(req);
                //}
            } catch (IOException e) {
                System.out.println("Client " + id + " disconnected!: " + e.getMessage());
                running = false;
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // also maybe refactor more into Serverhandle, looks hideous rn
    void handleRequest(Request request) throws IOException {
        // TODO: Handle Client Requests
        Response response = null;
        switch (request.getRequestCode()) {
            case RequestCode.ADD:
                // NOTE: change this when changing to a database
                response = addEvent(request.getContent())?new Response(StatusCode.OK):new Response(StatusCode.SERVER_ERROR);
            break;
            case RequestCode.DELETE:
                // NOTE: change this when changing to a database
                try {
                    response = remove(request.getContent());
                } catch (NumberFormatException e) {response = new Response(StatusCode.NOT_FOUND);}
            break;
            case RequestCode.SYNC:
                response = sync()?new Response(StatusCode.OK):new Response(StatusCode.SERVER_ERROR);
                break;
            case RequestCode.GET:
                String msg = request.getContent();
                String cmd[] = msg.split(";");
                // NOTE: change this when changing to a database
                switch (cmd[0]) {
                    case "person":
                        response = ServerHandle.getByPerson(cmd[1]);
                    break;
                    case "tag":
                        response = ServerHandle.getByTag(cmd[1]);
                    break;
                    default:
                        response = ServerHandle.getByPerson(cmd[1]);
                    break;
                }
            break;
            case RequestCode.SU:
                if (ServerHandle.su(request.getContent())) {
                    response = new Response(StatusCode.OK);
                    admin = true;
                } else {
                    response = new Response(StatusCode.PERMISSION_ERROR);
                }
            break;
            case RequestCode.LIST:
            // NOTE: change this when changing to a database
                    response = admin?new Response(StatusCode.OK, listClients()):
                                new Response(StatusCode.PERMISSION_ERROR);
                    //socket.write(response, response.length);
                    //socket.write(response, response.length);
            break;
            case RequestCode.KICK:
                if (admin) {
                    try {
                        if (kick(Integer.parseInt(request.getContent()))) {
                        response = new Response(StatusCode.OK, listClients());
                        } else {}
                    } catch (Exception e) {
                    response = new Response(StatusCode.PERMISSION_ERROR);
                    }
                }
            break;
            case RequestCode.EXIT:
                socket.close();
                running = false;
            break;
            case RequestCode.CLOSE:
                if (!admin) {
                    response = new Response(StatusCode.PERMISSION_ERROR);
                } else {
                multicast(new Request(RequestCode.EXIT));
                close();
            }
            break;
            default:
                response = new Response(StatusCode.BAD_REQUEST);
            break;
        }
        socket.write(response.toString());
    }

    public Boolean sync() {
        return true;
    }

    private Boolean addEvent(String content) {
        String[] event = content.split(":");
        try {
            EventHandler.addEvent(new Event(event[0], event[1], event[2]));
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

    private Response remove(String event) throws NumberFormatException {
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
        String str = new String();
        for (Clientp cli : Server.clients) {
            str += cli.toString();
        }
        return str;
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
                Thread.sleep(5000); // Check every 5 seconds
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
