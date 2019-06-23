package it.polimi.sw2019.controller.weaponeffect;

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

    public HighVoltageCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (HighVoltage) realmodel;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTarget(notselectable());
    }

    private ArrayList<String> notselectable(){
        Logger logger = Logger.getLogger("controller.HighVoltage");
        ArrayList<String> notselectable = new ArrayList<>();
        DoubleAdditive thor = null;
        for (Weapon weapon : attacker.getWeapons()){
            if (weapon.getName().equals("T.H.O.R.")){
                thor = (DoubleAdditive) weapon;
            }
        }
        try {
            notselectable.add(attacker.getNickname());
            notselectable.add(((Thor) thor.getPower()).getTarget().getNickname());
            notselectable.add(((ChainReaction) thor.getFirstAdditivePower()).getTarget().getNickname());
        }catch (NullPointerException e){
            logger.log(Level.SEVERE,"weapon not found");
        }
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
