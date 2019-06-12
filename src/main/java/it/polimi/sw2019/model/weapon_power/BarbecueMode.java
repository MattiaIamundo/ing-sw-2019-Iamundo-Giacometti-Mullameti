package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.BarbecueChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the alternative attack for the Flamethrower
 * @author Mattia Iamundo
 */
public class BarbecueMode extends Observable<BarbecueChooseEv> implements Power{

    private Space targetarea1;
    private Space targetarea2;

    /**
     * @param attacker the player who throws the attack
     */
    @Override
    public void usePower(Player attacker){
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition() == targetarea1){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 2);
            }
        }
        if (targetarea2 != null){
            for (int i = 0; i < 5; i++) {
                if (Table.getPlayers(i).getPosition() == targetarea2){
                    Table.getPlayers(i).getPlance().giveDamage(attacker,1);
                }
            }
        }
    }

    public void chooseDirection(Player attacker, ArrayList<String> directions){
        notify(new BarbecueChooseEv(attacker, directions));
    }

    public void setTargetarea1(Space targetarea1) {
        this.targetarea1 = targetarea1;
    }

    public void setTargetarea2(Space targetarea2) {
        this.targetarea2 = targetarea2;
    }
}
