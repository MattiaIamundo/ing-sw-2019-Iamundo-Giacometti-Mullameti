package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

/**
 * This class implements the basic effect of Rocket Launcher
 * @author Mattia Iamundo
 */
public class RocketLauncher extends SingleTarget implements Power{
    private Space origin;

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,2);
    }

    public void setTarget(Player target, Space newposition) {
        this.target = target;
        origin = target.getPosition();
        this.target.setPosition(newposition);
    }

    public Space getOrigin() {
        return origin;
    }

}
