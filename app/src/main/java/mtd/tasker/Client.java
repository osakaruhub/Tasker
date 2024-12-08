package mtd.tasker;

import socketio.Socket;
import java.io.IOException;

import mtd.tasker.protocol.Response;
import mtd.tasker.protocol.Request;

public class Client {

    private static final int PORT = 1234;
    private static final String HOST = "localhost";
    private static Socket socket; 

    public Client() {
        do {
            try {
                socket = new Socket(HOST, PORT);
                while (!verbinden()) {
                    System.out.println("couldnt connect to " + HOST + ":" + HOST + "\ntrying again in 5 seconds...");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ee) {}
                }
            } catch (IOException e) {
                System.out.println("couldnt create Socket, trying again in 5 seconds...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ee) {}
            }
        } while (socket == null);
    }

    public Boolean verbinden() {
        return socket.connect();
    }

    static public Response request(Request request) {
        try {
            byte[] req = Serialisation.serialize(request);
            socket.write(req, req.length);
            byte[] resp = null;
            int respLen = socket.read(resp, socket.dataAvailable());
            if ( respLen == -1) {
                return null;
            }
            return (Response) Serialisation.deserialize(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        System.out.println("Verbindung schliessen...");
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Konnte nicht die Verbindung schliessen");
            System.exit(1);
        }
    }

    // NOTE: solange GUI nicht vollständig ist, ist der main in Client
    public static void main(String[] args) {
        Client c = new Client();
        c.verbinden();
        while(true);
        //c.close();
    }
}
