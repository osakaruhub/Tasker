package org.example;

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
  public getCalendar(String calendar) {}
      
  public void sync() {}

  public static void main(String[] args) {
        new Client();
  }
}
