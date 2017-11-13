/**
 * Created by cj on 12/11/15.
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class P2PTCP {

    public static String host = "130.229.172.12";

    public static void main(String[] args) {

        Scanner scan;
        Thread st = null;
        Socket peerConnectionSocket = null;


        /**
         * arguments for server:
         * 0: "server"
         * 1: bitlength
         * 2: portNumber
         */
        if (args[0].equals("server")) {
            RSA rsa = new RSA(Integer.parseInt(args[1]));
            try {

                if (!rsa.generateKey()) {

                    throw new IOException("No key generated");
                }

                ServerSocket ss = new ServerSocket(Integer.parseInt(args[2]));
                System.out.println("Server address: " + ss.getLocalSocketAddress());
                System.out.println("Waiting for connection...");

                peerConnectionSocket = ss.accept();

                ObjectOutputStream os = new ObjectOutputStream(peerConnectionSocket.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(peerConnectionSocket.getInputStream());


                os.writeObject(new Message(rsa.getPublicKey(), "Welcome!"));


                String secretNumber = (String) is.readObject();
                System.out.println("SecretNumber before decryp: " + secretNumber);

                String decryptedNumber = rsa.decrypt(secretNumber);
                System.out.println("SecretNumber after decryp: " + decryptedNumber);


                st = new Thread(new MessageSender(os, rsa.getPublicKey()));
                st.start();
                Message messageFromClient;
                while ((messageFromClient = (Message) is.readObject()) != null) {
                    System.out.println("SecretNumber before decryp: " +messageFromClient.getMsg());

                    System.out.println(rsa.decrypt(messageFromClient.getMsg()));
                }

            } catch (IOException e) {
                System.err.println("Server crash");
                e.printStackTrace();
            } catch (ClassNotFoundException ce) {
                ce.printStackTrace();
            } finally {
                st.stop();
            }
        }
        /**
         * arguments for client:
         * 0: "client"
         * 1: ipAddress
         * 2: portNumber
         */
        else if (args[0].equals("client")) {
            try {
                System.out.println(args[1]);
                peerConnectionSocket = new Socket(args[1], Integer.parseInt(args[2]));

                System.out.println("Connection established");
                System.out.println("Connection os");
                ObjectOutputStream os = new ObjectOutputStream(peerConnectionSocket.getOutputStream());
                System.out.println("Connection is");
                ObjectInputStream is = new ObjectInputStream(peerConnectionSocket.getInputStream());


                System.out.println("Waiting for public key");
                Message initMessage = (Message) is.readObject();
                PublicKey publicKey = initMessage.getPublicKeyFromSender();

//                while ((publicKey = (PublicKey) is.readObject()) != null){
//
                System.out.println("PubKey: " + publicKey.toString());

                Random rand = new Random();
                int secret = rand.nextInt(100) + 1;

                System.out.println("secret number: " + secret);

                String encryptedMsg = RSA.encrypt("" + secret, publicKey);

                os.writeObject(encryptedMsg);


                st = new Thread(new MessageSender(os, publicKey));
                st.start();
                Message messageFromServer;
                while ((messageFromServer = (Message) is.readObject()) != null) {
                    System.out.println(messageFromServer.getMsg());
                }


            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Client crash");
            } finally {
                st.stop();
            }
        }
    }
}

