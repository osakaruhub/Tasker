package mtd.tasker;

import mtd.tasker.protocol.Request;
import mtd.tasker.protocol.StatusCode;
import java.util.HashMap;
import java.util.List;

/**
 * Handler
 */
public class Handler {
    static private HashMap<Entry> entries;
    static private List<String> tags;

    //add tags or entries here
    static {
    }

    static public Event getEvent(int id) {
        return entries.getOrDefault(id, null);
    }
    static public int addEvent(String title, String person, int from, int to, String tag) {
        Request request = new Request.ADD(title, person, from, to, tag);
        statusCode = client.request(request.toString());
        if (statusCode.getCode() == StatusCode.OK) {
            entries.add(new Event(title,person,from,to,tag));
            return OK;
        }
        return statusCode;
    }

    static public List<String> getTags() {
        return Handler.tags;
    }

    static public String getTagsCLI() {
        String str;
        for (int i = 0;i<tags.size();i++) {
            str += i + " - " + tags.get(i) + "\n";
        }
        str += "custom";
        return str;
    }

    static public Boolean deleteEntry(int id) {
        Request = Request.DELETE;
        StatusCode statuscode = Client.request(Request.DELETE + id +";");
        return statusCode == StatusCode.OK;
    }
      
    static public void sync() {
        StatusCode = Client.request(SYNC);
        if (statusCode == StatusCode.OK) {

        }
        //TODO: sync method
    }
}
