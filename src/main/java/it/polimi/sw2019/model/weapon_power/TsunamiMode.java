package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

/**
 * This class implements the alternative effect of Shockwave
 * @author Mattia Iamundo
 */
public class TsunamiMode implements Power{

    @Override
    public void usePower(Player attacker){
        for (int i = 0; i < 5; i++) {
            if (isValid(attacker.getPosition(), Table.getPlayers(i).getPosition())){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 1);
            }
        }
    }

    /**
     * This method check if the passed player is exactly 1 move away from the attacker
     * @param attackerposition identify the attacker position
     * @param targetposition identify the position of the player under exam
     * @return true if the player is exactly 1 move away from the attacker, false otherwise
     */
    private boolean isValid(Space attackerposition, Space targetposition){
        if (!attackerposition.getNorth().isWall() && targetposition == attackerposition.getNorth().getSpaceSecond()){
            return true;
        }
        if (!attackerposition.getWest().isWall() && targetposition == attackerposition.getWest().getSpaceSecond()){
            return true;
        }
        if (!attackerposition.getSouth().isWall() && targetposition == attackerposition.getSouth().getSpaceSecond()){
            return true;
        }
        if (!attackerposition.getEast().isWall() && targetposition == attackerposition.getEast().getSpaceSecond()){
            return true;
        }
        return false;
    }
}
