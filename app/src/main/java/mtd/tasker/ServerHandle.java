package mtd.tasker;

import mtd.tasker.protocol.Response;
import mtd.tasker.protocol.StatusCode;

import java.util.ArrayList;

public class ServerHandle {
    public static ArrayList<Event> events = new ArrayList<>();

    public static Boolean su(String pass) {
        return pass.equals(Server.ADMIN_PASS);
    }

    public static Response getByTag(String tag) {
        String str = "";
        for (Event event : events) {
            if (tag.equals(event.getTag())) {
                str += event.toString() + ";";
            }
        }
        return str.isEmpty() ? new Response(StatusCode.NOT_FOUND) : new Response(StatusCode.OK, str);
    }

    public static Response getByPerson(String person) {
        String str = "";
        for (Event event : events) {
            if (person.equals(event.getPerson())) {
                str += event.toString() + ";";
            }
        }
        return str.isEmpty() ? new Response(StatusCode.NOT_FOUND) : new Response(StatusCode.OK, str);
    }

    public static Response getByDate(String date) {
        String str = "";
        for (Event event : events) {
            if (date.equals(event.getPerson())) {
                str += event.toString() + ";";
            }
        }
        return str.isEmpty() ? new Response(StatusCode.NOT_FOUND) : new Response(StatusCode.OK, str);
    }
}
