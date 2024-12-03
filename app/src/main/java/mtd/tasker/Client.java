package mtd.tasker;

import socketio.Socket;
import java.io.IOException;

public class Client {

    private static final int port = 1234;
    private static final String host = "localhost";
    private static Socket socket; 

    public Client() {
        do {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
                System.out.println("couldnt create Socket, trying in 5 seconds...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ee) {}
            }
        } while (socket == null);
    }

    public Boolean verbinden() {
        try {
        return socket.connect();
        } catch (IOException e) {}
    }

    static public StatusCode request(String string) {
        socket.write(string);
        String status = socket.read();
        return new StatusCode(Integer.parseInt(status.substring(0, 3)), status.substring(4));
    }

    public void close() {
        System.out.println("Verbindung schliessen...");
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Konnte nicht die Verbindung schliessen");
            System.exit(0);
        }
    }

    // NOTE: solange GUI nicht vollständig ist, ist der main in Client
    public static void main(String[] args) {
        Client c = new Client();
        c.verbinden();
        while(true);
        c.close();
    }
}
