package mtd.tasker;

import java.util.List;
 
public class Event {
    private int timeAdded;
    static int ID = -1;
    private int id;
    private String title;
    private String person;
    private double from;
    private double to;
    private String tag;

    public Event(String title, String person, double from, double to, String tag) {
        this.timeAdded = java.time.Instant();
        this.id = ID++;
        this.title = title;
        this.person = person;
        this.from = from;
        this.to = to;
        this.tag = tag;
    }

    //returns a summary of the Event.
    @Override
    public String toString() {
        return ID + ";" + title + ";" + from + ";" + to + ":" + "(" + person + "," + tag + ")";
    }

    public String toStringFormat() {
        return ID + ":" + title + "|" + from + "-" + to + "(" + person + "," + tag + ")"; 
    }
}
