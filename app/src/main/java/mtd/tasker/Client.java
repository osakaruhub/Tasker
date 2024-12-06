package mtd.tasker;

import socketio.Socket;
import java.io.IOException;

import mtd.tasker.protocol.Response;

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

    static public Response request(String string) {
        socket.write(string);
        String status = socket.readLine();
        String[] c = status.split("");
        return new Response(c[0],c[1]);
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
