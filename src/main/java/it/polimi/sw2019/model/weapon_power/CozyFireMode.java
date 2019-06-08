package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.IllegalDirectionException;
import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.model.Events.CozyFireChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class implements the alternative power of the Furnace
 * @author Mattia Iamundo
 */
public class CozyFireMode extends Observable<CozyFireChooseEv> implements Power{
    private Space targetarea;

    @Override
    public void usePower(Player attacker){
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition() == targetarea){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 1);
                Table.getPlayers(i).getPlance().setMark(attacker);
            }
        }
    }

    public void chooseTargetArea(ArrayList<String> positions){
        notify(new CozyFireChooseEv(positions));
    }

    public void setTargetarea(Space targetarea) {
        this.targetarea = targetarea;
    }
}
