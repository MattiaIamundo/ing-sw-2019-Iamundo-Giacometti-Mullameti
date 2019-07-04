package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.SecondLockChooseEv;
import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.events.weaponeffect_controller_events.SecondLockSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.SecondLock;
import it.polimi.sw2019.model.weapon_power.LockRifle;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecondLockCont extends VisibleTargetCont implements Observer<SecondLockSetEv> {

    private SecondLock realmodel;
    private Logger logger = Logger.getLogger("controller.SecondLock");

    public SecondLockCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (SecondLock) realmodel;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        try {
            acquireTarget(notselectable());
        }catch (InexistentWeaponException e){
            logger.log(Level.SEVERE, e.getMessage()+"Lock Rifle");
        }
    }

    private ArrayList<String> notselectable() throws InexistentWeaponException{
        ArrayList<String> notselectable = new ArrayList<>();
        Weapon lockrifle;

        notselectable.add(attacker.getNickname());
        lockrifle = attacker.getWeapon("Lock Rifle");
        notselectable.add(((LockRifle) lockrifle.getPower()).getTarget().getNickname());
        return notselectable;
    }

    @Override
    protected void acquireTarget(ArrayList<String> notselctable) {
        super.acquireTarget(notselctable);
        notify(new SecondLockChooseEv(attacker.getNickname(), valid, notselctable, notreachable));
    }

    @Override
    public void update(SecondLockSetEv message) {
        //super.update(message);
        realmodel.usePower(attacker);
    }
}
