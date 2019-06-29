package it.polimi.sw2019.controller;

import it.polimi.sw2019.controller.powerup.*;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.PowerUp;
import it.polimi.sw2019.model.powerup.Newton;
import it.polimi.sw2019.model.powerup.TagbackGrenade;
import it.polimi.sw2019.model.powerup.Teleporter;

import java.util.ArrayList;
import java.util.HashMap;

public class UsePowerUp implements Action{
    private HashMap<String, PowerUpController> powerUpControllers = new HashMap<>();
    private ArrayList<Player> players;
    private Map map;

    public UsePowerUp() {
        powerUpControllers.put("TargetingScope", new TargetingScopeCont());
        powerUpControllers.put("Newton", new NewtonCont(new Newton(), players));
        powerUpControllers.put("TagbackGrenade", new TagbackGrenadeCont(new TagbackGrenade(), players));
        powerUpControllers.put("Teleporter", new TeleporterCont(new Teleporter(), players, map));
    }

    public void useAction(Player player){

    }
}
