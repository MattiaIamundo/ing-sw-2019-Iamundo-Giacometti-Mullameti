package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.events.HighVoltageSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.*;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class HighVoltageCont extends VisibleTargetCont implements Observer<HighVoltageSetEv> {

    private HighVoltage realmodel;

    public HighVoltageCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (HighVoltage) realmodel;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (realmodel.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTarget(notselectable());
        }
    }

    private ArrayList<String> notselectable(){
        ArrayList<String> notselectable = new ArrayList<>();
        int i = 0;
        DoubleAdditive thor;
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("T.H.O.R."))){
            i++;
        }
        thor = (DoubleAdditive) attacker.listWeapon()[i];
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
