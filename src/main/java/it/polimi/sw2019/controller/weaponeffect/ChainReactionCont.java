package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.ChainReactChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.TargetSetEv;
import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.events.weaponeffect_controller_events.ChainReactSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.ChainReaction;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.Thor;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represent the controller of Chain reaction, the first optional effect of T.H.O.R.
 */
public class ChainReactionCont extends VisibleTargetCont implements Observer<ChainReactSetEv>{
    private ChainReaction realmodel;
    private Logger logger = Logger.getLogger("controller.ChainReaction");

    /**
     * @param realmodel the model of the effect
     */
    public ChainReactionCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (ChainReaction) realmodel;
    }

    /**
     * This method identify if a player can be chosen as a target or not
     */
    @Override
    protected void acquireTarget() {
        ArrayList<String> notselctable = new ArrayList<>();
        Weapon thor;
        Player prevtarget;
        try {
            notselctable.add(attacker.getNickname());
            thor = attacker.getWeapon("T.H.O.R.");
            prevtarget = ((Thor) thor.getPower()).getTarget();
            notselctable.add(prevtarget.getNickname());
            for (Player player : players){
                if ((player != attacker) && (player != prevtarget) && (player.isVisible(prevtarget))){
                    valid.add(player.getNickname());
                }else if ((player != attacker) && (player != prevtarget) && !(player.isVisible(prevtarget))){
                    notreachable.add(player.getNickname());
                }
            }
            notify(new ChainReactChooseEv(attacker.getNickname(), valid, notselctable, notreachable));
        }catch (InexistentWeaponException e){
            logger.log(Level.SEVERE, e.getMessage()+" doesn't have T.H.O.R.");
        }
    }

    /**
     * This method catch a ChainReactionSetEv event
     * @param message the object which have to be updated
     */
    @Override
    public void update(ChainReactSetEv message) {
        super.update((TargetSetEv) message);
        realmodel.usePower(attacker);
    }
}
