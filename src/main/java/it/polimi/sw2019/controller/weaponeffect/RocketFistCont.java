package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponEffectController_events.RocketFistSetEv;
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
        super.update(message);
        realmodel.setMoveto(moveto(message.getDirection(), message.getMoveAmount()));
        realmodel.usePower(attacker);
    }

    private Space moveto(String direction, int amount){
        switch (direction){
            case "north":
                if (amount == 2){
                    return attacker.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond();
                }else if (amount == 1){
                    return attacker.getPosition().getNorth().getSpaceSecond();
                }else {
                    break;
                }
            case "west":
                if (amount == 2){
                    return attacker.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond();
                }else if (amount == 1){
                    return attacker.getPosition().getWest().getSpaceSecond();
                }else {
                    break;
                }
            case "south":
                if (amount == 2){
                    return attacker.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond();
                }else if (amount == 1){
                    return attacker.getPosition().getSouth().getSpaceSecond();
                }else {
                    break;
                }
            case "east":
                if (amount == 2){
                    return attacker.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond();
                }else if (amount == 1){
                    return attacker.getPosition().getEast().getSpaceSecond();
                }else {
                    break;
                }
            default:
                return attacker.getPosition();
        }
        return attacker.getPosition();
    }

}
