package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the basic effect of Shockwave
 * @author Mattia Iamundo
 */
public class Shockwave implements Power{

    private Player target1;
    private Player target2;
    private Player target3;

    @Override
    public void usePower(Player attacker){
        target1.getPlance().giveDamage(attacker, 1);
        target1.getPlance().removeMark(attacker);
        if (target2 != null){
            target2.getPlance().giveDamage(attacker, 1);
            target2.getPlance().removeMark(attacker);
            if (target3 != null){
                target3.getPlance().giveDamage(attacker,1);
                target3.getPlance().removeMark(attacker);
            }
        }
    }

    public Player getTarget1() {
        return target1;
    }

    public void setTarget1(Player target1) {
        this.target1 = target1;
    }

    public Player getTarget2() {
        return target2;
    }

    public void setTarget2(Player target2) {
        this.target2 = target2;
    }

    public Player getTarget3() {
        return target3;
    }

    public void setTarget3(Player target3) {
        this.target3 = target3;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
