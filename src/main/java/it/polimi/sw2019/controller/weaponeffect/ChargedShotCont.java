package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.ChargedShot;
import it.polimi.sw2019.model.weapon_power.PlasmaGun;
import it.polimi.sw2019.model.weapon_power.Power;

import java.util.ArrayList;

public class ChargedShotCont implements EffectController{

    private ChargedShot model;
    private Player attacker;
    private ArrayList<Player> players;
    private Map map;

    public ChargedShotCont(Power model) {
        this.model = (ChargedShot) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTarget();
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
