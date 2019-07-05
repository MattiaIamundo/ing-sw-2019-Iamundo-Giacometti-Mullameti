package it.polimi.sw2019.controller;

import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.NotYourTurnEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;

public class EndTurn extends Observable<NotifyReturn> implements Action {

    private Player player;

    public void useAction(Player player) {
        //this player want to change turn
        NotYourTurnEv notYourTurnEv = new NotYourTurnEv(null,null);
        notYourTurnEv.setNickname(player.getNickname());
        notify(notYourTurnEv);
    }
}
