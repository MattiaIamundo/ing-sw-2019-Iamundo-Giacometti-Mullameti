package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.RocketLaunchChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.RocketLaunchSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.RocketLauncher;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RocketLauncherContTest {
    private Logger logger = Logger.getLogger("test.RocketLauncherCont");
    private ArrayList<Player> players;
    private Map map;
    private RocketLauncher model = new RocketLauncher();
    private RocketLauncherCont controller = new RocketLauncherCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target1");
            game.addPlayers("target2");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(2,1));
            players.get(1).setPosition(map.getSpace(3,1));
            players.get(2).setPosition(map.getSpace(2,0));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedMoveTarget1 = new ArrayList<>();
        ArrayList<String>expectedMoveTarget2 = new ArrayList<>();

        controller.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedValid.add("target2");
        expectedValid.add("target1");
        expectedMoveTarget1.add("zero");
        expectedMoveTarget1.add("south");
        expectedMoveTarget1.add("north");
        expectedMoveTarget1.add("west");
        expectedMoveTarget2.add("zero");
        expectedMoveTarget2.add("east");
        expectedMoveTarget2.add("north");
        expectedMoveTarget2.add("west");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getTargets().keySet().toArray());
        Assert.assertArrayEquals(expectedMoveTarget1.toArray(), catcher.message.getTargets().get("target1").toArray());
        Assert.assertArrayEquals(expectedMoveTarget2.toArray(), catcher.message.getTargets().get("target2").toArray());
    }

    private class Catcher implements Observer<RocketLaunchChooseEv>{
        RocketLaunchChooseEv message;

        @Override
        public void update(RocketLaunchChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest_NoMove(){
        Thrower thrower = new Thrower("target1", "zero");

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        try {
            Assert.assertEquals(players.get(1), model.getTarget());
            Assert.assertEquals(map.getSpace(3, 1), model.getOrigin());
            Assert.assertEquals(model.getTarget().getPosition(), model.getOrigin());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_WithMove(){
        Thrower thrower = new Thrower("target1", "south");

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        try {
            Assert.assertEquals(players.get(1), model.getTarget());
            Assert.assertEquals(map.getSpace(3, 1), model.getOrigin());
            Assert.assertEquals(map.getSpace(3,0), model.getTarget().getPosition());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<RocketLaunchSetEv>{
        String target;
        String moveto;

        public Thrower(String target, String moveto) {
            this.target = target;
            this.moveto = moveto;
        }

        public void throwMessage(){
            notify(new RocketLaunchSetEv(target, moveto));
        }
    }
}
