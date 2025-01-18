package mtd.tasker;

import mtd.tasker.protocol.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Handler - Intermediary for Client and GUI - handles locally saved events
 *
 */
public class Handler {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.HH.mm");
    static private ArrayList<Event> entries = new ArrayList<>();
    private static final int OK = 0;

    // NOTE: add tags or entries here
    static {
    }

    static public Event[] getEvents() {
        return (Event[]) entries.toArray();
    }

    static public Event getEvent(int id) {
        return entries.get(id);
    }

    static public List<Event> getEventsByTag(String tag) {
        return entries.stream().filter(entry -> tag.equals(entry.getTag())).collect(Collectors.toList());
    }

    static public List<Event> getEventsByDate(Date date) {
        return entries.stream().filter(entry -> date.equals(entry.getDate())).collect(Collectors.toList());
    }

    static public List<Event> getEventsByPerson(String person) {
        return entries.stream().filter(entry -> person.equals(entry.getPerson())).collect(Collectors.toList());
    }

    static public Boolean addEvents(List<Event> events) throws NumberFormatException {
        for (Event e : events) {
            if (addEvent(e) + "" != StatusCode.OK.getCode()) {
                return false;
            }
        }
        return true;
    }

    // -1 - Event format invalid
    // primarly used for manual add (via cli or Serverrequest)
    static public int addEvent(String content) {
        String[] field = content.split(":");
        if (field.length != 3) {
            return -1;
        }
        try {
            Event entry = new EventBuilder().withDate(field[0]).withPerson(field[1]).withTag(field[2]).build();
            return addEvent(entry);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    static public String[] addEvent(RequestCode requestCode, String content) {
        Request request = new Request(requestCode, content);
        Response response = Client.request(request);
        return response.getContent().split(":");
    }

    static public int addEvent(Event e) {
        Request request = new Request(RequestCode.ADD, e);
        Response response = Client.request(request);
        if (response.getCode() == StatusCode.OK.toString()) {
            entries.add(e);
            return OK;
        }
        return Integer.parseInt(response.getCode());
    }

    // static public int addEvent(String title, String person, Date date, String
    // tag) {
    // Request request = new Request(RequestCode.ADD, new Event(title, person, date,
    // tag).toString());
    // Response response = Client.request(request);
    // if (response.getCode() == StatusCode.OK.toString()) {
    // entries.add(new Event(title, person, date, tag));
    // return OK;
    // }
    // return Integer.parseInt(response.getCode());
    // }
    //
    //static public String getTagsString() {
    //    String str = "";
    //    int i = 0;
    //    for (; i < tags.size(); i++) {
    //        str += i + " - " + tags.get(i) + "\n";
    //    }
    //    str += (i + 1) + " - custom";
    //    return str;
    //}

    // delete an Entry locally and from the Server.
    static public Boolean deleteEntry(String uid) {
        return entries.removeIf(entry -> entry.getID().equals(uid) &&
                Client.request(new Request(RequestCode.DELETE, uid)).getStatusCode() == StatusCode.OK);
    }

    // remove it only locally. useful when the Server requests for a deletion of an
    // Event.
    static public Boolean removeEntry(String uid) {
        return entries.removeIf(entry -> entry.getID().equals(uid));
    }

    static public void sync(Date date) {
        Response response = Client.request(new Request(RequestCode.SYNC));
        if (response.getStatusCode() == StatusCode.OK) {

        }
        // TODO: sync method
    }

    static public String availableTime(String date) {
        Request request = new Request(RequestCode.GET, "date;" + date);
        return Client.request(request).getContent();
    }
}
