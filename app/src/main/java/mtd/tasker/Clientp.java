package mtd.tasker;

import socketio.Socket;

public class Clientp {
    static int ID = 0;
    public int id;
    private Socket socket;
    private Thread thread;
    private String ip; 

    public Clientp(Socket s, Thread t, String ip) {
        this.id = ++ID;
        this.socket = s;
        this.thread = t;
        this.ip = ip;
    }

    public int getId() {
        return id;
    }
    public String getIp() {
        return ip;
    }

    public Thread getThread() {
        return thread;
    }

    public Socket getSocket() {
        return socket;
    }
}
