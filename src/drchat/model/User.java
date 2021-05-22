package drchat.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @NotNull
    @GeneratedValue
    private int id;

    @NotNull
    private String username; // global name for group

    @NotNull
    private String password;

    @NotNull
    private String shortname;

    @NotNull
    private int colorIndex;

    public User() {}
    public User(int id, String username, String password, String shortname, int colorIndex) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.shortname = shortname;
        this.colorIndex = colorIndex;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getShortname() { return shortname; }
    public int getColorIndex() { return colorIndex; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setSHortname(String shortname) { this.shortname = shortname; }
    public void setColorIndex(int colorIndex) { this.colorIndex = colorIndex; }

}


