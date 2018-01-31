//run two instances of this class
//run one instance with some command line argument - this will be the server
//run one instance without any arguments - this will be the client

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0)
            //if any command line argument exists
            startServer();
        else
            //if no command line argument exists
            startClient();
    }//end main()

    private static void startServer() {
        ServerSocket serverSocket;
        Socket socket;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        String message;
        try {
            //setting up server
            //server will set on this machine at port 5000
            //and accept maximum of 1 clients
            serverSocket = new ServerSocket(5000, 1);
            serverSocket.setSoTimeout(20000);
            socket = serverSocket.accept();
            //setting up streams
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Server not created...");
        }

        //sending message to client
        try {
            objectOutputStream.writeObject("Hello this is message one from Server...");
        } catch (IOException e) {
            System.out.println("Cant send message from server...");
        }

        //receiving reply from client
        try {
            message = (String) objectInputStream.readObject();
            System.out.println("Client: " + message);
        } catch (Exception e) {
            System.out.println("Cant receive message from client...");
        }
    }//end startServer()

    private static void startClient() {
        Socket socket;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        String message;

        try {
            //connecting to server at localhost
            //127.0.0.1 is local machine and 5000 is port for communication
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 5000);
            //setting up streams
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.flush();
        } catch (IOException e) {
            System.out.println("Cant connect to server...");
        }

        //receiving message from server
        try {
            message = (String) objectInputStream.readObject();
            System.out.println("Server: " + message);
        } catch (Exception e) {
            System.out.println("Cant receive message from server...");
        }

        //writing reply to server
        try {
            objectOutputStream.writeObject("Hello this is reply one from Client...");
        } catch (IOException e) {
            System.out.println("Cant send message from client...");
        }
    }//end startClient()
}//end Main
