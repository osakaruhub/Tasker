package mtd.tasker;

import java.net.InetAddress;
import java.net.UnknownHostException;

import socketio.Socket;

public class Clientp {
    static int ID = 0;
    public int id;
    private Socket socket;
    private Thread thread;
    private InetAddress ip; 

    public Clientp(Socket s, Thread t, String ip) throws UnknownHostException {
        this.id = ++ID;
        this.socket = s;
        this.thread = t;
        this.ip = InetAddress.getByName(ip);
    }

    public int getId() {
        return id;
    }
    public String getIp() {
        return ip.toString();
    }

    public Thread getThread() {
        return thread;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return id + ": " + ip.toString();
    }
}
