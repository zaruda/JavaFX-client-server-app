package Network;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by zarud on 28.11.2016.
 */
public class StartServer extends Thread{
        public static void main(String[] args) {

            ServerSocket serverSocket;
            MultiThreadServer server;
            try {
                serverSocket = new ServerSocket(2526);
                server = new MultiThreadServer(serverSocket);
                System.out.println("Server is up and running");
                System.out.println("Server IP: "+serverSocket.getInetAddress()+"\nPort: "+serverSocket.getLocalPort());
                server.run();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void startServer(){
            new Thread(() -> {
                StartServer.main(null);
            }).start();
        }

        public void stopServer(){

            new Thread(() -> {
                StartServer.main(null);
            }).interrupt();
        }

}
