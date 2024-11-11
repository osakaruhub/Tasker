package org.example;
import java.util.ArrayList;

public class Calendar extends ArrayList<Entry> {
    private static String ID = 0;
    String name;

    public Calendar(String name, List<String> tags) {
        super();
        ID += 1;
        this.name = name;
    }

  @Override
  public toString() {
    for (Entry entry :entries) {
      string += entry.toString + "\n";
    }
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
