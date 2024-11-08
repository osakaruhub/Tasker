package org.example;

import java.util.ArrayList;

public class Calendar extends ArrayList<Entry> {
    String name;

    public Calendar(String name) {
        super();
        this.name = name;
    }

  @Override
  public toString() {
    for (Entry entry :entries) {
      string += entry.toString + "\n";
    }
  }
    public findEntryByString(String entry) {} 
}
