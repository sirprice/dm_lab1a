package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by cj on 2017-11-20.
 */
public class Client implements Runnable {

    private Server serverDelegate;
    private Socket socket;
    private String nickname;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private AtomicBoolean running;


    private Client connectedClient;

    public void setConnectedClient(Client connectedClient) {
        this.connectedClient = connectedClient;
    }

    public Client(Server serverDelegate, Socket socket, String nickname) {
        this.serverDelegate = serverDelegate;
        this.socket = socket;
        this.nickname = nickname;

        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        running.getAndSet(true);


        sendMessageToClient(new Message(null,"Welcome..",Message.Type.WELCOME));

        try {
            while (running.get()) {
                Message messageFromClient;
                if ((messageFromClient = (Message) inputStream.readObject()) != null){
                    if (connectedClient != null){
                        // vidarebefodra meddelande till clienten
                        connectedClient.sendMessageToClient(messageFromClient);

                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    public void sendMessageToClient(Message message) {
        try {
            System.out.println("Sending message to: " + nickname);
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void terminate() {
        running.getAndSet(false);
    }

    private void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (this.outputStream != null) {
            try {
                this.outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
