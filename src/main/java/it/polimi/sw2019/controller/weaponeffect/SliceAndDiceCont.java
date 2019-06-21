package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.SliceAndDiceSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.Cyberblade;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.SliceAndDice;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class SliceAndDiceCont implements Observer<SliceAndDiceSetEv>, EffectController{

    private SliceAndDice model;
    private Player attacker;

    public SliceAndDiceCont(Power model) {
        this.model = (SliceAndDice) model;
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

        notselectable.add(attacker.getNickname());
        initialize(notselectable);
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && !(notselectable.contains(Table.getPlayers(i).getNickname()))){
                if (Table.getPlayers(i).getPosition() == attacker.getPosition()){
                    valid.add(Table.getPlayers(i).getNickname());
                }else {
                    notreachable.add(Table.getPlayers(i).getNickname());
                }
            }
        }
        model.chooseTarget(valid, notselectable, notreachable, attacker);
    }

    private void initialize(ArrayList<String> notselectable){
        int i = 0;
        Weapon cyberblade;

        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Cyberblade"))){
            i++;
        }
        cyberblade = attacker.listWeapon()[i];
        notselectable.add(((Cyberblade) cyberblade.getPower()).getTarget().getNickname());
    }

    @Override
    public void update(SliceAndDiceSetEv message) {
        int i = 0;

        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(message.getTarget()))){
            i++;
        }
        model.setTarget(Table.getPlayers(i));
        model.usePower(attacker);
    }
}
