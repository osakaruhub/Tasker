package mtd.tasker;

import socketio.Socket;

public class Clientp {
    static int ID = 0;
    private int id;
    public Socket socket;
    public Thread thread;

    public Clientp(Socket s, Thread t) {
        this.id = ++ID;
        this.socket = s;
        this.thread = t;
    }
}
