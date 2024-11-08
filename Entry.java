public class Entry {
  static int ID;
  String title;
  double from;
  double until;
  Arraylist<String> tags;

  public Entry(String title, double from, double until) {
    ID += 1;
    this.title = title;
    this.from = from;
    this.until = until;
  }

  @Override
  public String toString() {
    return ID + ":" + title + "|" + from + "-" + until; 
  }
}
