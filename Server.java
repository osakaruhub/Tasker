public class Server {
  final int port = 1234;
  final String host = "localhost";
  private ServerSocket sSocket;
  private ArrayList<Clients>;
  private List<Calendar>;
  public Server() {
    try {
      sSocket = new ServerSocket(port)
      System.out.println("starting server at " + host + ":" + port);
      System.out.println("listening for clients...");
      while (true) {
        clients.add(sSocket.accept());
      }
    } catch (Exception e) {
      //TODO: handle exception
    }
  }             
  public Boolean close() {
    for (cSocket socket: clients ) {
      try {
        socket.close()
      } catch(Exception e) {
        System.out.println("couldnt close socket" + socket.toString();
        return false;
        }
        }
        try {
          sSocket.close()
        } catch(Exception e) {
          System.out.println("couldnt close server.");
          return false;
        }
        }
        public static void main(String[] args) {
          new Server();
        }
        }
