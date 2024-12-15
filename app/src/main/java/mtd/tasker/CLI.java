package mtd.tasker;

import java.util.Scanner;
import java.io.IOException;
import mtd.tasker.protocol.Request;
import mtd.tasker.protocol.RequestCode;
import mtd.tasker.protocol.Response;

/**
 * access the server right from your terminal! this is a small program that sends commands straight to the server, but it still has to conform to the TaskerProtocol
 *
 */
public class CLI {
    static Client c;
    static private final String stopMessage = "exit";

    public static void main(String[] args) {
        //try {
        //    if (args != null && args[0] != null && args[1] != null) {
        //    c = new Client(args[0], Integer.parseInt(args[1]));
        //} else {
        //}
            //} catch (NumberFormatException e) {
            //    System.exit(1);
        new CLI();
    }

public CLI() {
    c = new Client();
    commands();
}

    public static void commands() {
        Scanner sc = new Scanner(System.in);
        System.out.println("conntected! (exit with \'" + stopMessage + "\' )");
        String cmd;
        do {
            cmd = sc.next();
                Response resp = Client.request(new Request(RequestCode.fromCode(cmd.substring(1,cmd.indexOf(' '))),cmd));
                System.out.println(resp);
        } while (!cmd.equals(stopMessage));
    }
}
