package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.HellionSetEv;
import it.polimi.sw2019.model.weapon_power.Hellion;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class HellionCont extends VisibleTargetCont implements Observer<HellionSetEv> {

    private Hellion realmodel;

    public HellionCont(Hellion realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (realmodel.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTarget(notSelectable());
        }
    }

    @Override
    protected void acquireTarget(ArrayList<String> notselctable) {
        super.acquireTarget(notselctable);
        notselctable.remove(attacker.getNickname());
        notreachable.addAll(notselctable);
        realmodel.chooseTarget(attacker, valid, notreachable);
    }

    private ArrayList<String> notSelectable(){
        ArrayList<String> notselectable = new ArrayList<>();

        notselectable.add(attacker.getNickname());
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition() == attacker.getPosition()){
                notselectable.add(Table.getPlayers(i).getNickname());
            }
        }
        return notselectable;
    }

    @Override
    public void update(HellionSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
