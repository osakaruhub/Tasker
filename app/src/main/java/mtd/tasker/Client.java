package mtd.tasker;

import socketio.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

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
    static public Response request(Request req) {
        try {
            if (!socket.connect()) throw new SocketException("Socket is closed, try opening the class again");
            System.out.println("Requesting " + request.getCode());

            //byte[] req = Serialisation.serialize(request);
            //socket.write(req, req.length);
            socket.write(req.toString);
            return new Response (socket.readLine());
            //byte[] resp = new byte[1024];
            //int bytesRead = socket.read(resp, resp.length);
            //
            //if (bytesRead == -1) return null; // End of stream
            //
            //Response response = (Response) Serialisation.deserialize(resp);
            //System.out.println("Response in bytes: " + Arrays.toString(resp)); // Print byte array content
            //
            return response;
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
