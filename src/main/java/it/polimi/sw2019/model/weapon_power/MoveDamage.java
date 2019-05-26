package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Events.TracBeamChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class MoveDamage extends Observable<TracBeamChooseEv> implements Power{
    //base effect for TractorBeam
    private Player target = null;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 1);
    }

    public void chooseTarget(HashMap<String, ArrayList<String>> valid, ArrayList<String> notselectable){
        notify(new TracBeamChooseEv(valid, notselectable));
    }

    public void setTarget(Player target, Space position){
        this.target = target;
        target.setPosition(position);
    }
}
