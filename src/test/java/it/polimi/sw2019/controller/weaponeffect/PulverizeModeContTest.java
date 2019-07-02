package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.PulvModeChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.PulvModeSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.PulverizeMode;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PulverizeModeContTest {
    private Logger logger = Logger.getLogger("test.PulverizeModeCont");
    private ArrayList<Player> players;
    private Map map;
    private PulverizeMode model = new PulverizeMode();
    private PulverizeModeCont controller = new PulverizeModeCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        game.createMap("zero");
        game.addPlayers("attacker");
        game.addPlayers("target");
        map = game.getGameboard().getMap();
        players = (ArrayList<Player>) game.getPlayers();
    }

    @Test
    public void acquireTargetTest_Position_3_2(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedDirection = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(3,2));
            players.get(1).setPosition(map.getSpace(3,2));
            controller.addObserver(catcher);
            controller.useEffect(players.get(0), players, map);
            expectedValid.add("target");
            expectedDirection.add("zero");
            expectedDirection.add("south-south");
            expectedDirection.add("south");
            expectedDirection.add("west");
            expectedDirection.add("west-west");

            Assert.assertArrayEquals(expectedDirection.toArray(), catcher.message.getMovements().toArray());
            Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getTargets().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest_Position_0_0(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedDirection = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(0,0));
            controller.addObserver(catcher);
            controller.useEffect(players.get(0), players, map);
            expectedValid.add("target");
            expectedDirection.add("zero");
            expectedDirection.add("east");
            expectedDirection.add("north");
            expectedDirection.add("east-east");
            expectedDirection.add("north-north");

            Assert.assertArrayEquals(expectedDirection.toArray(), catcher.message.getMovements().toArray());
            Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getTargets().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Catcher implements Observer<PulvModeChooseEv> {
        PulvModeChooseEv message;

        @Override
        public void update(PulvModeChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest_NoMove(){
        Thrower thrower = new Thrower("target", "zero");

        try {
            players.get(0).setPosition(map.getSpace(3,2));
            players.get(1).setPosition(map.getSpace(3,2));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(map.getSpace(3,2), model.getMoveto());
            Assert.assertEquals(players.get(1), model.getTarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_MoveSingleSquare(){
        Thrower thrower = new Thrower("target", "west");

        try {
            players.get(0).setPosition(map.getSpace(3,2));
            players.get(1).setPosition(map.getSpace(3,2));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(map.getSpace(2,2), model.getMoveto());
            Assert.assertEquals(players.get(1), model.getTarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_MoveDoubleSquares(){
        Thrower thrower = new Thrower("target", "west-west");

        try {
            players.get(0).setPosition(map.getSpace(3,2));
            players.get(1).setPosition(map.getSpace(3,2));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(map.getSpace(1,2), model.getMoveto());
            Assert.assertEquals(players.get(1), model.getTarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<PulvModeSetEv>{
        String target;
        String direction;

        public Thrower(String target, String direction) {
            this.target = target;
            this.direction = direction;
        }

        public void throwMessage(){
            notify(new PulvModeSetEv(target, direction));
        }
    }
}
