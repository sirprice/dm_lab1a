/**
 * Created by cj on 12/11/15.
 */

import java.util.*;
import java.io.*;

public class MessageSender implements Runnable {
    private Scanner scan;
    private PublicKey publicKey;
    private ObjectOutputStream out;
    boolean cont = true;

    public MessageSender(ObjectOutputStream out, PublicKey publicKey) {
        this.out = out;
        this.publicKey = publicKey;
        scan = new Scanner(System.in);
    }

    public void run() {
        while (cont == true) {
            System.out.print("Send > ");
            String str = scan.nextLine();
            try {
                out.writeObject(new Message(publicKey, RSA.encrypt(str,publicKey)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        cont = false;
    }

}

