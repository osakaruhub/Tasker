package mtd.tasker;

import java.time.Instant;
 
/**
 * A class Representing an event, with an optional tag
 */
public class Event {
    private Instant timeAdded;
    static int ID = -1;
    private String title;
    private String person;
    private double from;
    private double to;
    private String tag;

    /**
     * create an Event
     *
     * @param title the purpose of the event
     * @param person the person the event is for
     * @param from the starting time of the event
     * @param to the ending time of the event
     * @param tag the tag of the event
     */
    public Event(String title, String person, double from, double to, String tag) {
        this.timeAdded = Instant.now();
        this.title = title;
        this.person = person;
        this.from = from;
        this.to = to;
        this.tag = tag;
    }

    /**
     * Same as Event, but without the Tag
     *
     * @param title the purpose of the event
     * @param person the person the event is for
     * @param from the starting time of the event
     * @param to the ending time of the event
     */
    public Event(String title, String person, double from, double to) {
        this.timeAdded = Instant.now();
        this.title = title;
        this.person = person;
        this.from = from;
        this.to = to;
    }

    /**
     * get a summary of the Event, useful for sending through a socket
     *
     * @return 
     */
    @Override
    public String toString() {
        return title + ":" + from + ":" + to + ":" + ":" + person + ":" + tag;
    }

    /**
     * format Event to String for easy debugging or view in the terminal
     *
     * @return String - a formatted String of the event
     */
    public String toStringFormat() {
        return ID + ":" + title + "|" + from + "-" + to + "(" + person + "," + tag + ")"; 
    }

    public String getPerson() {
    return person;
}

    public String getTitle() {
    return title;
}
    public Instant getTimeAdded() {
    return timeAdded;
}
    public String getTag() {
    return tag;
}
}
