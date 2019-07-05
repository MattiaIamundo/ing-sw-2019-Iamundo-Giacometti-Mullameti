package it.polimi.sw2019.controller.PowerUp;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.controller.powerup.NewtonCont;
import it.polimi.sw2019.events.powerup_events.NewtonChooseEv;
import it.polimi.sw2019.events.powerup_events.NewtonSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.powerup.Newton;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewtonContTest {
    Logger logger = Logger.getLogger("test.PowerUp.NewtonCont");
    ArrayList<Player> players;
    Map map;

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target1");
            game.addPlayers("target2");
            players = (ArrayList<Player>) game.getPlayers();
            map = game.getGameboard().getMap();

            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(0,0));
            players.get(2).setPosition(map.getSpace(3,2));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map");
        }
    }

    @Test
    public void usePowerUp(){
        NewtonCont controller = new NewtonCont(new Newton(), players);
        Catcher catcher = new Catcher();
        ArrayList<String> expectedTarget = new ArrayList<>();

        controller.addObserver(catcher);
        controller.usePowerUp(players.get(0));
        expectedTarget.add("target2");
        expectedTarget.add("target1");

        Assert.assertArrayEquals(expectedTarget.toArray(), catcher.message.getMovements().keySet().toArray());
    }

    private class Catcher implements Observer<NewtonChooseEv>{
        NewtonChooseEv message;

        @Override
        public void update(NewtonChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("target1", "north");
        NewtonCont controller = new NewtonCont(new Newton(), players);

        thrower.addObserver(controller);
        controller.usePowerUp(players.get(0));
        thrower.throwMessage();

        try {
            Assert.assertEquals(map.getSpace(0, 1), players.get(1).getPosition());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map");
        }
    }

    private class Thrower extends Observable<NewtonSetEv>{
        private String target;
        private String moveto;

        public Thrower(String target, String moveto) {
            this.target = target;
            this.moveto = moveto;
        }

        public void throwMessage(){
            notify(new NewtonSetEv(target, moveto));
        }
    }
}
