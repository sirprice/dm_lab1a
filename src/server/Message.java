package server;

import java.io.Serializable;

/**
 * Created by cj on 2017-11-20.
 */
public class Message implements Serializable{

    private Client sendingClient;
    private String msg;
    private Type type;


    public Message(String msg, Type type) {
        this.msg = msg;
        this.type = type;
        this.sendingClient = null;
    }

    public Message(Client sendingClient, String msg, Type type) {
        this.sendingClient = sendingClient;
        this.msg = msg;
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public Type getType() {
        return type;
    }

    public Client getFromUser() {
        return sendingClient;
    }

    public enum Type {
        LOGIN,MESSAGE,WELCOME
    }
}
