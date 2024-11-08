public class Client {
  final int port = 1234;
  final String host = "localhost";
  public Socket socket; 
  private ArrayList<Calendar> calendars;
  
  public Client() {
    while (true) { 
      try {
        socket = new Socket(host, port);
        break;
      } catch (Exception e) {
        System.out.println("couldnt connect to server, trying in 5s");
        
      }
    }
  }
  public Boolean verbinden() {
    return socket.connect();
  }
  public getCalendar(String calendar);
  public void sync();
  public void getCalendar(String cal);
  public Entry findEntry(String);
  public List<String> Entry filter(String tag);
  public Boolean close();
  
  public static void main(String[] args) {
    
  }
}
