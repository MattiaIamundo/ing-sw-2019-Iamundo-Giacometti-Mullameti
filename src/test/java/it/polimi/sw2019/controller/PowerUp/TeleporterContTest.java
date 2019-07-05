package it.polimi.sw2019.controller.PowerUp;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.controller.powerup.TeleporterCont;
import it.polimi.sw2019.events.powerup_events.TeleporterChooseEv;
import it.polimi.sw2019.events.powerup_events.TeleporterSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.powerup.Teleporter;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeleporterContTest {
    Logger logger = Logger.getLogger("test.controller.PowerUp.Teleporter");
    ArrayList<Player> players;
    Map map;

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target");
            players = (ArrayList<Player>) game.getPlayers();
            map = game.getGameboard().getMap();

            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(3,2));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map");
        }
    }

    @Test
    public void usePowerUpTest(){
        TeleporterCont controller = new TeleporterCont(new Teleporter(), players, map);
        Catcher catcher = new Catcher();
        ArrayList<String> expectedTarget = new ArrayList<>();

        controller.addObserver(catcher);
        controller.usePowerUp(players.get(0));
        expectedTarget.add("target");

        Assert.assertArrayEquals(expectedTarget.toArray(), catcher.message.getPlayers().toArray());
        Assert.assertEquals(12, catcher.message.getPositions().size());
    }

    private class Catcher implements Observer<TeleporterChooseEv>{
        TeleporterChooseEv message;

        @Override
        public void update(TeleporterChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        TeleporterCont controller = new TeleporterCont(new Teleporter(), players, map);
        Thrower thrower = new Thrower("target", "0-0");

        thrower.addObserver(controller);
        controller.usePowerUp(players.get(0));
        thrower.throwMessage();

        Assert.assertEquals(players.get(0).getPosition(), players.get(1).getPosition());
    }

    private class Thrower extends Observable<TeleporterSetEv>{
        private String target;
        private String position;

        public Thrower(String target, String position) {
            this.target = target;
            this.position = position;
        }

        public void throwMessage(){
            notify(new TeleporterSetEv(target, position));
        }
    }
}
