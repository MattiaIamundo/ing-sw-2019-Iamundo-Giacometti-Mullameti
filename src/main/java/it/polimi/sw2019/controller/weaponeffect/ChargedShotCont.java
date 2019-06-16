package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.ChargedShot;
import it.polimi.sw2019.model.weapon_power.PlasmaGun;

public class ChargedShotCont implements EffectController{

    private ChargedShot model;
    private Player attacker;

    public ChargedShotCont(ChargedShot model) {
        this.model = model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTarget();
        }
    }

    private void acquireTarget(){
        Weapon plasmagun;
        int i = 0;
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Plasma Gun"))){
            i++;
        }
        plasmagun = attacker.listWeapon()[i];
        model.setTarget(((PlasmaGun) plasmagun.getPower()).getTarget());
        model.usePower(attacker);
    }
}
