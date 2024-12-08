package mtd.tasker;

import mtd.tasker.Server;
import java.util.ArrayList;

public class ServerHandle {
    public static ArrayList<Event> events = new ArrayList<>();

    public static Boolean su(String pass) {
        return pass.equals(Server.ADMIN_PASS);
    }
}
