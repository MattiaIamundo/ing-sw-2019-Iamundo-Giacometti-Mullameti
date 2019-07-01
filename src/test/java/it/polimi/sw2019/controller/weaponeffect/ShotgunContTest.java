package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.ShotgunChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.ShotgunSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Shotgun;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShotgunContTest {
    private Logger logger = Logger.getLogger("test.ShotgunCont");
    private ArrayList<Player> players;
    private Map map;
    private Shotgun model = new Shotgun();
    private ShotgunCont controller = new ShotgunCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target");
            game.addPlayers("noTarget");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(1,0));
            players.get(1).setPosition(map.getSpace(1,0));
            players.get(2).setPosition(map.getSpace(3,1));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();
        ArrayList<String> expectedMoveTo = new ArrayList<>();

        controller.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedValid.add("target");
        expectedNotReachable.add("noTarget");
        expectedMoveTo.add("east");
        expectedMoveTo.add("north");
        expectedMoveTo.add("west");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
        Assert.assertArrayEquals(expectedMoveTo.toArray(), catcher.message.getMoveto().toArray());
    }

    private class Catcher implements Observer<ShotgunChooseEv>{
        ShotgunChooseEv message;

        @Override
        public void update(ShotgunChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest_WithMove(){
        Thrower thrower = new Thrower("target", "north");

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        try {
            Assert.assertEquals(players.get(1), model.getTarget());
            Assert.assertEquals(map.getSpace(1, 1), model.getMoveto());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_NoMove(){
        Thrower thrower = new Thrower("target", null);

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(players.get(1), model.getTarget());
        Assert.assertNull(model.getMoveto());
    }

    private class Thrower extends Observable<ShotgunSetEv>{
        private String target;
        private String moveTo;

        public Thrower(String target, String moveTo) {
            this.target = target;
            this.moveTo = moveTo;
        }

        public void throwMessage(){
            notify(new ShotgunSetEv(target, moveTo));
        }
    }
}
