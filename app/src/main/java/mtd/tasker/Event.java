package mtd.tasker;

import java.time.Instant;
 
public class Event {
    private Instant timeAdded;
    static int ID = -1;
    private String title;
    private String person;
    private double from;
    private double to;
    private String tag;

    public Event(String title, String person, double from, double to, String tag) {
        this.timeAdded = Instant.now();
        this.title = title;
        this.person = person;
        this.from = from;
        this.to = to;
        this.tag = tag;
    }

    //returns a summary of the Event.
    @Override
    public String toString() {
        return title + ":" + from + ":" + to + ":" + ":" + person + ":" + tag;
    }

    public String toStringFormat() {
        return ID + ":" + title + "|" + from + "-" + to + "(" + person + "," + tag + ")"; 
    }
}
