package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponEffectController_events.TurretTripodSetEv;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.FocusShot;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.TurretTripod;
import it.polimi.sw2019.model.weapon_power.MachineGun;
import it.polimi.sw2019.view.Observer;
import sun.rmi.runtime.Log;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TurretTripodCont extends VisibleTargetCont implements Observer<TurretTripodSetEv> {

    private TurretTripod realmodel;

    public TurretTripodCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (TurretTripod) realmodel;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTarget(notselectable());
    }

    @Override
    protected void acquireTarget(ArrayList<String> notselctable) {
        super.acquireTarget(notselctable);
        realmodel.chooseTarget(attacker, valid, notselctable, notreachable);
        additionalDamage();
    }

    private void additionalDamage(){
        Logger logger = Logger.getLogger("controller.TurretTripod");
        DoubleAdditive machinegun = null;
        for (Weapon weapon : attacker.getWeapons()){
            if (weapon.getName().equals("Machine Gun")){
                machinegun = (DoubleAdditive) weapon;
            }
        }
        try {
            if ((((MachineGun) machinegun.getPower()).getTarget2() == null) || (((FocusShot) machinegun.getFirstAdditivePower()).getTarget() == ((MachineGun) machinegun.getPower()).getTarget2())) {
                realmodel.setPrevioustarget(((MachineGun) machinegun.getPower()).getTarget1());
            } else if ((((MachineGun) machinegun.getPower()).getTarget2() != null) && (((FocusShot) machinegun.getFirstAdditivePower()).getTarget() == ((MachineGun) machinegun.getPower()).getTarget1())) {
                realmodel.setPrevioustarget(((MachineGun) machinegun.getPower()).getTarget2());
            }
        }catch (NullPointerException e){
            logger.log(Level.SEVERE,"weapon not found");
        }
    }

    private ArrayList<String> notselectable(){
        Logger logger = Logger.getLogger("controller.TurretTripod");
        ArrayList<String> notselectable = new ArrayList<>();
        notselectable.add(attacker.getNickname());
        MachineGun basiceffect = null;

        for (Weapon weapon : attacker.getWeapons()){
            if (weapon.getName().equals("Machine Gun")){
                basiceffect = (MachineGun) weapon.getPower();
            }
        }
        try {
            notselectable.add(basiceffect.getTarget1().getNickname());
            if (basiceffect.getTarget2() != null) {
                notselectable.add(basiceffect.getTarget2().getNickname());
            }
        }catch (NullPointerException e){
            logger.log(Level.SEVERE,"weapon not found");
        }
        return notselectable;
    }

    @Override
    public void update(TurretTripodSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
