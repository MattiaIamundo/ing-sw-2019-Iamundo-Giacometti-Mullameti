package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.WeaponEvent;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponeffect_controller_events.TargetSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

public abstract class OneMoveAway extends Observable<WeaponEvent> implements EffectController{

    private SingleTarget model;
    protected Player attacker;
    protected ArrayList<Player> players;
    protected Map map;
    protected ArrayList<String> valid = new ArrayList<>();
    protected ArrayList<String> notreachable = new ArrayList<>();
    private ArrayList<Space> validposition = new ArrayList<>();

    public OneMoveAway(Power model) {
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
        loadSquares();
        for (Player player : players){
            if ((player != attacker) && (validposition.contains(player.getPosition()))){
                valid.add(player.getNickname());
            }else if ((player != attacker) && !(validposition.contains(player.getPosition()))){
                notreachable.add(player.getNickname());
            }
        }
    }

    private void loadSquares(){
        if (!attacker.getPosition().getNorth().isWall()){
            validposition.add(attacker.getPosition().getNorth().getSpaceSecond());
        }
        if (!attacker.getPosition().getWest().isWall()){
            validposition.add(attacker.getPosition().getWest().getSpaceSecond());
        }
        if (!attacker.getPosition().getSouth().isWall()){
            validposition.add(attacker.getPosition().getSouth().getSpaceSecond());
        }
        if (!attacker.getPosition().getEast().isWall()){
            validposition.add(attacker.getPosition().getEast().getSpaceSecond());
        }
    }

    public void update(TargetSetEv message){
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget())){
                model.setTarget(player);
                break;
            }
        }
    }
}
