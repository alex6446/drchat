package drchat;

import java.io.Serializable;

public class SocketMessage implements Serializable {

    public enum Type { USER, MESSAGE, CONNECTION };

    private Type type;
    private Serializable msg;

    public SocketMessage(Type msgType, Serializable msgObj) {
        this.type = msgType;
        this.msg = msgObj;
    }

    public Type getMessageType() { return type; }
    public Serializable getMessageObject() { return type; }

    public void setMessageType(Type msgType) { type = msgType; }
    public void setMessageObject(Serializable msgObj) { msg = msgObj; }
}
