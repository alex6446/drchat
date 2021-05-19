package drchat.model;

import java.io.Serializable;

public class SocketMessage implements Serializable {

    public enum Type { ACTIVATION, LOGIN, USER, MESSAGE, FAIL, GET_USERS };

    private Type type;
    private Serializable msg;

    public SocketMessage() { } 
    public SocketMessage(Type msgType, Serializable msgObj) {
        this.type = msgType;
        this.msg = msgObj;
    }

    public Type getMessageType() { return type; }
    public Serializable getMessageObject() { return msg; }

    public void setMessageType(Type msgType) { type = msgType; }
    public void setMessageObject(Serializable msgObj) { msg = msgObj; }
}
