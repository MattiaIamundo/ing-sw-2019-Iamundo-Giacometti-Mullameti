package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.ChargedShot;
import it.polimi.sw2019.model.weapon_power.PlasmaGun;
import it.polimi.sw2019.model.weapon_power.Power;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Logger logger = Logger.getLogger("controller.ChargedShot");
        Weapon plasmagun;
        try {
            plasmagun = attacker.getWeapon("Plasma Gun");
            model.setTarget(((PlasmaGun) plasmagun.getPower()).getTarget());
            model.usePower(attacker);
        }catch (InexistentWeaponException e){
            logger.log(Level.SEVERE,"weapon not found");
        }
    }
}
