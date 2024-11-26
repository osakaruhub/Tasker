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

    static public Entry getEntry(int id) {
        return this.entries.getOrDefault(id, null);
    }
    static public int addEntry(String title, String name, int from, int to) {
        statusCode = client.request(ADD + " " + title + "," + name + "," + from + "," + to);
        if (statusCode.getCode() == StatusCode.OK) {
            this.entries.add(new Entry(title,person,from,to));
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
        int statuscode = Client.request(DELETE + calendar+":"+event+";");
        return statusCode;
    }
      
    static public void sync() {
        Client.request(SYNC);
        //TODO: sync method
    }
}
