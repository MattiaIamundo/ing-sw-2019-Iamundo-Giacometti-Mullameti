package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.FocusShotSetEv;
import it.polimi.sw2019.model.weapon_power.FocusShot;
import it.polimi.sw2019.model.weapon_power.TwoTargetDamage;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class FocusShotCont implements Observer<FocusShotSetEv>, EffectController {

    private FocusShot model;
    private Player attacker;

    public FocusShotCont(FocusShot model, Player attacker) {
        this.model = model;
        this.attacker = attacker;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTarget();
        }
    }

    public void acquireTarget(){
        ArrayList<String> targets = new ArrayList<>();
        TwoTargetDamage basiceffect;
        int i = 0;

        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Machine Gun"))){
            i++;
        }
        basiceffect = (TwoTargetDamage) attacker.listWeapon()[i].getPower();
        targets.add(basiceffect.getTarget1().getNickname());
        if (basiceffect.getTarget2() != null){
            targets.add(basiceffect.getTarget2().getNickname());
        }
        model.chooseTarget(attacker, targets);
    }

    @Override
    public void update(FocusShotSetEv message) {
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getNickname().equals(message.getTarget())){
                model.setTarget(Table.getPlayers(i));
            }
        }
        model.usePower(attacker);
    }
}
