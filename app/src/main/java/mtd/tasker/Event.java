package mtd.tasker;

import java.util.Date;
import java.util.UUID;


public class Event {
    private final UUID ID;
    private String person;
    private Date date;
    private String tag;

    /**
        * create an Event
        *
        * @param title the purpose of the event
        * @param person the person the event is for
        * @param tag the tag of the event
        */
    public Event(String person, Date date, String tag) {
        this(UUID.randomUUID(), person, date, tag);
    }

    public Event(UUID id, String person, Date date, String tag) {
        this.ID = id==null?UUID.randomUUID():id;
        this.person = person;
        this.date = date;
        this.tag = tag;
    }

    public UUID getID() {
        return ID;
    }

    public String getPerson() {
        return person;
    }

    public Date getDate() {
        return date;
    }

    public String getTag() {
        return tag;
    }

    /**
        * get a summary of the Event, useful for sending through a socket
        *
        * @return String - a string used for sending.
        */
    @Override
    public String toString() {
        return Handler.dateFormat.format(date) + ":" + person + ":" + tag;
    }

    /**
        * format Event to String for easy debugging or view in the terminal
        *
        * @return String - a formatted String of the event
        */
    public String toStringFormat() {
        return ID + ":" + date + "(" + person + "," + tag + ")"; 
    }
}
