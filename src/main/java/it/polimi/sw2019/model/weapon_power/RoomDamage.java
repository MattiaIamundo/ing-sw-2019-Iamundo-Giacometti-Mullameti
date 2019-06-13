package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.FurnaceChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implement the basic effect of Furnace
 * @author Mattia Iamundo
 */
public class RoomDamage extends Observable<FurnaceChooseEv> implements Power{

    private String room;

    @Override
    public void usePower(Player attacker){
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition().getRoom().equals(room)){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 1);
            }
        }
    }

    public void chooseRoom(Player attacker, ArrayList<String> rooms){
        notify(new FurnaceChooseEv(attacker, rooms));
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
