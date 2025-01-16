package mtd.tasker;

import mtd.tasker.protocol.Request;
import mtd.tasker.protocol.RequestCode;
import mtd.tasker.protocol.StatusCode;
import mtd.tasker.protocol.Response;
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
    static private List<String> tags;
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

    // primarly used for manual add (via cli or Serverrequest)
    static public Boolean addEvent(String content) throws NumberFormatException {
        Event entrie;
        System.out.println(content);
        String[] field = content.split(":");
        try {
            entrie = new Event(field[0], field[1], field[2]);
            entries.add(entrie);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return false;
        }
        if (entries != null) {
            Request request = new Request(RequestCode.ADD, entrie);
            System.out.println(request.getRequestCode());
            Client.request(request);
        }
        return true;

    }

    static public String[] addEvent(RequestCode requestCode, String content) {
        Request request = new Request(requestCode, content);
        Response response = Client.request(request);
        return response.getContent().split(":");
    }

    static public int addEvent(Event e) {
        Request request = new Request(RequestCode.ADD, e.toString());
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

    static public List<String> getTags() {
        return Handler.tags;
    }

    static public String getTagsString() {
        String str = "";
        int i = 0;
        for (; i < tags.size(); i++) {
            str += i + " - " + tags.get(i) + "\n";
        }
        str += (i + 1) + " - custom";
        return str;
    }

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

    static public void sync() {
        Response response = Client.request(new Request(RequestCode.SYNC));
        if (response.getStatusCode() == StatusCode.OK) {

        }
        // TODO: sync method
    }

    static public String availableTime(String date) {
        Request request = new Request(RequestCode.GET, date);
        return Client.request(request).getContent();
    }
}
