package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

/**
 * This class implements the basic effect of the Electroscythe
 * @author Mattia Iamundo
 */
public class Electroscythe implements Power{

    @Override
    public void usePower(Player attacker){
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i).getPosition() == attacker.getPosition()) && (Table.getPlayers(i) != attacker)){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 1);
            }
        }
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
