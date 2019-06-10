package it.polimi.sw2019.model.events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class HighVoltageChooseEv extends TargetAcquisitionEv{

    public HighVoltageChooseEv(Player attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable, String message) {
        super(attacker, valid, notselectable, notreachable, message);
    }
}
