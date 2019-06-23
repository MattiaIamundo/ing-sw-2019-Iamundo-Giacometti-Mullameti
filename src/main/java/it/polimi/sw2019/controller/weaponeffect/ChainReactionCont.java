package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weaponEffectController_events.ChainReactSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.ChainReaction;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.Thor;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChainReactionCont extends VisibleTargetCont implements Observer<ChainReactSetEv>{

    private ChainReaction realmodel;


    public ChainReactionCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (ChainReaction) realmodel;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTarget(notselectable());
    }

    @Override
    protected void acquireTarget(ArrayList<String> notselctable) {
        super.acquireTarget(notselctable);
        realmodel.chooseTarget(attacker, valid, notselctable, notreachable);
    }

    private ArrayList<String> notselectable(){
        Logger logger = Logger.getLogger("controller.ChainReaction");
        ArrayList<String> notselectable = new ArrayList<>();
        Weapon thor = null;
        notselectable.add(attacker.getNickname());
        for (Weapon weapon : attacker.getWeapons()){
            if (weapon.getName().equals("T.H.O.R.")){
                thor = weapon;
            }
        }
        try {
            notselectable.add(((Thor) thor.getPower()).getTarget().getNickname());
        }catch (NullPointerException e){
            logger.log(Level.SEVERE,"weapon not found");
        }
        return notselectable;
    }

    @Override
    public void update(ChainReactSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
