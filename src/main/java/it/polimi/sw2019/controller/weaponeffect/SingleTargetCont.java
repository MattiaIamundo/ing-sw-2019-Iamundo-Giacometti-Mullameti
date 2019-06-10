package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.TargetSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.view.Observer;

public abstract class SingleTargetCont implements Observer<TargetSetEv>, EffectController {
    protected SingleTarget model;
    protected Player attacker;

    public SingleTargetCont(SingleTarget model) {
        this.model = model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTarget();
        }
    }

    public abstract void acquireTarget();

    @Override
    public void update(TargetSetEv message) {
        Player target;
        int i = 0;
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(message.getTarget()))){
            i++;
        }
        target = Table.getPlayers(i);
        model.setTarget(target);
        model.usePower(attacker);
    }
}
