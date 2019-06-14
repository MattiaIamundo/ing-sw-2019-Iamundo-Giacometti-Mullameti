package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.FragmentingWarhead;
import it.polimi.sw2019.model.weapon_power.RocketLauncher;

public class FragmentingWarheadCont implements EffectController{

    private FragmentingWarhead model;
    private Player attacker;

    public FragmentingWarheadCont(FragmentingWarhead model) {
        this.model = model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            initialize();
        }
    }

    private void initialize(){
        Weapon rocketlauncher;
        int i = 0;

        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Rocket Launcher"))){
            i++;
        }
        rocketlauncher = attacker.listWeapon()[i];
        model.setTarget(((RocketLauncher) rocketlauncher.getPower()).getTarget());
        model.setOriginsquare(((RocketLauncher) rocketlauncher.getPower()).getOrigin());
        model.usePower(attacker);
    }
}
