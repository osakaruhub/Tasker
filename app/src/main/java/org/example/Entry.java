package org.example;
import java.util.List;
 
public class Entry {
  static int ID = 0;
  String title;
  double from;
  double to;
  List<String> tags;

  public Entry(String title, double from, double to) {
    ID += 1;
    this.title = title;
    this.from = from;
    this.to = to;
  }

  //returns a summary of the Event.
  @Override
  public String toString() {
    return ID + ":" + title + "|" + from + "-" + to; 
  }
}
