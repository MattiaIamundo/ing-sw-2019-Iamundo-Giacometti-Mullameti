package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

public abstract class SingleTarget {
    protected Player target;

    public void usePower(Player attacker, int quantity) {
        target.getPlance().giveDamage(attacker,quantity);
        target.getPlance().removeMark(attacker);
    }

    public void setMark(Player attacker){
        target.getPlance().setMark(attacker);
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }
}
