package drchat.model;

import java.io.Serializable;

public class SocketMessage implements Serializable {

    public enum Type { REGISTER, LOGIN, ACTIVATION, MESSAGE, USER };

    private Type type;
    private Serializable object;

    public SocketMessage() { } 
    public SocketMessage(Type type, Serializable object) {
        this.type = type;
        this.object = object;
    }

    public Type getType() { return type; }
    public Serializable getObject() { return object; }

    public void setType(Type type) { this.type = type; }
    public void setObject(Serializable object) { this.object = object; }
}
