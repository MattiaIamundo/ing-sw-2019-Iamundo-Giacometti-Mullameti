package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.ChainReactSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.ChainReaction;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.Thor;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class ChainReactionCont extends VisibleTargetCont implements Observer<ChainReactSetEv>{

    private ChainReaction realmodel;

    public ChainReactionCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (ChainReaction) realmodel;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (realmodel.toString().equals(effectname)) {
            this.attacker = attacker;
            acquireTarget(notselectable());
        }
    }

    @Override
    protected void acquireTarget(ArrayList<String> notselctable) {
        super.acquireTarget(notselctable);
        realmodel.chooseTarget(attacker, valid, notselctable, notreachable);
    }

    private ArrayList<String> notselectable(){
        ArrayList<String> notselectable = new ArrayList<>();
        int i = 0;
        Weapon thor;
        notselectable.add(attacker.getNickname());
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("T.H.O.R."))){
            i++;
        }
        thor = attacker.listWeapon()[i];
        notselectable.add(((Thor) thor.getPower()).getTarget().getNickname());
        return notselectable;
    }

    @Override
    public void update(ChainReactSetEv message) {
        //super.update(message);
        realmodel.usePower(attacker);
    }
}
