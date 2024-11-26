package org.example;


import java.util.List;
 
public class event {
    static int ID = -1;
    String title;
    String person;
    double from;
    double to;
    String tag;

    public Event(String title, String person, double from, double to, String tag) {
        ID += 1;
        this.title = title;
        this.from = from;
        this.to = to;
        this.tag = tag;
    }

    //returns a summary of the Event.
    @Override
    public String toString() {
        return ID + ";" + title + ";" + from + ";" + to + ":"; 
    }

    public String toStringFormat() {
        return ID + ":" + title + "|" + from + "-" + to; 
    }
}
