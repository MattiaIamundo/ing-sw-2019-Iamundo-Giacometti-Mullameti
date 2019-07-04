package it.polimi.sw2019.controller;

import it.polimi.sw2019.controller.powerup.*;
import it.polimi.sw2019.events.powerup_events.PowerUpChooseEv;
import it.polimi.sw2019.events.powerup_events.PowerUpEffectChooseEv;
import it.polimi.sw2019.events.powerup_events.PowerUpSetEv;
import it.polimi.sw2019.events.weapon_event.PowerSetEv;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.PowerUp;
import it.polimi.sw2019.model.powerup.Newton;
import it.polimi.sw2019.model.powerup.TagbackGrenade;
import it.polimi.sw2019.model.powerup.TargetingScope;
import it.polimi.sw2019.model.powerup.Teleporter;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class UsePowerUp extends Observable<PowerUpChooseEv> implements Action, Observer<PowerUpSetEv> {
    private HashMap<String, PowerUpController> powerUpControllers = new HashMap<>();
    private ArrayList<Player> players;
    private Map map;
    private Player attacker;

    public UsePowerUp(ArrayList<Player> players, Map map) {
        this.players = players;
        this.map = map;
        initializePowerUps();
    }

    public void initializePowerUps() {
        powerUpControllers.put("TargetingScope", new TargetingScopeCont(new TargetingScope(), players));
        powerUpControllers.put("Newton", new NewtonCont(new Newton(), players));
        powerUpControllers.put("TagbackGrenade", new TagbackGrenadeCont(new TagbackGrenade(), players));
        powerUpControllers.put("Teleporter", new TeleporterCont(new Teleporter(), players, map));
    }

    public void useAction(Player player){
        ArrayList<String> powerUps = new ArrayList<>();

        attacker = player;
        for (PowerUp powerUp : player.getPowerup()){
            powerUps.add(powerUp.getName());
        }
        notify(new PowerUpChooseEv(player.getNickname(), powerUps));
    }

    @Override
    public void update(PowerUpSetEv message) {
        powerUpControllers.get(message.getPowerUp()).usePowerUp(attacker);
    }

    public HashMap<String, PowerUpController> getPowerUpControllers() {
        return powerUpControllers;
    }
}
