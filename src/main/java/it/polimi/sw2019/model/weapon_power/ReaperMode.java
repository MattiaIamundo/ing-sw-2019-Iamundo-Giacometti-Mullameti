package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

/**
 * This class implements the alternative effect of Electroscythe
 * @author Mattia Iamundo
 */
public class ReaperMode implements Power{

    @Override
    public void usePower(Player attacker){
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i).getPosition() == attacker.getPosition()) && (Table.getPlayers(i) != attacker)){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 2);
            }
        }
    }
}
