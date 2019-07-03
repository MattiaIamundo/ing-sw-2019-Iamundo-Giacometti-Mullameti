package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.RocketLaunchChooseEv;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weaponeffect_controller_events.RocketLaunchSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.RocketLauncher;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class RocketLauncherCont extends EffectController implements Observer<RocketLaunchSetEv> {

    private RocketLauncher model;
    private Player attacker;
    private ArrayList<Player> players;
    private HashMap<String, HashMap<String, Space>> targets = new HashMap<>();

    public RocketLauncherCont(Power model) {
        this.model = (RocketLauncher) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        acquireTarget();
    }

    private void acquireTarget() {
        for (Player player : players){
            if ((player != attacker) && (player.isVisible(attacker)) && (player.getPosition() != attacker.getPosition())){
                targets.put(player.getNickname(), acquireMovements(player.getPosition()));
            }
        }
        HashMap<String, ArrayList<String>> valid = new HashMap<>();
        for (HashMap.Entry<String, HashMap<String, Space>> a : targets.entrySet()){
            valid.put(a.getKey(), new ArrayList<>(a.getValue().keySet()));
        }
        notify(new RocketLaunchChooseEv(attacker.getNickname(), valid));
    }

    private HashMap<String, Space> acquireMovements(Space tarpos){
        HashMap<String, Space> positions = new HashMap<>();

        positions.put("zero", tarpos);
        if (!tarpos.getEast().isWall()){
            positions.put("east", tarpos.getEast().getSpaceSecond());
        }
        if (!tarpos.getWest().isWall()){
            positions.put("west", tarpos.getWest().getSpaceSecond());
        }
        if (!tarpos.getNorth().isWall()){
            positions.put("north", tarpos.getNorth().getSpaceSecond());
        }
        if (!tarpos.getSouth().isWall()){
            positions.put("south", tarpos.getSouth().getSpaceSecond());
        }
        return positions;
    }

    @Override
    public void update(RocketLaunchSetEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget())){
                model.setTarget(player, targets.get(player.getNickname()).get(message.getPosition()));
                model.usePower(attacker);
                break;
            }
        }
    }
}
