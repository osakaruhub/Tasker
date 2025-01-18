package mtd.tasker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * A class Representing an appointment.
 */
public class EventBuilder {
    private UUID id;
    private String person;
    private Date date;
    private String tag;

    public EventBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public EventBuilder withId(String id) {
        this.id = UUID.fromString(id);
        return this;
    }

    public EventBuilder withPerson(String person) {
        this.person = person;
        return this;
    }

    public EventBuilder withDate(Date date) {
        this.date = date;
        return this;
    }

    public EventBuilder withDate(String date) throws ParseException {
        this.date = Handler.dateFormat.parse(date);
        return this;
    }

    public EventBuilder withDate(int date) {
        this.date = new Date(date * 1000);
        return this;
    }

    public EventBuilder withTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Event build() {
        return new Event(id, person, date, tag);
    }
}
