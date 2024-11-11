package org.example;

import java.sql.Date;

public class Client {
  private static final int port = 1234;
  private static final String host = "localhost";
  private Socket socket; 
  private ArrayList<Calendar> calendars;

  public Client() {
    try {
      socket = new Socket(host, port);
    } catch (Exception e) {}
  }

  public Boolean verbinden() {
    return socket.connect();
  }
  public Calendar getCalendar(String calendar) {
        //stub
        return null;
  }
  
  public Boolean addCalendar(String name) {
        //try {
        //socket.write(name);
        //} catch (IOException) {}
        return true;
    }
    public Boolean addEntry(String name, int from, int to, List<String> tags) {
        //socket.write(name+";"+from+";"+to+";"+String.join("%", tags)+"/n");
        return true;
    }
    public Boolean deleteEntry() {
        return true;
    }
    public Boolean deleteCalendar() {
        return true;
    }
      
  public void sync() {}

  public static void main(String[] args) {
        new Client();
  }
}
