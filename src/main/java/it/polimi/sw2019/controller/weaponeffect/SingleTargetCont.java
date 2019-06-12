package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.TargetSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.SingleTarget;

import java.util.ArrayList;

public abstract class SingleTargetCont implements EffectController {
    private SingleTarget model;
    protected Player attacker;
    protected ArrayList<String> valid = new ArrayList<>();
    protected ArrayList<String> notreachable = new ArrayList<>();

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

    protected void acquireTarget(){
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker)){
                if (Table.getPlayers(i).isVisible(attacker)){
                    valid.add(Table.getPlayers(i).getNickname());
                }else {
                    notreachable.add(Table.getPlayers(i).getNickname());
                }
            }
        }
    }

    public void update(TargetSetEv message) {
        int i = 0;
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(message.getTarget()))){
            i++;
        }
        model.setTarget(Table.getPlayers(i));
    }
}
