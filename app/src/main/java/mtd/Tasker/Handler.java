package main.java.org.example;

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
    static public int addEvent(String title, String name, int from, int to, String tag) {
        request = new Request.ADD();
        statusCode = client.request(ADD + " " + title + "," + name + "," + from + "," + to + "," + tag);
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
        StatusCode statuscode = Client.request(DELETE + calendar+":"+event+";");
        return statusCode == StatusCode.OK;
    }
      
    static public void sync() {
        StatusCode = Client.request(SYNC);
        if (statusCode = StatusCode.OK) {

        }
        //TODO: sync method
    }
}
