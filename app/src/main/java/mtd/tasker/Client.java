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
        c.verbinden();
        while(true);
        //c.close();
    }
}
