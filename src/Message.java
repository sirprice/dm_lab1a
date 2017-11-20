import java.io.Serializable;

/**
 * Created by cj on 2017-11-13.
 */
public class Message implements Serializable {

    private User fromUser;
    private PublicKey publicKeyFromSender;
    private String msg;
    private Type type;


    public Message(PublicKey publicKeyFromSender, String msg, Type type) {
        this.publicKeyFromSender = publicKeyFromSender;
        this.msg = msg;
        this.type = type;
        this.fromUser = null;
    }

    public Message(User fromUser, PublicKey publicKeyFromSender, String msg, Type type) {
        this.fromUser = fromUser;
        this.publicKeyFromSender = publicKeyFromSender;
        this.msg = msg;
        this.type = type;
    }

    public PublicKey getPublicKeyFromSender() {
        return publicKeyFromSender;
    }

    public String getMsg() {
        return msg;
    }

    public Type getType() {
        return type;
    }

    public User getFromUser() {
        return fromUser;
    }

    public enum Type {
        LOGIN,MESSAGE,WELCOME
    }


}
