package org.example;
import java.util.List;
 
public class Entry {
  static int ID;
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

  @Override
  public String toString() {
    return ID + ":" + title + "|" + from + "-" + to; 
  }
}
