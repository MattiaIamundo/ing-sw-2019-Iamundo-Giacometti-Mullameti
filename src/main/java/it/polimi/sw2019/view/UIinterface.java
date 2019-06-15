package it.polimi.sw2019.view;

import java.util.List;

public interface UIinterface {

    void requestNickname(boolean isTheFirstTime, List<String> nicknameInTheGame);

    void requestColor(boolean firstTime, boolean duplicated, List<String> colorlist);

    void reconnection();

    void sendOk();

}
