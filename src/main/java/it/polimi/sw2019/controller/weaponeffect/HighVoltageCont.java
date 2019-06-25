package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weaponEffectController_events.HighVoltageSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.*;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HighVoltageCont extends VisibleTargetCont implements Observer<HighVoltageSetEv> {

    private HighVoltage realmodel;
    private Logger logger = Logger.getLogger("controller.HighVoltage");

    public HighVoltageCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (HighVoltage) realmodel;
    }

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

    private ArrayList<String> notselectable() throws InexistentWeaponException{

        ArrayList<String> notselectable = new ArrayList<>();
        DoubleAdditive thor;

        thor = (DoubleAdditive) attacker.getWeapon("T.H.O.R.");
        notselectable.add(attacker.getNickname());
        notselectable.add(((Thor) thor.getPower()).getTarget().getNickname());
        notselectable.add(((ChainReaction) thor.getFirstAdditivePower()).getTarget().getNickname());
        return notselectable;
    }

    @Override
    protected void acquireTarget(ArrayList<String> notselctable) {
        super.acquireTarget(notselctable);
        realmodel.chooseTarget(attacker, valid, notselctable, notreachable);
    }

    @Override
    public void update(HighVoltageSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
