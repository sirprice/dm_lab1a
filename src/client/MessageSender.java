package client; /**
 * Created by cj on 12/11/15.
 */



import java.util.*;
import java.io.*;

public class MessageSender implements Runnable {
    private Scanner scan;
    private PublicKey pkToSender;
    private RSA rsa;
    private AES aes;
    private ObjectOutputStream out;
    boolean running = true;

    public MessageSender(PublicKey pkToSender,ObjectOutputStream out, RSA rsa, AES aes) {
        this.out = out;
        this.rsa = rsa;
        this.aes = aes;
        this.pkToSender = pkToSender;
        scan = new Scanner(System.in);
    }



    public void run() {
        while (running) {
            System.out.print("Send > ");
            String str = scan.nextLine();
            try {
                out.writeObject(new Message(rsa.getPublicKey(), aes.encrypt(str), Message.Type.MESSAGE));
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void stop() {
        running = false;
    }

}

