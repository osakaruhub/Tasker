public class Server {
  final int port = 1234;
  final String host = "localhost"
  private ArrayList<Clients>
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

    

  public static void main(String[] args) {
    new Server();
  }
}
