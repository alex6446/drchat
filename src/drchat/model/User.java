package drchat.model;

import java.io.Serializable;

public class User implements Serializable {

    public User() {}
    public User(int id, String username, String password, String shortname, int color_index) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.shortname = shortname;
        this.color_index = color_index;
    }

    private int id;
    private String username; // global name for group
    private String password;
    private String shortname;
    private int color_index;

    public int getID() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getShortname() { return shortname; }
    public int getColorIndex() { return color_index; }

    public void setID(int id) { this.id = id; }
    public void setUsername(String name) { username = name; }
    public void setPassword(String name) { username = name; }
    public void setSHortname(String name) { shortname = name; }
    public void setColorIndex(int index) { color_index = index; }

}


