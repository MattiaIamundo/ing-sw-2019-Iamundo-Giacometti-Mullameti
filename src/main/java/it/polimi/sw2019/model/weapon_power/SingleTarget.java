package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Events.WeaponEvent;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

public abstract class SingleTarget extends Observable<WeaponEvent> implements Power{

    public abstract void chooseTarget(ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable, Player attacker);

    public abstract void setTarget(Player target);

}
