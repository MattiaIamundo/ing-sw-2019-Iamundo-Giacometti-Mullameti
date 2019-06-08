package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.*;
import it.polimi.sw2019.model.Events.RocketLaunchChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class implements the basic effect of Rocket Launcher
 * @author Mattia Iamundo
 */
public class RocketLauncher extends Observable<RocketLaunchChooseEv> implements Power{

    Player target;
    Space origin;

    @Override
    public void usePower(Player attacker){

    }

    public void setTarget(Player target, Space newposition) {
        this.target = target;
        origin = target.getPosition();
        this.target.setPosition(newposition);
    }

    public void chooseTarget(HashMap<String, ArrayList<String>> targets){
        notify(new RocketLaunchChooseEv(targets));
    }
}
