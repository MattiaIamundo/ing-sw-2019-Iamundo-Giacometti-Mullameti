package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Events.TargetSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class TargetAcquisition implements Observer<TargetSetEv> {
    protected SingleTarget model;
    protected Player attacker;

    public TargetAcquisition(SingleTarget model, Player attacker) {
        this.model = model;
        this.attacker = attacker;
    }

    public  void acquireTarget(boolean notsamesquare){
        int i = 0;
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> notselectable = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();

        while ((i < 5) && (Table.getPlayers(i)!=null) && (Table.getPlayers(i) != attacker)){
            if (Table.getPlayers(i).isVisible(attacker)){
                if (notsamesquare && Table.getPlayers(i).getPosition() == attacker.getPosition()){
                    notreachable.add(Table.getPlayers(i).getNickname());
                }else {
                    valid.add(Table.getPlayers(i).getNickname());
                }
            }else {
                notreachable.add(Table.getPlayers(i).getNickname());
            }
            i++;
        }
        notselectable.add(attacker.getNickname());
        model.chooseTarget(valid, notselectable, notreachable, attacker);
    }

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
