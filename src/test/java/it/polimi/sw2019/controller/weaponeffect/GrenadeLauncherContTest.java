package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.GrenadeLaunchChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.GrenadeLaunchSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.GrenadeLauncher;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrenadeLauncherContTest {
    private Logger logger = Logger.getLogger("test.GrenadeLauncherCont");
    private ArrayList<Player> players;
    private Map map;

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target");
            game.addPlayers("notarget");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(3,2));
            players.get(1).setPosition(map.getSpace(2,1));
            players.get(2).setPosition(map.getSpace(1,1));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        GrenadeLauncher model = new GrenadeLauncher();
        GrenadeLaunchCont controller = new GrenadeLaunchCont(model);
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();
        ArrayList<String> expectedSquares = new ArrayList<>();

        model.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedValid.add("target");
        expectedNotReachable.add("notarget");
        expectedSquares.add("east");
        expectedSquares.add("south");
        expectedSquares.add("north");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
        Assert.assertArrayEquals(expectedSquares.toArray(), catcher.message.getMoveto().get("target").toArray());
    }

    private class Catcher implements Observer<GrenadeLaunchChooseEv>{
        GrenadeLaunchChooseEv message;

        @Override
        public void update(GrenadeLaunchChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest_NoMove(){
        Thrower thrower = new Thrower("target", null);
        GrenadeLauncher model = new GrenadeLauncher();
        GrenadeLaunchCont controller = new GrenadeLaunchCont(model);

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage2();

        Assert.assertNull(model.getMoveto());
        Assert.assertFalse(model.isIsmoved());
        Assert.assertEquals(players.get(1), model.getTarget());
    }

    @Test
    public void updateTest_MoveTarget(){
        Thrower thrower = new Thrower("target", "north");
        GrenadeLauncher model = new GrenadeLauncher();
        GrenadeLaunchCont controller = new GrenadeLaunchCont(model);

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage1();

        try {
            Assert.assertTrue(model.isIsmoved());
            Assert.assertEquals(map.getSpace(2, 2), players.get(1).getPosition());
            Assert.assertEquals(players.get(1), model.getTarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<GrenadeLaunchSetEv>{
        String target;
        String moveto;

        public Thrower(String target, String moveto) {
            this.target = target;
            this.moveto = moveto;
        }

        public void throwMessage1(){
            notify(new GrenadeLaunchSetEv(target, moveto));
        }

        public void throwMessage2(){
            notify(new GrenadeLaunchSetEv(target));
        }
    }
}
