package it.polimi.sw2019.events.client_event.Cevent;

import java.util.ArrayList;
import java.util.List;

public class Login {

    private boolean firstTime;
    private List<String> nickname = new ArrayList<>(5);

    public Login(boolean fT, List<String> nick) {
        this.firstTime = fT;
        this.nickname = nick;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public List<String> getNickname() {
        return nickname;
    }

}
