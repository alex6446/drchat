package drchat.client;

public class Color {

    public static final Color[] codes = new Color[] {
        new Color("#edd65c", "#ef933c", "#cb6b13", "#e17f30", "#eba011"), // Orange
        new Color("#ccc4c2", "#9e9397", "#7f747c", "#776c72", "#010101"), // Grey
        new Color("#8bb4d7", "#6999cb", "#4e7abb", "#4768a9", "#84c4f2"), // Blue
        new Color("#97bd4d", "#84c242", "#68b332", "#456c23", "#84e135"), // Green
        new Color("#c55a6b", "#9c1728", "#8c0004", "#8f0006", "#970007"), // Red

    };

    private String[] msg;
    private String av;

    public Color(String msg1, String msg2, String msg3, String msg4, String av) {
        this.msg = new String[]{ msg1, msg2, msg3, msg4 };
        this.av = av;
    }

    public String getMessageCss() {
        return "-fx-background-color: linear-gradient(" +
        msg[0] + " 0%, " +
        msg[1] + " 50%, " + 
        msg[2] + " 50%, " +
        msg[3] + " 100%); ";
    }

    public String getAvatarCss() {
        return "-fx-background-color: " + av + "; ";
    }


}

