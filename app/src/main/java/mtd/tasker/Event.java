package mtd.tasker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
 
/**
 * A class Representing an event, with an optional tag
 */
public class Event {
    private Instant timeAdded; // for sync
    private final UUID ID;
    private String person;
    private Date date;
    private String tag;

    // TODO: make this easier to handle, maybe with a EventBuilder class with withX methods
    /**
     * create an Event
     *
     * @param title the purpose of the event
     * @param person the person the event is for
     * @param tag the tag of the event
     */
    public Event(Date date,String person, String tag) {
        this.timeAdded = Instant.now();
        this.ID = UUID.randomUUID();
        this.person = person;
        this.date = date;
        this.tag = tag;
    }

    /**
     * Same as Event, but without the Tag
     *
     * @param title the purpose of the event
     * @param person the person the event is for
     * @param date the date of the event
     */
    public Event(Date date, String person) {
        this(date,person, null);
    }

    // time is the seconds after the unix epoch, which it will convert to date
    public Event(int date, String person) {
        this(new Date(date * 1000),person, null);
    }

    // same as last one but with tag
    public Event(int date, String person, String tag) {
        this(new Date(date * 1000),person, tag);
    }

    // a String that is of pattern 'DD,mm,YY,HH,MM'
    public Event(String date, String person) throws ParseException {
        this(Handler.dateFormat.parse(date), person, null);
    }

    // same as last one but with tag
    public Event(String date, String person, String tag) throws ParseException {
        this(Handler.dateFormat.parse(date), person, tag);
    }

    /**
     * get a summary of the Event, useful for sending through a socket
     *
     * @return 
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

    public String getPerson() {
        return person;
    }

    public String getID() {
        return ID.toString();
    }

    public Date getDate() {
        return ;
    }

    public Instant getTimeAdded() {
        return timeAdded;
    }

    public String getTag() {
        return tag;
    }
}
