package it.polimi.sw2019.controller;

import it.polimi.sw2019.events.powerup_events.PowerUpChooseEv;
import it.polimi.sw2019.exception.PowerUpOutOfBoundException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.PowerUp;
import it.polimi.sw2019.model.powerup.Newton;
import it.polimi.sw2019.model.powerup.TagbackGrenade;
import it.polimi.sw2019.model.powerup.Teleporter;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsePowerUpTest {
    Logger logger = Logger.getLogger("test.UsePowerUp");
    ArrayList<Player> players;
    Map map;

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.addPlayers("attaker");
            game.getPlayers().get(0).addPowerUp(new PowerUp("red", "TagbackGrenade", new TagbackGrenade()));
            game.getPlayers().get(0).addPowerUp((new PowerUp("blue", "Newton", new Newton())));
            game.getPlayers().get(0).addPowerUp(new PowerUp("blue", "Teleporter", new Teleporter()));
            players = (ArrayList<Player>) game.getPlayers();
            map = game.getGameboard().getMap();
        }catch (PowerUpOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 powerups");
        }
    }

    @Test
    public void useAction(){
        UsePowerUp controller = new UsePowerUp(players, map);
        Catcher catcher = new Catcher();
        ArrayList<String> expectedPowerUps = new ArrayList<>();

        controller.addObserver(catcher);
        controller.useAction(players.get(0));
        expectedPowerUps.add("TagbackGrenade");
        expectedPowerUps.add("Newton");
        expectedPowerUps.add("Teleporter");

        Assert.assertArrayEquals(expectedPowerUps.toArray(), catcher.message.getPowerUps().toArray());
    }

    private class Catcher implements Observer<PowerUpChooseEv>{
        PowerUpChooseEv message;

        @Override
        public void update(PowerUpChooseEv message) {
            this.message = message;
        }
    }
}
