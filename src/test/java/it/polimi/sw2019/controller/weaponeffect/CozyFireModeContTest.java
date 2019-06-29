package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.CozyFireModeChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.CozyFireModeSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.CozyFireMode;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CozyFireModeContTest {
    private ArrayList<Player> players;
    private Map map;
    private Logger logger = Logger.getLogger("test.CozyFireModeCont");

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target1");
            game.addPlayers("target2");
            game.addPlayers("notarget");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(1,1));
            players.get(1).setPosition(map.getSpace(1,0));
            players.get(2).setPosition(map.getSpace(1,2));
            players.get(3).setPosition(map.getSpace(0,1));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        CozyFireMode model = new CozyFireMode();
        CozyFireModeCont cozyFireModeCont = new CozyFireModeCont(model);
        Catcher catcher = new Catcher();
        ArrayList<String> excpected = new ArrayList<>();

        model.addObserver(catcher);
        cozyFireModeCont.useEffect(players.get(0), players, map);
        excpected.add("south");
        excpected.add("north");

        Assert.assertArrayEquals(excpected.toArray(), catcher.message.getDirections().toArray());
    }

    private class Catcher implements Observer<CozyFireModeChooseEv>{
        private CozyFireModeChooseEv message;

        @Override
        public void update(CozyFireModeChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        CozyFireMode cozyFireMode = new CozyFireMode();
        CozyFireModeCont cozyFireModeCont = new CozyFireModeCont(cozyFireMode);
        Thrower thrower = new Thrower("north");

        thrower.addObserver(cozyFireModeCont);
        cozyFireModeCont.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        try {
            Assert.assertNotNull(cozyFireModeCont.getValid().get(thrower.direction));
            Assert.assertEquals(map.getSpace(1, 2), cozyFireModeCont.getValid().get(thrower.direction));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<CozyFireModeSetEv>{
        private String direction;

        public Thrower(String direction) {
            this.direction = direction;
        }

        public void throwMessage(){
            notify(new CozyFireModeSetEv(direction));
        }
    }
}
