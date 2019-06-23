package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.events.weaponEffectController_events.SecondLockSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.SecondLock;
import it.polimi.sw2019.model.weapon_power.LockRifle;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecondLockCont extends VisibleTargetCont implements Observer<SecondLockSetEv> {

    private SecondLock realmodel;

    public SecondLockCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (SecondLock) realmodel;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTarget(notselectable());
    }

    private ArrayList<String> notselectable(){
        Logger logger = Logger.getLogger("controller.SecondLock");
        ArrayList<String> notselectable = new ArrayList<>();
        Weapon lockrifle = null;
        notselectable.add(attacker.getNickname());
        for (Weapon weapon : attacker.getWeapons()){
            if (weapon.getName().equals("Lock Rifle")){
                lockrifle = weapon;
            }
        }
        try {
            notselectable.add(((LockRifle) lockrifle.getPower()).getTarget().getNickname());
        }catch (NullPointerException e){
            logger.log(Level.SEVERE, "weapon not found");
        }
        return notselectable;
    }

    @Override
    protected void acquireTarget(ArrayList<String> notselctable) {
        super.acquireTarget(notselctable);
        realmodel.chooseTarget(attacker, valid, notselctable, notreachable);
    }

    @Override
    public void update(SecondLockSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
