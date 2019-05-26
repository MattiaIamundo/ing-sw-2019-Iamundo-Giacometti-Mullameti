package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.SingleTarget;

import java.util.ArrayList;

public class SameSquare extends TargetAcquisition{

    public SameSquare(SingleTarget model, Player attacker) {
        super(model, attacker);
    }

    public void acquireTarget(Player attacker) {
        int i = 0;
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> notselectable = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();

        while ((i < 5) && (Table.getPlayers(i) != attacker)){
            if (Table.getPlayers(i).getPosition() == attacker.getPosition()){
                valid.add(Table.getPlayers(i).getNickname());
            }else {
                notreachable.add(Table.getPlayers(i).getNickname());
            }
        }
        notselectable.add(attacker.getNickname());
        model.chooseTarget(valid, notselectable, notreachable, attacker);
    }
}
