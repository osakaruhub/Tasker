package mtd.tasker;

import mtd.tasker.protocol.Request;
import mtd.tasker.protocol.RequestCode;
import mtd.tasker.protocol.StatusCode;
import mtd.tasker.protocol.Response;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Handler
 */
public class Handler {
    static private ArrayList<Event> entries;
    static private List<String> tags;
    private static final int OK = 0;

    //add tags or entries here
    static {
    }

    static public Event[] getEvents() {
        return (Event[]) entries.toArray();
    }

    static public Event getEvent(int id) {
        return entries.get(id);
    }

    static public int addEvent(String title, String person, double from, double to, String tag) {
        Request request = new Request(RequestCode.ADD, new Event(title, person, from, to, tag).toString());
        Response response = Client.request(request.toString());
        if (response.getCode() == StatusCode.OK.toString()) {
            entries.add(new Event(title,person,from,to,tag));
            return OK;
        }
        return Response.getCode();
    }

    static public List<String> getTags() {
        return Handler.tags;
    }

    static public String getTagsCLI() {
        String str;
        int i = 0;
        for (;i<tags.size();i++) {
            str += i + " - " + tags.get(i) + "\n";
        }
        str += (i+1) + " - custom";
        return str;
    }

    static public Boolean deleteEntry(int id) {
        Request = Request.DELETE;
        Response response = Client.request(new Request(RequestCode.DELETE, id + "").toString());
        return response.getStatusCode == StatusCode.OK;
    }
      
    static public void sync() {
        StatusCode = Client.request(new Request(RequestCode.SYNC));
        if (statusCode == StatusCode.OK) {

        }
        //TODO: sync method
    }
}
