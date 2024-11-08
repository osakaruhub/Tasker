public class Client {
  final int port = 1234;
  final String host = "localhost"
  public Socket socket; 
  private ArrayList<Calendar> calendars;

  public Client() {
    try {
      socket = new Socket(host, port);
    } catch (Exception e) {
    }
  }
  public Boolean verbinden() {
    return socket.connect();
  }
  public getCalendar(String calendar) {
    return Server.readLine()
  }
      
  public void sync() {
    
  }

  public static void main(String[] args) {

  }
}
