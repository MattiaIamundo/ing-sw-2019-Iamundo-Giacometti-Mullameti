package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the basic effect of Cyberblade and Sledgehammer
 * @author Mattia Iamundo
 */
public class TwoDamageSameSpace implements Power{

    private Player target;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 2);
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }
}
