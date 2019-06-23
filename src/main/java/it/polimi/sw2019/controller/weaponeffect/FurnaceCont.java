package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponEffectController_events.FurnaceSetEv;
import it.polimi.sw2019.model.weapon_power.Furnace;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class FurnaceCont implements Observer<FurnaceSetEv>, EffectController {

    private Furnace model;
    private Player attacker;

    public FurnaceCont(Power model) {
        this.model = (Furnace) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        acquireRooms();
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
