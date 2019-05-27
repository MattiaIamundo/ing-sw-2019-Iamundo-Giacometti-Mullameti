package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.*;
import it.polimi.sw2019.model.Events.RailGunChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class implements the basic effect of Railgun
 * @author Mattia Iamundo
 */
public class RailGun extends Observable<RailGunChooseEv> implements Power{
    private Player target = null;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 3);
        target = null;
    }

    public void chooseTarget(HashMap<String, ArrayList<String>> valid){
        notify(new RailGunChooseEv(valid));
    }

    public void setTarget(Player target){
        this.target = target;
    }
}
