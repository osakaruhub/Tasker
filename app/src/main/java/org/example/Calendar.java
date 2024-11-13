package org.example;
import java.awt.List;
import java.util.ArrayList;

public class Calendar extends ArrayList<Entry> {
    private static String ID = 0;
    String name;
    List<String> tags;

    public Calendar(String name, List<String> tags) {
        super();
        ID += 1;
        this.name = name;
        this.tags = tags;
    }

    //returns a String of all Events a Calendar.
    @Override
    public String toString() {
        String string;
        for (Entry entry : this) {
            string += this.name + "|";
            string += entry.toString();
        }
        return string;
    }

    public toStringFormat() {
        String string;
        for (Entry entry : this) {
            string += this.name + ":\n\n";
            string += entry.toStringFormat() + "\n";
        }
        return string;
    }
    // find Entry by String. Must be its full name.
    // returns the id of String or -1 if nothing.
    public Entry findEntryIdByString(String filter) {
        Entry entry;
        for (int i = 0; i < this.size(); i++) {
            entry = this.get(i);
            if (this.get(i).getName().equals(filter)) {
                return entry.getID();
            }
        }
        return -1;
    } 
}
