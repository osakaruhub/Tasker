package mtd.tasker;

import java.util.Scanner;

import mtd.tasker.protocol.Request;
import socketio.Socket;

/**
 * access the server right from your terminal! this is a small program that sends commands straight to the server, but it still has to conform to the TaskerProtocol
 *
 */
public class CLI {
    static Client client = new Client();
    static private final String stopMessage = "exit";

    public static void main(String[] args) {
        Client c;
        if (args != null && args[0] != null && args[1] != null) {
            c = new Client(args[0], Integer.parseInt(args[1]));
        }
        commands();
    }

    public static void commands() {
        Scanner sc = new Scanner(System.in);
        String cmd;
        do {
            cmd = sc.nextLine();
            try {
                System.out.println(Client.request(Serialisation.serialize(new Request(StatusCode.fromCode(),cmd))));
            } catch (Exception e) {
                //TODO: handle exception
            }
        } while (!cmd.equals(stopMessage));
    }
}
