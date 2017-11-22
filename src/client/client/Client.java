package client.client;

import client.crypto.AES;
import client.crypto.PublicKey;
import client.crypto.RSA;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by cj on 2017-11-20.
 */
public class Client implements Runnable, Serializable
{
    private RSA rsa;
    private AES aes;
    byte[] aesEncryptionKey;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private AtomicBoolean running;
    private boolean iShouldInitiateHandshake;
    private Thread ms;

    private Socket socket;
    private ServerSocket myServerSocket;
    private String clientAddress;
    private int serverPort;
    private int clientPort;
    private int rsaBitLength;


    private PublicKey publicKeyFromSender;


    public Client(String clientAddress, int serverPort, int rsaBitLength, boolean iShouldInitiateHandshake) {
        this.clientAddress = clientAddress;
        if (!iShouldInitiateHandshake){
            this.serverPort = 9000;
            this.clientPort = 9001;
        } else{
            this.clientPort = 9000;
            this.serverPort = 9001;
        }
        this.iShouldInitiateHandshake = iShouldInitiateHandshake;
        this.rsaBitLength = rsaBitLength;


    }

    @Override
    public void run() {
        try {

            /**
             *  **/

            rsa = new RSA(rsaBitLength);
            rsa.generateKey();


            System.out.println("Client starting...");
            myServerSocket = new ServerSocket(serverPort);
            System.out.println("Reachable on: " + myServerSocket.getLocalSocketAddress());



            if (!iShouldInitiateHandshake) {
                System.out.println("Waiting for connection...");
                socket = myServerSocket.accept();
                System.out.println("Connection established...");

                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());


                if (confirmClient()) {
                    System.out.println("Client confirmed...");
                    if (!confirmMe()) {

                        terminate();
                        return;
                    }
                    System.out.println("I am confirmed...");
                    waitForSymmetricCrypto();
                }





            } else {
                System.out.println("Initiating contact...");
                socket = new Socket(clientAddress,clientPort);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());

                if (confirmMe()) {
                    System.out.println("I am confirmed...");
                    if (!confirmClient()) {

                        terminate();
                        return;
                    }
                    System.out.println("Client confirmed...");
                    setupSymmetricCrypto();
                }
            }


            System.out.println("Handshake done! ");


            ms = new Thread(new MessageSender(publicKeyFromSender, outputStream, rsa, aes));
            ms.start();

            Message messageFromSender;
            while ((messageFromSender = (Message) inputStream.readObject()) != null) {

                System.out.println(new String(messageFromSender.getMsgBytes()) + " = " + aes.decrypt(messageFromSender.getMsgBytes()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Client crash");
        } finally {
            terminate();
        }
    }


    private void terminate() {

        if (ms != null) {
            ms.stop();
        }
    }



    private boolean confirmClient() {
        try {
            Message initMessage = null;

            System.out.println("Waiting for handshake to begin...");
            initMessage = (Message) inputStream.readObject();

            publicKeyFromSender = initMessage.fromClientPK();

            Random rand = new Random();
            long secretNumber = rand.nextInt(1000000) + 1;
            System.out.println("secret number: \n" + secretNumber);
            String encryptedSecretNumber = RSA.encrypt("" + secretNumber, publicKeyFromSender);

            outputStream.writeObject(new Message(rsa.getPublicKey(), encryptedSecretNumber, Message.Type.MESSAGE));

            Message confirmation = (Message) inputStream.readObject();
            System.out.println("Confirmation: \n" + confirmation.getMsg());
            if (confirmation.getMsg().equals("" + secretNumber)) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Client crash");
        }
        return false;
    }

    private boolean confirmMe() {
        try {
            System.out.println("Beginning handshake...");
            outputStream.writeObject(new Message(rsa.getPublicKey(), "", Message.Type.MESSAGE));

            Message secretNumberMesssage = (Message) inputStream.readObject();
            System.out.println("SecretNumber before decryp: " + secretNumberMesssage.getMsg());
            String decryptedSecretNumber = rsa.decrypt(secretNumberMesssage.getMsg());
            System.out.println("Secret Number was: " + decryptedSecretNumber);

            outputStream.writeObject(new Message(rsa.getPublicKey(), decryptedSecretNumber, Message.Type.MESSAGE));

            return true;


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Client crash");
        }
        return false;
    }

    /**
     *
     *  AES setup
     *
     */

    private boolean setupSymmetricCrypto(){
        try {
            System.out.println("Sending AES key...");

            String aesKey = "MZIdaewJsCpCalle";
            aesEncryptionKey = aesKey.getBytes(StandardCharsets.UTF_8);
            aes = new AES(aesEncryptionKey);

            outputStream.writeObject(new Message(rsa.getPublicKey(), aesKey, Message.Type.MESSAGE));

            Message confirmation = (Message) inputStream.readObject();
            System.out.println("Confirmation on aes setup: " + confirmation.getMsg());

            outputStream.writeObject(new Message(rsa.getPublicKey(), "ok", Message.Type.MESSAGE));
            return true;


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Client crash");
        }
        return false;
    }

    private boolean waitForSymmetricCrypto(){
        try {
            System.out.println("Waiting for AES key...");
            Message aesCryptoMessage = (Message) inputStream.readObject();
            String aesKey = aesCryptoMessage.getMsg();

            aesEncryptionKey = aesKey.getBytes(StandardCharsets.UTF_8);
            aes = new AES(aesEncryptionKey);

            outputStream.writeObject(new Message(rsa.getPublicKey(), "ok", Message.Type.MESSAGE));

            Message confirmation = (Message) inputStream.readObject();
            System.out.println("Confirmation on aes setup: " + confirmation.getMsg());
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Client crash");
        }
        return false;
    }


    public PublicKey getPublicKey() {
        return rsa.getPublicKey();
    }
}
