package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.TargetSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.SingleTarget;

import java.util.ArrayList;

public abstract class SameSquare implements EffectController{

    private SingleTarget model;
    protected Player attacker;
    protected ArrayList<Player> players;
    protected Map map;
    protected ArrayList<String> valid = new ArrayList<>();
    protected ArrayList<String> notreachable = new ArrayList<>();

    public SameSquare(Power model) {
        this.model = (SingleTarget) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTarget();
    }

    protected void acquireTarget() {
        for (Player player : players){
            if ((player != attacker) && (player.getPosition() == attacker.getPosition())){
                valid.add(player.getNickname());
            }else if ((player != attacker) && (player.getPosition() != attacker.getPosition())){
                notreachable.add(player.getNickname());
            }
        }
    }

    public void update(TargetSetEv message) {
        int i = 0;
        while ((i < players.size()) && !(players.get(i).getNickname().equals(message.getTarget()))){
            i++;
        }
        model.setTarget(players.get(i));
    }
}
