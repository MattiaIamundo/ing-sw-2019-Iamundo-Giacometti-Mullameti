package it.polimi.sw2019.controller;

import it.polimi.sw2019.controller.powerup.*;
import it.polimi.sw2019.events.PowerUpSetEv;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.PowerUp;
import it.polimi.sw2019.model.powerup.Newton;
import it.polimi.sw2019.model.powerup.TagbackGrenade;
import it.polimi.sw2019.model.powerup.TargetingScope;
import it.polimi.sw2019.model.powerup.Teleporter;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class UsePowerUp implements Observer<PowerUpSetEv>, Action{
    private HashMap<String, PowerUpController> powerUpControllers = new HashMap<>();
    private ArrayList<Player> players;
    private Map map;
    private Player attacker;

    public UsePowerUp(ArrayList<Player> players, Map map) {
        this.players = players;
        this.map = map;
    }

    public UsePowerUp() {
        powerUpControllers.put("TargetingScope", new TargetingScopeCont(new TargetingScope(), players));
        powerUpControllers.put("Newton", new NewtonCont(new Newton(), players));
        powerUpControllers.put("TagbackGrenade", new TagbackGrenadeCont(new TagbackGrenade(), players));
        powerUpControllers.put("Teleporter", new TeleporterCont(new Teleporter(), players, map));
    }

    public void useAction(Player player){
        ArrayList<String> availablePowerUps = new ArrayList<>();

        attacker = player;
        for (PowerUp powerUp : player.getPowerup()){
            availablePowerUps.add(powerUp.getName());
        }
        //TODO notify the player to choose the desidered powerUp
    }

    @Override
    public void update(PowerUpSetEv message) {
        powerUpControllers.get(message.getPowerUp()).usePowerUp(attacker);
    }
}
