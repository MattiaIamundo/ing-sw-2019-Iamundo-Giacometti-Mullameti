package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the basic effect of Flamethrower
 * @author Mattia Iamundo
 */
public class Flamethrower implements Power, LineFire{

    private Player target1;
    private Player target2;

    @Override
    public void usePower(Player attacker){
        if (target1 != null) {
            target1.getPlance().giveDamage(attacker, 1);
            target1.getPlance().removeMark(attacker);
        }
        if (target2 != null) {
            target2.getPlance().giveDamage(attacker, 1);
            target2.getPlance().removeMark(attacker);
        }
    }

    public void setTarget1(Player target1) {
        this.target1 = target1;
    }

    public void setTarget2(Player target2) {
        this.target2 = target2;
    }

    public Player getTarget1() {
        return target1;
    }

    public Player getTarget2() {
        return target2;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
