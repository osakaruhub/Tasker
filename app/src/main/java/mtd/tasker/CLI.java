package mtd.tasker;

import java.util.Scanner;
import mtd.tasker.protocol.Request;
import mtd.tasker.protocol.RequestCode;
import mtd.tasker.protocol.InvalidCommandException;
import mtd.tasker.protocol.Response;

/**
 * access the server right from your terminal! this is a small program that
 * sends commands straight to the server, but it still has to conform to the
 * TaskerProtocol
 *
 */
public class CLI {
    static Client c;
    static private final String stopMessage = "exit";

    public static void main(String[] args) {
        try {
            c = (args.length == 2 && args[0] != null && args[1] != null)
                    ? new Client(args[0], Integer.parseInt(args[1]))
                    : new Client();

        } catch (NumberFormatException e) {
            System.out.println("not a port: " + args[1]);
            System.exit(1);
        }
        new CLI();
    }

    public CLI() {
        commands();
    }

    public static void commands() {
        Scanner sc = new Scanner(System.in);
        System.out.println("conntected! (exit with \'" + stopMessage + "\')\n");
        String msg = "";
        do {
            // send user input and receive in form of a Response.
            // The input can be either just the code or also a content.
            msg = sc.nextLine().trim();
            // int spaceIndex = msg.indexOf(' ');
            if (msg.isBlank())
                continue;
            String[] com = msg.split(" ");
            if (com.length != 2)
                continue;
            try {
                Response resp = com.length==2?c.request(new Request(RequestCode.fromCode(com[0]), com[1])):c.request(new Request(RequestCode.fromCode(com[0]), ""));
                System.out.println(resp.toString());
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
        } while (!msg.equals(stopMessage));
        sc.close();
    }
}
