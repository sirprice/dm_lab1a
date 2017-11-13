import java.io.Serializable;

/**
 * Created by cj on 2017-11-13.
 */
public class Message implements Serializable {

    private PublicKey publicKeyFromSender;
    private String msg;


    public Message(PublicKey publicKeyFromSender, String msg) {
        this.publicKeyFromSender = publicKeyFromSender;
        this.msg = msg;
    }

    public PublicKey getPublicKeyFromSender() {
        return publicKeyFromSender;
    }

    public String getMsg() {
        return msg;
    }


}
