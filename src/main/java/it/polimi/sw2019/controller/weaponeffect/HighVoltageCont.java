package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.HighVoltageChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.TargetSetEv;
import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weaponeffect_controller_events.HighVoltageSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.*;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represent the controller of High voltage, the second optional effect of T.H.O.R.
 */
public class HighVoltageCont extends VisibleTargetCont implements Observer<HighVoltageSetEv> {
    private HighVoltage realmodel;
    private Player prevTarget;
    private Logger logger = Logger.getLogger("controller.HighVoltage");

    /**
     * @param realmodel the model of the effect
     */
    public HighVoltageCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (HighVoltage) realmodel;
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
        try {
            acquireTarget(notselectable());
        }catch (InexistentWeaponException e){
            logger.log(Level.SEVERE,e.getMessage()+" doesn't have T.H.O.R.");
        }
    }

    /**
     * This method identify the players that can0t be selected as a target due to be the attacker or one of the previous targets
     * @return the list of the players that can't be selected, in first position of the array the attacker
     * @throws InexistentWeaponException
     */
    private ArrayList<String> notselectable() throws InexistentWeaponException{
        ArrayList<String> notselectable = new ArrayList<>();
        DoubleAdditive thor;

        thor = (DoubleAdditive) attacker.getWeapon("T.H.O.R.");
        notselectable.add(attacker.getNickname());
        notselectable.add(((Thor) thor.getPower()).getTarget().getNickname());
        notselectable.add(((ChainReaction) thor.getFirstAdditivePower()).getTarget().getNickname());
        prevTarget = ((ChainReaction) thor.getFirstAdditivePower()).getTarget();
        return notselectable;
    }

    /**
     * This method check if a player can be selected as a target or not
     * @param notselctable is the list of the payers that can't be selected
     */
    @Override
    protected void acquireTarget(ArrayList<String> notselctable) {
        for (Player player : players){
            if (!(notselctable.contains(player.getNickname())) && (player.isVisible(prevTarget))){
                valid.add(player.getNickname());
            }else if (!(notselctable.contains(player.getNickname()))){
                notreachable.add(player.getNickname());
            }
        }
        notify(new HighVoltageChooseEv(attacker.getNickname(), valid, notselctable, notreachable));
    }

    /**
     * This method catch a HighVoltageSetEv event
     * @param message the object which have to be updated
     */
    @Override
    public void update(HighVoltageSetEv message) {
        super.update((TargetSetEv) message);
        realmodel.usePower(attacker);
    }
}
