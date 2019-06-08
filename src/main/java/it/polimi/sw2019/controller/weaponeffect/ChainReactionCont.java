package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Events.ChainReactSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.ChainReaction;
import it.polimi.sw2019.model.weapon_power.TwoDamage;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class ChainReactionCont implements Observer<ChainReactSetEv>, EffectController{

    private ChainReaction model;
    private Player attacker;

    public ChainReactionCont(ChainReaction model) {
        this.model = model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)) {
            this.attacker = attacker;
            acquireTarget();
        }
    }

    private void acquireTarget(){
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> notselectable = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();
        Player notvalid = getPreviousTarget();
        notselectable.add(attacker.getNickname());
        notselectable.add(notvalid.getNickname());
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker) && (Table.getPlayers(i) != notvalid)){
                if (Table.getPlayers(i).isVisible(notvalid)){
                    valid.add(Table.getPlayers(i).getNickname());
                }else {
                    notreachable.add(Table.getPlayers(i).getNickname());
                }
            }
        }
        model.chooseTarget(valid, notselectable, notreachable, attacker);
    }

    private Player getPreviousTarget(){
        Weapon thor;
        int i = 0;

        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("T.H.O.R."))){
            i++;
        }
        thor = attacker.listWeapon()[i];
        return ((TwoDamage) thor.getPower()).getTarget();
    }

    @Override
    public void update(ChainReactSetEv message) {
        int i = 0;
        while ((i< 5) && !(Table.getPlayers(i).getNickname().equals(message.getTarget()))){
            i++;
        }
        model.setTarget(Table.getPlayers(i));
    }
}
