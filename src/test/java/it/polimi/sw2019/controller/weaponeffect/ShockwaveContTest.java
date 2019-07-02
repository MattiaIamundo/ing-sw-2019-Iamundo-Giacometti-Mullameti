package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.ShockwaveChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.ShockwaveSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Shockwave;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShockwaveContTest {
    private Logger logger = Logger.getLogger("test.ShockwaveCont");
    private ArrayList<Player> players;
    private Map map;
    private Shockwave model = new Shockwave();
    private ShockwaveCont controller = new ShockwaveCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        game.createMap("zero");
        game.addPlayers("attacker");
        game.addPlayers("target1");
        game.addPlayers("target2");
        game.addPlayers("target3");
        map = game.getGameboard().getMap();
        players = (ArrayList<Player>) game.getPlayers();
    }

    @Test
    public void acquireTargetTest_AttackerPosition_2_1(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedSquare = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(2,1));
            players.get(1).setPosition(map.getSpace(2,2));
            players.get(2).setPosition(map.getSpace(3,1));
            players.get(3).setPosition(map.getSpace(2,0));
            model.addObserver(catcher);
            controller.useEffect(players.get(0), players, map);
            expectedSquare.add("east");
            expectedSquare.add("south");
            expectedSquare.add("north");

            Assert.assertArrayEquals(expectedSquare.toArray(), catcher.message.getTargets().keySet().toArray());
            Assert.assertEquals(players.get(1).getNickname(), catcher.message.getTargets().get("north").get(0));
            Assert.assertEquals(players.get(2).getNickname(), catcher.message.getTargets().get("east").get(0));
            Assert.assertEquals(players.get(3).getNickname(), catcher.message.getTargets().get("south").get(0));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest_AttackerPosition_3_0(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedSquare = new ArrayList<>();
        ArrayList<String> expectedWestTargets = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(3,0));
            players.get(1).setPosition(map.getSpace(3,1));
            players.get(2).setPosition(map.getSpace(2,0));
            players.get(3).setPosition(map.getSpace(2,0));
            model.addObserver(catcher);
            controller.useEffect(players.get(0), players, map);
            expectedSquare.add("north");
            expectedSquare.add("west");
            expectedWestTargets.add("target2");
            expectedWestTargets.add("target3");

            Assert.assertArrayEquals(expectedSquare.toArray(), catcher.message.getTargets().keySet().toArray());
            Assert.assertEquals(players.get(1).getNickname(), catcher.message.getTargets().get("north").get(0));
            Assert.assertArrayEquals(expectedWestTargets.toArray(), catcher.message.getTargets().get("west").toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Catcher implements Observer<ShockwaveChooseEv>{
        ShockwaveChooseEv message;

        @Override
        public void update(ShockwaveChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest_SingleTarget(){
        Thrower thrower = new Thrower("target1", null, null);

        try {
            players.get(0).setPosition(map.getSpace(2,1));
            players.get(1).setPosition(map.getSpace(2,2));
            players.get(2).setPosition(map.getSpace(3,1));
            players.get(3).setPosition(map.getSpace(2,0));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessageSingle();

            Assert.assertEquals(players.get(1), model.getTarget1());
            Assert.assertNull(model.getTarget2());
            Assert.assertNull(model.getTarget3());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_DoubleTarget(){
        Thrower thrower = new Thrower("target1", "target2", null);

        try {
            players.get(0).setPosition(map.getSpace(2,1));
            players.get(1).setPosition(map.getSpace(2,2));
            players.get(2).setPosition(map.getSpace(3,1));
            players.get(3).setPosition(map.getSpace(2,0));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessageDouble();

            Assert.assertEquals(players.get(1), model.getTarget1());
            Assert.assertEquals(players.get(2), model.getTarget2());
            Assert.assertNull(model.getTarget3());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_TripleTarget(){
        Thrower thrower = new Thrower("target1", "target2", "target3");

        try {
            players.get(0).setPosition(map.getSpace(2,1));
            players.get(1).setPosition(map.getSpace(2,2));
            players.get(2).setPosition(map.getSpace(3,1));
            players.get(3).setPosition(map.getSpace(2,0));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessageTriple();

            Assert.assertEquals(players.get(1), model.getTarget1());
            Assert.assertEquals(players.get(2), model.getTarget2());
            Assert.assertEquals(players.get(3), model.getTarget3());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<ShockwaveSetEv>{
        private String target1;
        private String target2;
        private String target3;

        public Thrower(String target1, String target2, String target3) {
            this.target1 = target1;
            this.target2 = target2;
            this.target3 = target3;
        }

        public void throwMessageSingle(){
            notify(new ShockwaveSetEv(target1));
        }

        public void throwMessageDouble(){
            notify(new ShockwaveSetEv(target1, target2));
        }

        public void throwMessageTriple(){
            notify(new ShockwaveSetEv(target1, target2, target3));
        }
    }
}
