package client.client;


import client.crypto.PublicKey;

import java.io.Serializable;

/**
 * Created by cj on 2017-11-20.
 */
public class Message implements Serializable {

    private PublicKey sendingClientPK;
    private String msg;
    private byte[] msgBytes;
    private Message.Type type;

    public Message(PublicKey sendingClientPK, String msg, Type type) {
        this.sendingClientPK = sendingClientPK;
        this.msg = msg;
        this.type = type;
    }

    public Message(PublicKey sendingClientPK, byte[] msgBytes, Type type) {
        this.sendingClientPK = sendingClientPK;
        this.msgBytes = msgBytes;
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public byte[] getMsgBytes() {
        return msgBytes;
    }

    public Message.Type getType() {
        return type;
    }

    public PublicKey fromClientPK() {
        return sendingClientPK;
    }

    public enum Type {
        LOGIN,MESSAGE,WELCOME
    }
}
