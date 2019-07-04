package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.TargetSetEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.TurretTripodChooseEv;
import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.TurretTripodSetEv;
import it.polimi.sw2019.model.weapon_power.FocusShot;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.TurretTripod;
import it.polimi.sw2019.model.weapon_power.MachineGun;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TurretTripodCont extends VisibleTargetCont implements Observer<TurretTripodSetEv> {

    private TurretTripod realmodel;
    private Logger logger = Logger.getLogger("controller.TurretTripod");

    public TurretTripodCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (TurretTripod) realmodel;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;

        try {
            acquireTarget(notselectable());
        }catch (InexistentWeaponException e){
            logger.log(Level.SEVERE, e.getMessage()+" doesn't have Machine Gun");
        }
    }

    @Override
    protected void acquireTarget(ArrayList<String> notselctable) {
        super.acquireTarget(notselctable);
        additionalDamage();
        notify(new TurretTripodChooseEv(attacker.getNickname(), valid, notselctable, notreachable));
    }

    private void additionalDamage(){
        DoubleAdditive machinegun;

        try {
            machinegun = (DoubleAdditive) attacker.getWeapon("Machine Gun");
            if ((((MachineGun) machinegun.getPower()).getTarget2() != null) && (((FocusShot) machinegun.getFirstAdditivePower()).getTarget() == ((MachineGun) machinegun.getPower()).getTarget2())) {
                realmodel.setPrevioustarget(((MachineGun) machinegun.getPower()).getTarget());
            } else if ((((MachineGun) machinegun.getPower()).getTarget2() != null) && (((FocusShot) machinegun.getFirstAdditivePower()).getTarget() == ((MachineGun) machinegun.getPower()).getTarget())) {
                realmodel.setPrevioustarget(((MachineGun) machinegun.getPower()).getTarget2());
            }else if (((MachineGun) machinegun.getPower()).getTarget2() == null){
                realmodel.setPrevioustarget(((MachineGun) machinegun.getPower()).getTarget());
            }
        }catch (InexistentWeaponException e){
            logger.log(Level.SEVERE,e.getMessage()+" doesn't have Machine Gun");
        }
    }

    private ArrayList<String> notselectable() throws InexistentWeaponException{
        ArrayList<String> notselectable = new ArrayList<>();
        MachineGun basiceffect;
        notselectable.add(attacker.getNickname());

        basiceffect = (MachineGun) attacker.getWeapon("Machine Gun").getPower();
        notselectable.add(basiceffect.getTarget().getNickname());
        if (basiceffect.getTarget2() != null) {
            notselectable.add(basiceffect.getTarget2().getNickname());
        }
        return notselectable;
    }

    @Override
    public void update(TurretTripodSetEv message) {
        super.update((TargetSetEv) message);
        realmodel.usePower(attacker);
    }
}
