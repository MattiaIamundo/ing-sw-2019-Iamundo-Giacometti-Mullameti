package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weaponeffect_controller_events.TargetSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.SingleTarget;

import java.util.ArrayList;

public abstract class VisibleTargetCont extends EffectController {
    private SingleTarget model;
    protected Player attacker;
    protected ArrayList<Player> players;
    protected Map map;
    protected ArrayList<String> valid = new ArrayList<>();
    protected ArrayList<String> notreachable = new ArrayList<>();

    public VisibleTargetCont(Power model) {
        this.model = (SingleTarget) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTarget();
    }

    protected void acquireTarget(){
        for (Player player : players){
            if ((player != attacker) && (player.isVisible(attacker))){
                valid.add(player.getNickname());
            }else if ((player != attacker) && !(player.isVisible(attacker))){
                notreachable.add(player.getNickname());
            }
        }
    }

    protected void acquireTarget(ArrayList<String> notselctable){
        for (Player player : players){
            if (!(notselctable.contains(player.getNickname())) && (player.isVisible(attacker))){
                valid.add(player.getNickname());
            }else if (!(notselctable.contains(player.getNickname())) && !(player.isVisible(attacker))){
                notreachable.add(player.getNickname());
            }
        }
    }

    public void update(TargetSetEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget())){
                model.setTarget(player);
                return;
            }
        }
        model.setTarget(null);
    }
}
