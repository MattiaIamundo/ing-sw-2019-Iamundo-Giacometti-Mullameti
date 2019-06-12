package it.polimi.sw2019.events.server_event.VCevent;

public class VCLogin {

    private String nickname;

    public VCLogin(String nick) {
        this.nickname = nick;
    }

    public String getNickname() {
        return nickname;
    }
}
