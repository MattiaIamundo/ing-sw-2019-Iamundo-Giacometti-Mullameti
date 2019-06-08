package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Events.HighVoltageSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.ChainReaction;
import it.polimi.sw2019.model.weapon_power.HighVoltage;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class HighVoltageCont implements Observer<HighVoltageSetEv>, EffectController {

    private HighVoltage model;
    private Player attacker;

    public HighVoltageCont(HighVoltage model) {
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
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> notselectable = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();
        Player prevtarget = getSecondTarget();

        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker) && (Table.getPlayers(i) != prevtarget)){
                if (!Table.getPlayers(i).isVisible(attacker)){
                    notreachable.add(Table.getPlayers(i).getNickname());
                }else {
                    valid.add(Table.getPlayers(i).getNickname());
                }
            }
        }
        notselectable.add(attacker.getNickname());
        notselectable.add(prevtarget.getNickname());
        model.chooseTarget(valid, notselectable, notreachable, attacker);
    }

    private Player getSecondTarget(){
        int i = 0;
        DoubleAdditive thor;
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("T.H.O.R."))){
            i++;
        }
        thor = (DoubleAdditive) attacker.listWeapon()[i];
        return ((ChainReaction) thor.getFirstAdditivePower()).getTarget();
    }

    @Override
    public void update(HighVoltageSetEv message) {
        int i = 0;
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(message.getTarget()))){
            i++;
        }
        model.setTarget(Table.getPlayers(i));
    }
}
