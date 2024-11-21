package org.example;


public class Client {
    private static final int port = 1234;
    private static final String host = "localhost";
    private Socket socket; 

    public Client() {
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {}
    }

    public Boolean verbinden() {
        return socket.connect();
    }

    public StatusCode request(String string) {
        socket.write(String);

        while (socket.read() != ACK) {}
        String status = socket.read();
        return new StatusCode(Integer.parseInt(status.substring(0, 3)), status.substring(4));
    }

    public static void main(String[] args) {
        new Client();
    }
}
