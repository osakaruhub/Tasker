package mtd.tasker;

import socketio.Socket;
import java.io.IOException;
import java.net.InetAddress;

import mtd.tasker.protocol.Response;
import mtd.tasker.protocol.Request;
import mtd.tasker.protocol.RequestCode;

public class Client {

    private static int DEF_PORT = 1234;
    private static String DEF_HOST = "localhost";
    private String host;
    private int port;
    private static Socket socket; 

    /**
     * create Client with default values
     */
    public Client() {
        this(DEF_HOST, DEF_PORT);
    }

    /**
     * create the Client with custom adress
     *
     * @param port 
     * @param host 
     */
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        connect();
        Thread c = new Thread(new ServerThread(socket));
        c.setDaemon(true);
        c.start();
    }

    public void connect() {
        do {
            try {
                socket = new Socket(host, port);
                while (!verbinden()) {
                    System.out.println("couldnt connect to " + this.host + ":" + this.port + "\ntrying again in 5 seconds...");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ee) {
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (IOException e) {
                System.out.println("couldnt create Socket, trying again in 5 seconds...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ee) {
                    Thread.currentThread().interrupt();
                }
            }
        } while (socket == null);
        try {
                socket.write(InetAddress.getLocalHost().getHostAddress() + "\n");
        } catch (Exception e) {
        }
    }

    public Boolean verbinden() {
        return socket.connect();
    }

    /**
     * Send a Request to the Server
     *
     * @param request the request that is being sent
     * @return Response - the Response from the Server
     */
    static public Response request(Request request) {
        try {
            System.out.println("requesting " + request.getCode());
            byte[] req = Serialisation.serialize(request);
            socket.write(req, req.length);
            byte[] resp = new byte[1024];
            if (socket.read(resp, socket.dataAvailable()) == -1) return null;
            System.out.println("response in bytes: " + resp.toString());
            return (Response) Serialisation.deserialize(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * close the connection
     * note that it will close the JVM if the close fails
     */
    public void close() {
        System.out.println("Verbindung schliessen...");
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Konnte nicht die Verbindung schliessen");
            System.exit(1);
        }
    }

    // NOTE: solange GUI nicht vollständig ist, ist der main in Client, EDIT: habe auch Klassen TUI fuer 'Terminal User Interface' und CLI fuer die Kommandozeile erstellt, sodass sie temporaer zum Main gehoren
    public static void main(String[] args) {
        Client c = new Client();
        while(true) {
        }
        //c.close();
    }
}

class ServerThread implements Runnable {
    Socket s;

    public ServerThread(Socket s) {
        this.s = s;
    }

    public void run() {
        while (true) {
            try {
                int dataAvailable = s.dataAvailable();
                byte[] msg = null;
                if (dataAvailable == 0 && s.read(msg, dataAvailable) == -1) continue;
                Request req = (Request) Serialisation.deserialize(msg);
                switch (req.getRequestCode()) {
                    case RequestCode.ADD:
                        Handler.addEvent(req.getContent());
                        break;
                    case RequestCode.DELETE:
                        Handler.deleteEntry(req.getContent());
                        break;
                    default:
                        break;
                }
                
            } catch (Exception e) {
                continue;
            }
        }
    }
}
