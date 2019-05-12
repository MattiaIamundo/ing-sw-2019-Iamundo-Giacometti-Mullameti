package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Events.TargetAcquisitionEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

public abstract class TargetAcquisition extends Observable<TargetAcquisitionEv> {
    public static Player acquireTarget(Player attacker){
        int i = 0;
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> notselectable = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();

        while ((i < 5) && (Table.getPlayers(i)!=null) && (Table.getPlayers(i) != attacker)){
            if (Table.getPlayers(i).isVisible(attacker)){
                valid.add(Table.getPlayers(i).getNickname());
            }else {
                notreachable.add(Table.getPlayers(i).getNickname());
            }
            i++;
        }
        notselectable.add(attacker.getNickname());
        TargetAcquisition view;
        view.notify(new TargetAcquisitionEv(attacker, valid, notselectable, notreachable, "Select a player that you can see"));
    }
}
