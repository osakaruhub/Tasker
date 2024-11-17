package main.java.org.example;

import java.util.HashMap;

/**
 * Handler
 */
public class Handler {
    private HashMap<Entry> entries;
    

    public Entry getEntry(int id) {
        return this.entries.getOrDefault(id, null);
    }
  
    public int addEntry(String title, String name, int from, int to) {
        statusCode = client.request(ADD + " " + title + "," + name + "," + from + "," + to);
        if (statusCode.getCode() == StatusCode.OK) {
            this.entries.add(new Entry(title,person,from,to));
            return OK;
        }
        return statusCode;
    }

    public Boolean deleteEntry(int id) {
        statuscode = client.request(DELETE + calendar+":"+event+";");
        return statusCode;
    }
      
  public void sync() {
        client.request(SYNC);
        //TODO: sync method
    }
}
