package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

/**
 * This class implements the second optional effect of Rocket Launcher
 * @author Mattia Iamundo
 */
public class FragmentingWarhead implements Power{

    private Player target;
    private Space originsquare;

    @Override
    public void usePower(Player attacker){
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition() == originsquare){
                Table.getPlayers(i).getPlance().giveDamage(attacker,1);
            }
        }
        if (target.getPosition() != originsquare){
            target.getPlance().giveDamage(attacker,1);
        }
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public void setOriginsquare(Space originsquare) {
        this.originsquare = originsquare;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
