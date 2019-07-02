package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponeffect_controller_events.BarbecueChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the alternative attack for the Flamethrower
 * @author Mattia Iamundo
 */
public class BarbecueMode extends Observable<BarbecueChooseEv> implements Power{

    private Space targetarea1;
    private Space targetarea2;
    private ArrayList<Player> targets;

    /**
     * @param attacker the player who throws the attack
     */
    @Override
    public void usePower(Player attacker){
        for (Player player : targets) {
            if (player.getPosition() == targetarea1){
                player.getPlance().giveDamage(attacker, 2);
            }else if ((targetarea2 != null) && (player.getPosition() == targetarea2)){
                player.getPlance().giveDamage(attacker,1);
            }
            player.getPlance().removeMark(attacker);
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

    public void setTargets(ArrayList<Player> targets) {
        this.targets = targets;
    }

    public Space getTargetarea1() {
        return targetarea1;
    }

    public Space getTargetarea2() {
        return targetarea2;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
