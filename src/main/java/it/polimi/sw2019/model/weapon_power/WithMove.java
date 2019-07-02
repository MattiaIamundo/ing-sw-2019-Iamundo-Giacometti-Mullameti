package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

import java.util.ArrayList;
import java.util.Map;

public abstract class WithMove {
    protected Space moveto;

    public void moveAttacker(Player attacker){
        attacker.setPosition(moveto);
    }

    public void setMoveto(Space moveto) {
        this.moveto = moveto;
    }

    public Space getMoveto() {
        return moveto;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
