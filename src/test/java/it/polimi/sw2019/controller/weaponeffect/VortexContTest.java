package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.VortexChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.VortexSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Vortex;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VortexContTest {
    private Logger logger = Logger.getLogger("test.VortexCont");
    private ArrayList<Player> players;
    private Map map;
    private Vortex model = new Vortex();
    private VortexCont controller = new VortexCont(model);

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
    public void acquireTargetTest_AttackerPosition_2_2(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedVortexPositions = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(2,2));
            players.get(1).setPosition(map.getSpace(3,1));
            players.get(2).setPosition(map.getSpace(2,0));
            players.get(3).setPosition(map.getSpace(1,2));
            model.addObserver(catcher);
            controller.useEffect(players.get(0), players, map);
            expectedVortexPositions.add("2-0");
            expectedVortexPositions.add("1-2");
            expectedVortexPositions.add("2-1");
            expectedVortexPositions.add("3-0");
            expectedVortexPositions.add("3-1");
            expectedVortexPositions.add("3-2");

            Assert.assertArrayEquals(expectedVortexPositions.toArray(), catcher.message.getValidchoices().keySet().toArray());
            Assert.assertEquals("target1", catcher.message.getValidchoices().get("3-1").get(0));
            Assert.assertEquals("target1", catcher.message.getValidchoices().get("3-2").get(0));
            Assert.assertEquals("target1", catcher.message.getValidchoices().get("3-0").get(0));
            Assert.assertEquals("target1", catcher.message.getValidchoices().get("2-1").get(0));
            Assert.assertEquals("target2", catcher.message.getValidchoices().get("2-0").get(0));
            Assert.assertEquals("target2", catcher.message.getValidchoices().get("2-1").get(1));
            Assert.assertEquals("target2", catcher.message.getValidchoices().get("3-0").get(1));
            Assert.assertEquals("target3", catcher.message.getValidchoices().get("1-2").get(0));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest_AttackerPosition_1_0(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedVortexPositions = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(1,0));
            players.get(1).setPosition(map.getSpace(0,1));
            players.get(2).setPosition(map.getSpace(1,1));
            players.get(3).setPosition(map.getSpace(3,1));
            model.addObserver(catcher);
            controller.useEffect(players.get(0), players, map);
            expectedVortexPositions.add("0-0");
            expectedVortexPositions.add("1-1");
            expectedVortexPositions.add("2-0");
            expectedVortexPositions.add("2-1");
            expectedVortexPositions.add("3-0");
            expectedVortexPositions.add("3-1");

            Assert.assertArrayEquals(expectedVortexPositions.toArray(), catcher.message.getValidchoices().keySet().toArray());
            Assert.assertEquals("target1", catcher.message.getValidchoices().get("0-0").get(0));
            Assert.assertEquals("target2", catcher.message.getValidchoices().get("1-1").get(0));
            Assert.assertEquals("target3", catcher.message.getValidchoices().get("3-1").get(0));
            Assert.assertEquals("target3", catcher.message.getValidchoices().get("3-0").get(0));
            Assert.assertEquals("target3", catcher.message.getValidchoices().get("2-1").get(0));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Catcher implements Observer<VortexChooseEv>{
        VortexChooseEv message;

        @Override
        public void update(VortexChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("0-0", "target1");

        try {
            players.get(0).setPosition(map.getSpace(1,0));
            players.get(1).setPosition(map.getSpace(0,1));
            players.get(2).setPosition(map.getSpace(1,2));
            players.get(3).setPosition(map.getSpace(3,2));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(map.getSpace(0,0), model.getVortex());
            Assert.assertEquals(players.get(1), model.getTarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<VortexSetEv>{
        private String vortex;
        private String target;

        public Thrower(String vortex, String target) {
            this.vortex = vortex;
            this.target = target;
        }

        public void throwMessage(){
            notify(new VortexSetEv(target, vortex));
        }
    }
}
