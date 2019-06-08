package it.polimi.sw2019.model.Events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class ChainReactChooseEv extends TargetAcquisitionEv{

    public ChainReactChooseEv(Player attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable, String message) {
        super(attacker, valid, notselectable, notreachable, message);
    }
}
