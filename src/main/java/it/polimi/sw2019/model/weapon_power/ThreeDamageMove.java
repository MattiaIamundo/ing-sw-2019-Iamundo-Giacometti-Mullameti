package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.events.PositionChooseEv;
import it.polimi.sw2019.model.events.TargetAcquisitionEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class implements the basic effect of Shotgun
 * @author Mattia Iamundo
 */
public class ThreeDamageMove extends SingleTarget implements WithMove{
    private Player target = null;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 3);
    }

    @Override
    public void chooseTarget(ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable, Player attacker) {
        notify(new TargetAcquisitionEv(attacker, valid, notselectable, notreachable, "Select a target that is on your square"));
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    public void changePosition(Player attacker, Map<String, Space> positions){
        notify(new PositionChooseEv(attacker, positions, "Select one of the possible square to move the target in, or just press enter leave it there"));
    }

    @Override
    public void setNewPosition(Space position) {
        target.setPosition(position);
    }
}
