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
    private String title;
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
    public Event(String title, String person, Date date, String tag) {
        this.timeAdded = Instant.now();
        this.ID = UUID.randomUUID();
        this.title = title;
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
    public Event(String title, String person, Date date) {
        this(title, person, date, null);
    }

    // time is the seconds after the unix epoch, which it will convert to date
    public Event(String title, String person, int date, String tag) {
        this(title, person, new Date(date * 1000), tag);
    }

    // time is the seconds after the unix epoch, which it will convert to date
    public Event(String title, String person, int date) {
        this(title, person, new Date(date * 1000), null);
    }

    // a String that is of pattern 'DD,MM,YY,HH,MM'
    public Event(String title, String person, String date, String tag) throws ParseException {
        this(title, person, Handler.dateFormat.parse(date), tag);
    }

    /**
     * get a summary of the Event, useful for sending through a socket
     *
     * @return 
     */
    @Override
    public String toString() {
        return title + ":" + Handler.dateFormat.format(date) + ":" + person + ":" + tag;
    }

    /**
     * format Event to String for easy debugging or view in the terminal
     *
     * @return String - a formatted String of the event
     */
    public String toStringFormat() {
        return ID + ":" + title + "|" + date + "(" + person + "," + tag + ")"; 
    }

    public String getPerson() {
        return person;
    }

    public String getID() {
        return ID.toString();
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public Instant getTimeAdded() {
        return timeAdded;
    }

    public String getTag() {
        return tag;
    }
}
