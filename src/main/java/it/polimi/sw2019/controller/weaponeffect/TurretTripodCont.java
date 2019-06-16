package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.events.TurretTripodSetEv;
import it.polimi.sw2019.model.weapon_power.FocusShot;
import it.polimi.sw2019.model.weapon_power.TurretTripod;
import it.polimi.sw2019.model.weapon_power.MachineGun;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class TurretTripodCont extends VisibleTargetCont implements Observer<TurretTripodSetEv> {

    private TurretTripod realmodel;

    public TurretTripodCont(TurretTripod realmodel) {
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

    @Override
    protected void acquireTarget(ArrayList<String> notselctable) {
        super.acquireTarget(notselctable);
        realmodel.chooseTarget(attacker, valid, notselctable, notreachable);
        additionalDamage();
    }

    private void additionalDamage(){
        DoubleAdditive machinegun;
        int i = 0;
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Machine Gun"))){
            i++;
        }
        machinegun = (DoubleAdditive) attacker.listWeapon()[i];
        if ((((MachineGun) machinegun.getPower()).getTarget2() == null) || (((FocusShot) machinegun.getFirstAdditivePower()).getTarget() == ((MachineGun) machinegun.getPower()).getTarget2())){
            realmodel.setPrevioustarget(((MachineGun) machinegun.getPower()).getTarget1());
        }else if ((((MachineGun) machinegun.getPower()).getTarget2() != null) && (((FocusShot) machinegun.getFirstAdditivePower()).getTarget() == ((MachineGun) machinegun.getPower()).getTarget1())){
            realmodel.setPrevioustarget(((MachineGun) machinegun.getPower()).getTarget2());
        }
    }

    private ArrayList<String> notselectable(){
        int i = 0;
        ArrayList<String> notselectable = new ArrayList<>();
        MachineGun basiceffect;
        notselectable.add(attacker.getNickname());
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Machine Gun"))){
            i++;
        }
        basiceffect = (MachineGun) attacker.listWeapon()[i].getPower();
        notselectable.add(basiceffect.getTarget1().getNickname());
        if (basiceffect.getTarget2() != null){
            notselectable.add(basiceffect.getTarget2().getNickname());
        }
        return notselectable;
    }

    @Override
    public void update(TurretTripodSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
