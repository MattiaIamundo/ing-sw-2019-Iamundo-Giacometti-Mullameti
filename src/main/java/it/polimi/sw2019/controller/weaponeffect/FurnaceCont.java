package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.events.FurnaceSetEv;
import it.polimi.sw2019.model.weapon_power.Furnace;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class FurnaceCont implements Observer<FurnaceSetEv>, EffectController {

    private Furnace model;
    private Player attacker;

    public FurnaceCont(Furnace model, Player attacker) {
        this.model = model;
        this.attacker = attacker;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireRooms();
        }
    }

    private void acquireRooms(){
        ArrayList<String> rooms = new ArrayList<>();
        String attackerroom = attacker.getPosition().getRoom();
        if (!(attacker.getPosition().getNorth().isWall()) && !(attacker.getPosition().getNorth().getSpaceSecond().getRoom().equals(attackerroom))){
            rooms.add(attacker.getPosition().getNorth().getSpaceSecond().getRoom());
        }
        if (!(attacker.getPosition().getWest().isWall()) && !(attacker.getPosition().getWest().getSpaceSecond().getRoom().equals(attackerroom))){
            rooms.add(attacker.getPosition().getWest().getSpaceSecond().getRoom());
        }
        if (!(attacker.getPosition().getSouth().isWall()) && !(attacker.getPosition().getSouth().getSpaceSecond().getRoom().equals(attackerroom))){
            rooms.add(attacker.getPosition().getSouth().getSpaceSecond().getRoom());
        }
        if (!(attacker.getPosition().getEast().isWall()) && !(attacker.getPosition().getEast().getSpaceSecond().getRoom().equals(attackerroom))){
            rooms.add(attacker.getPosition().getEast().getSpaceSecond().getRoom());
        }
        model.chooseRoom(attacker, rooms);
    }

    @Override
    public void update(FurnaceSetEv message) {
        model.setRoom(message.getRoom());
        model.usePower(attacker);
    }
}
