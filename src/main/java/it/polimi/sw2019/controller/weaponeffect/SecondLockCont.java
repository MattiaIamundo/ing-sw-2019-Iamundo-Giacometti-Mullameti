package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.events.SecondLockSetEv;
import it.polimi.sw2019.model.weapon_power.SecondLock;
import it.polimi.sw2019.model.weapon_power.TwoDamageMark;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class SecondLockCont extends VisibleTargetCont implements Observer<SecondLockSetEv> {

    private SecondLock realmodel;

    public SecondLockCont(SecondLock realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (realmodel.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTarget(notselectable());
        }
    }

    private ArrayList<String> notselectable(){
        int i = 0;
        ArrayList<String> notselectable = new ArrayList<>();
        Weapon lockrifle;
        notselectable.add(attacker.getNickname());

        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Lock Rifle"))){
            i++;
        }
        lockrifle = attacker.listWeapon()[i];
        notselectable.add(((TwoDamageMark) lockrifle.getPower()).getTarget().getNickname());
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
