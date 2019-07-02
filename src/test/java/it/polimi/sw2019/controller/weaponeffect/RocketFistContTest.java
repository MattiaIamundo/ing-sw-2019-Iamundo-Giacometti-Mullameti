package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.RocketFistChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.RocketFistSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.RocketFistMode;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RocketFistContTest {
    private Logger logger = Logger.getLogger("test.RocketFistCont");
    private ArrayList<Player> players;
    private Map map;
    private RocketFistMode model = new RocketFistMode();
    private RocketFistCont controller = new RocketFistCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        game.createMap("zero");
        game.addPlayers("attacker");
        game.addPlayers("target1");
        game.addPlayers("target2");
        map = game.getGameboard().getMap();
        players = (ArrayList<Player>) game.getPlayers();
    }

    @Test
    public void acquireTargetTest_South(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedFirstSquare = new ArrayList<>();
        ArrayList<String> expectedSecondSquare = new ArrayList<>();

        try {
            controller.addObserver(catcher);
            players.get(0).setPosition(map.getSpace(2,2));
            players.get(1).setPosition(map.getSpace(2,1));
            players.get(2).setPosition(map.getSpace(2,0));
            controller.useEffect(players.get(0), players, map);
            expectedFirstSquare.add("target1");
            expectedSecondSquare.add("target2");

            Assert.assertArrayEquals(expectedFirstSquare.toArray(), catcher.message.getFirstlevel().get("south").toArray());
            Assert.assertArrayEquals(expectedSecondSquare.toArray(), catcher.message.getSecondlevel().get("south").toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest_West(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedFirstSquare = new ArrayList<>();
        ArrayList<String> expectedSecondSquare = new ArrayList<>();

        try {
            controller.addObserver(catcher);
            players.get(0).setPosition(map.getSpace(2,2));
            players.get(1).setPosition(map.getSpace(1,2));
            players.get(2).setPosition(map.getSpace(0,2));
            controller.useEffect(players.get(0), players, map);
            expectedFirstSquare.add("target1");
            expectedSecondSquare.add("target2");

            Assert.assertArrayEquals(expectedFirstSquare.toArray(), catcher.message.getFirstlevel().get("west").toArray());
            Assert.assertArrayEquals(expectedSecondSquare.toArray(), catcher.message.getSecondlevel().get("west").toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest_North(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedFirstSquare = new ArrayList<>();
        ArrayList<String> expectedSecondSquare = new ArrayList<>();

        try {
            controller.addObserver(catcher);
            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(0,1));
            players.get(2).setPosition(map.getSpace(0,2));
            controller.useEffect(players.get(0), players, map);
            expectedFirstSquare.add("target1");
            expectedSecondSquare.add("target2");

            Assert.assertArrayEquals(expectedFirstSquare.toArray(), catcher.message.getFirstlevel().get("north").toArray());
            Assert.assertArrayEquals(expectedSecondSquare.toArray(), catcher.message.getSecondlevel().get("north").toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest_East(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedFirstSquare = new ArrayList<>();
        ArrayList<String> expectedSecondSquare = new ArrayList<>();

        try {
            controller.addObserver(catcher);
            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(1,0));
            players.get(2).setPosition(map.getSpace(2,0));
            controller.useEffect(players.get(0), players, map);
            expectedFirstSquare.add("target1");
            expectedSecondSquare.add("target2");

            Assert.assertArrayEquals(expectedFirstSquare.toArray(), catcher.message.getFirstlevel().get("east").toArray());
            Assert.assertArrayEquals(expectedSecondSquare.toArray(), catcher.message.getSecondlevel().get("east").toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Catcher implements Observer<RocketFistChooseEv>{
        RocketFistChooseEv message;

        @Override
        public void update(RocketFistChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest_North_OneMove(){
        Thrower thrower = new Thrower("target1", null, 1, "north");

        try {
            thrower.addObserver(controller);
            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(0,1));
            players.get(2).setPosition(map.getSpace(0,2));
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(players.get(1), model.getTarget1());
            Assert.assertNull(model.getTarget2());
            Assert.assertEquals(map.getSpace(0,1), model.getMoveto());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_West_OneMove(){
        Thrower thrower = new Thrower("target1", null, 1, "west");

        try {
            thrower.addObserver(controller);
            players.get(0).setPosition(map.getSpace(2,2));
            players.get(1).setPosition(map.getSpace(1,2));
            players.get(2).setPosition(map.getSpace(0,2));
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(players.get(1), model.getTarget1());
            Assert.assertNull(model.getTarget2());
            Assert.assertEquals(map.getSpace(1,2), model.getMoveto());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_South_OneMove(){
        Thrower thrower = new Thrower("target1", null, 1, "south");

        try {
            thrower.addObserver(controller);
            players.get(0).setPosition(map.getSpace(2,2));
            players.get(1).setPosition(map.getSpace(2,1));
            players.get(2).setPosition(map.getSpace(2,0));
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(players.get(1), model.getTarget1());
            Assert.assertNull(model.getTarget2());
            Assert.assertEquals(map.getSpace(2,1), model.getMoveto());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_East_OneMove(){
        Thrower thrower = new Thrower("target1", null, 1, "east");

        try {
            thrower.addObserver(controller);
            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(1,0));
            players.get(2).setPosition(map.getSpace(2,0));
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(players.get(1), model.getTarget1());
            Assert.assertNull(model.getTarget2());
            Assert.assertEquals(map.getSpace(1,0), model.getMoveto());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_North_TwoMove(){
        Thrower thrower = new Thrower("target1", "target2", 2, "north");

        try {
            thrower.addObserver(controller);
            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(0,1));
            players.get(2).setPosition(map.getSpace(0,2));
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(players.get(1), model.getTarget1());
            Assert.assertEquals(players.get(2), model.getTarget2());
            Assert.assertEquals(map.getSpace(0,2), model.getMoveto());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_West_TwoMove(){
        Thrower thrower = new Thrower("target1", "target2", 2, "west");

        try {
            thrower.addObserver(controller);
            players.get(0).setPosition(map.getSpace(2,2));
            players.get(1).setPosition(map.getSpace(1,2));
            players.get(2).setPosition(map.getSpace(0,2));
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(players.get(1), model.getTarget1());
            Assert.assertEquals(players.get(2), model.getTarget2());
            Assert.assertEquals(map.getSpace(0,2), model.getMoveto());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_South_TwoMove(){
        Thrower thrower = new Thrower("target1", "target2", 2, "south");

        try {
            thrower.addObserver(controller);
            players.get(0).setPosition(map.getSpace(2,2));
            players.get(1).setPosition(map.getSpace(2,1));
            players.get(2).setPosition(map.getSpace(2,0));
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(players.get(1), model.getTarget1());
            Assert.assertEquals(players.get(2), model.getTarget2());
            Assert.assertEquals(map.getSpace(2,0), model.getMoveto());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_East_TwoMove(){
        Thrower thrower = new Thrower("target1", "target2", 2, "east");

        try {
            thrower.addObserver(controller);
            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(1,0));
            players.get(2).setPosition(map.getSpace(2,0));
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(players.get(1), model.getTarget1());
            Assert.assertEquals(players.get(2), model.getTarget2());
            Assert.assertEquals(map.getSpace(2,0), model.getMoveto());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<RocketFistSetEv>{
        private String target1;
        private String target2;
        private int amount;
        private String direction;

        public Thrower(String target1, String target2, int amount, String direction) {
            this.target1 = target1;
            this.target2 = target2;
            this.amount = amount;
            this.direction = direction;
        }

        public void throwMessage(){
            notify(new RocketFistSetEv(direction, amount, target1, target2));
        }
    }
}
