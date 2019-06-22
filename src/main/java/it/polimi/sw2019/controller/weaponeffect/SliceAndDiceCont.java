package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
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
    private ArrayList<Player> players;

    public SliceAndDiceCont(Power model) {
        this.model = (SliceAndDice) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        acquireTarget();
    }

    private void acquireTarget(){
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> notselectable = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();

        notselectable.add(attacker.getNickname());
        initialize(notselectable);
        for (Player player : players){
            if (!(notselectable.contains(player.getNickname())) && (player.getPosition() == attacker.getPosition())){
                valid.add(player.getNickname());
            }else if (!(notselectable.contains(player.getNickname())) && (player.getPosition() != attacker.getPosition())){
                notreachable.add(player.getNickname());
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
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget())){
                model.setTarget(player);
                model.usePower(attacker);
                break;
            }
        }
    }
}
