package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import java.util.ArrayList;

public class TargetIsVisible extends SingleTargetCont{

    public TargetIsVisible(SingleTarget model) {
        super(model);
    }

    public  void acquireTarget(){
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> notselectable = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker)){
                if (Table.getPlayers(i).isVisible(attacker)){
                    valid.add(Table.getPlayers(i).getNickname());
                }else {
                    notreachable.add(Table.getPlayers(i).getNickname());
                }
            }
        }
        notselectable.add(attacker.getNickname());
        model.chooseTarget(valid, notselectable, notreachable, attacker);
    }
}
