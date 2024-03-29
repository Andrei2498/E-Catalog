import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SocialNetworkServer {
    private static final int PORT = 8100;
    private ServerSocket serverSocket;
    private boolean running = false;
    Connection connection;


    public static void main(String[] args) throws IOException {
        SocialNetworkServer server = new SocialNetworkServer();
        try{
        server.init(); //handle exceptions in here
        server.waitForClients(); } catch (IOException e){
            System.err. println ("Ooops... " + e);
        } finally {
            server.stop();
        }

    }

    private void init() throws IOException {
        connection = Database.getConnection();
        serverSocket = new ServerSocket(PORT);
        this.running=true;
    }

    private  void waitForClients() throws IOException{
        while (true) {
            System.out.println ("Waiting for a client ...");
            Socket socket = serverSocket.accept();
            // Execute the client's request in a new thread
            new ClientThread(socket).start();
        }

    }

     // Implement the init() method: create the serverSocket and set running to true
             // Implement the waitForClients() method: while running is true, create a new socket for every incoming client and start a ClientThread to execute its request.

    private void stop() throws IOException {
        this.running = false;
        serverSocket.close();
        Database.closeConnection();
    }
}
