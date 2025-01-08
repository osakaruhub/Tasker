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
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MM,yy,HH,mm");
    static private ArrayList<Event> entries;
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
        String[] field = content.split(":");
        try {
            entries.add(new Event(field[0], field[1], field[2], field[3]));
        } catch (ParseException e) {
            return false;
        }
        return true;
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


    //static public int addEvent(String title, String person, Date date, String tag) {
    //    Request request = new Request(RequestCode.ADD, new Event(title, person, date, tag).toString());
    //    Response response = Client.request(request);
    //    if (response.getCode() == StatusCode.OK.toString()) {
    //        entries.add(new Event(title, person, date, tag));
    //        return OK;
    //    }
    //    return Integer.parseInt(response.getCode());
    //}

    static public List<String> getTags() {
        return Handler.tags;
    }

    static public String getTagsString() {
        String str = "";
        int i = 0;
        for (;i<tags.size();i++) {
            str += i + " - " + tags.get(i) + "\n";
        }
        str += (i+1) + " - custom";
        return str;
    }

    static public Boolean deleteEntry(String uid) {
    //    return Client.request(new Request(RequestCode.DELETE, id + "")).getStatusCode() == StatusCode.OK;
        return entries.removeIf(obj -> obj.getID().equals(uid));
    }
      
    static public void sync() {
        Response response = Client.request(new Request(RequestCode.SYNC));
        if (response.getStatusCode() == StatusCode.OK) {

        }
        //TODO: sync method
    }
}
