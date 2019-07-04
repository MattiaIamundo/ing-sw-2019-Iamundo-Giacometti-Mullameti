package it.polimi.sw2019.controller.powerup;

import it.polimi.sw2019.events.powerup_events.TargetingScopeChooseEv;
import it.polimi.sw2019.events.powerup_events.TargetingScopeSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.powerup.TargetingScope;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class TargetingScopeCont extends Observable<TargetingScopeChooseEv> implements Observer<TargetingScopeSetEv>, PowerUpController{
    private TargetingScope model;
    private ArrayList<Player> players;
    private Player attacker;

    public TargetingScopeCont(TargetingScope model, ArrayList<Player> players) {
        this.model = model;
        this.players = players;
    }

    @Override
    public void usePowerUp(Player attacker) {
        ArrayList<String> targets = new ArrayList<>();

        this.attacker = attacker;
        for (Player player : attacker.getLastHittedPlayers()){
            targets.add(player.getNickname());
        }
        notify(new TargetingScopeChooseEv(attacker.getNickname(), targets));
    }

    @Override
    public void update(TargetingScopeSetEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget())){
                model.setTarget(player);
                model.useEffect(attacker);
                return;
            }
        }
    }
}
