package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by cj on 2017-11-20.
 */
public class Server implements Runnable{


    private ServerSocket serverSocket;
    private ConcurrentHashMap<SocketAddress, Client> clientLookup;
    private ExecutorService threadPool;
    private AtomicBoolean running;


    @Override
    public void run() {

    }

    public Server(int port) throws IOException {
        this.running = new AtomicBoolean(true);
        this.serverSocket = new ServerSocket(port);
        this.clientLookup = new ConcurrentHashMap<SocketAddress, Client>();
        this.threadPool = Executors.newCachedThreadPool();
        System.out.println(InetAddress.getLocalHost());
    }


    public void start() {

        try {
            threadPool.execute(this);
            while (running.get()) {
                System.out.println("Waiting for connection...");

                Socket clientSocket = null;
                clientSocket = serverSocket.accept();
                System.out.println("Client connected!");
//
//                SocketAddress inetAddress = clientSocket.getRemoteSocketAddress();//.getSocketAddress();
//
//                Client client = new Client(clientSocket, this);
//                Client oldClient = clientLookup.put(inetAddress, client);
//
//                if (oldClient != null) {
//                    oldClient.terminateClient();
//                }
//                System.out.println("Client Added!");
//                threadPool.execute(client);
//                broadcastMsg("Client " + client.getNickName() + " connected", null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            shutdownServer();
        }
    }

    public void shutdownServer() {
//        for (Map.Entry<SocketAddress, Client> entry : clientLookup.entrySet()) {
//            //System.out.println(entry);
//            Client c = entry.getValue();
//            disconnectClient(c);
//        }
//
//        try {
//            if(serverSocket != null)
//                serverSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        running.set(false);
//        threadPool.shutdown();
    }
}
