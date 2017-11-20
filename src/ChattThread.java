import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Created by cj on 2017-11-20.
 */
public class ChattThread implements Runnable {
    private Scanner scan;
    private PublicKey publicKey;
    private ObjectOutputStream out;
    private Map<User,ObjectOutputStream> outputStreams = new HashMap<>();


    boolean cont = true;

    public ChattThread(Map<User,ObjectOutputStream> outputStreams) {
        this.outputStreams = outputStreams;
    }

    public void run() {
        while (cont == true) {




            System.out.print("Send > ");
            String str = scan.nextLine();
            try {
                out.writeObject(new Message(publicKey, RSA.encrypt(str,publicKey), Message.Type.MESSAGE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        cont = false;
    }

}