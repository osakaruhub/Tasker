package mtd.tasker;

import java.util.Scanner;
import mtd.tasker.protocol.Request;
import mtd.tasker.protocol.RequestCode;
import mtd.tasker.protocol.InvalidCommandException;
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
        System.out.println("conntected! (exit with \'" + stopMessage + "\')");
        String cmd = "";
        do {
            cmd = sc.nextLine();
            System.out.println(cmd + "test");
            cmd = cmd.trim();
            int spaceIndex = cmd.indexOf(' ');
            if (cmd.isBlank() || spaceIndex == -1) {
                continue;
            }
            try {
                Response resp = Client.request(new Request(RequestCode.fromCode(cmd.substring(1,spaceIndex)),cmd));
                System.out.println(resp);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
        } while (!cmd.equals(stopMessage));
    }
}
