package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.events.RocketFistSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.RocketFistMode;
import it.polimi.sw2019.view.Observer;

public class RocketFistCont extends LineFireCont implements EffectController, Observer<RocketFistSetEv> {

    private RocketFistMode realmodel;

    public RocketFistCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (RocketFistMode) realmodel;
    }

    @Override
    public void update(RocketFistSetEv message) {
        realmodel.setMoveto(moveto(message.getDirection()));
        super.update(message);
        realmodel.usePower(attacker);
    }

    private Space moveto(String direction){
        switch (direction){
            case "north":
                if (!attacker.getPosition().getNorth().getSpaceSecond().getNorth().isWall()){
                    return attacker.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond();
                }else {
                    return attacker.getPosition().getNorth().getSpaceSecond();
                }
            case "west":
                if (!attacker.getPosition().getWest().getSpaceSecond().getWest().isWall()){
                    return attacker.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond();
                }else {
                    return attacker.getPosition().getWest().getSpaceSecond();
                }
            case "south":
                if (!attacker.getPosition().getSouth().getSpaceSecond().getSouth().isWall()){
                    return attacker.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond();
                }else {
                    return attacker.getPosition().getSouth().getSpaceSecond();
                }
            case "east":
                if (!attacker.getPosition().getEast().getSpaceSecond().getEast().isWall()){
                    return attacker.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond();
                }else {
                    return attacker.getPosition().getEast().getSpaceSecond();
                }
            default:
                return attacker.getPosition();
        }
    }
}
