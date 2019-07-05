package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.HellionChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.TargetSetEv;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.HellionSetEv;
import it.polimi.sw2019.model.weapon_power.Hellion;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

/**
 * This class represent the controller of Hellion, the basic effect of Hellion
 */
public class HellionCont extends VisibleTargetCont implements Observer<HellionSetEv> {
    private Hellion realmodel;

    /**
     * @param realmodel the model of the effect
     */
    public HellionCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Hellion) realmodel;
    }

    /**
     * This method activate the effect
     * @param attacker is the player that invoke the effect
     * @param players is the list of the players in the math
     * @param gamemap is the map of the match
     */
    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTarget(notSelectable());
    }

    /**
     * This method check if a target can be selected as a target or not
     * @param notselctable the list of the players that can't be selected due to be the attacker or to be the attacker's square
     */
    @Override
    protected void acquireTarget(ArrayList<String> notselctable) {
        super.acquireTarget(notselctable);
        notselctable.remove(attacker.getNickname());
        notreachable.addAll(notselctable);
        notify(new HellionChooseEv(attacker.getNickname(), valid, notreachable));
    }

    /**
     * This method search the players that are on the same square of the attacker
     * @return the list of the players on the attacker's square and the attacker in the first position of the array
     */
    private ArrayList<String> notSelectable(){
        ArrayList<String> notselectable = new ArrayList<>();

        for (Player player : players){
            if (player.getPosition() == attacker.getPosition()){
                notselectable.add(player.getNickname());
            }
        }
        return notselectable;
    }

    /**
     * This method catch a HellionSetEv event
     * @param message the object which have to be updated
     */
    @Override
    public void update(HellionSetEv message) {
        ArrayList<Player> targets = new ArrayList<>();

        super.update((TargetSetEv) message);
        for (Player player : players){
            if (player.getPosition() == realmodel.getTarget().getPosition()){
                targets.add(player);
            }
        }
        realmodel.setMarkTargets(targets);
        realmodel.usePower(attacker);
    }
}
