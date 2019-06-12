package it.polimi.sw2019.view;

import java.util.List;

public interface UIinterface {

    void requestNickname(boolean isTheFirstTime, List<String> nicknameInTheGame);

    void reconnection();

    void sendOk();

}
