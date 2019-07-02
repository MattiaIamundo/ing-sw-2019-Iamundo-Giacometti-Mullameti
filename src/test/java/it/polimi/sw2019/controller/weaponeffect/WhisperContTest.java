package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.WhisperChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.WhisperSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Whisper;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WhisperContTest {
    private Logger logger = Logger.getLogger("test.WhisperCont");
    private ArrayList<Player> players;
    private Map map;
    private Whisper model = new Whisper();
    private WhisperCont controller = new WhisperCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        game.createMap("zero");
        game.addPlayers("attacker");
        game.addPlayers("target");
        game.addPlayers("noTarget1");
        game.addPlayers("noTarget2");
        map = game.getGameboard().getMap();
        players = (ArrayList<Player>) game.getPlayers();
    }

    @Test
    public void acquireTargetTest_AttackerPosition_2_1(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();
        ArrayList<String> expectedNotSelectable = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(2,1));
            players.get(1).setPosition(map.getSpace(3,0));
            players.get(2).setPosition(map.getSpace(2,2));
            players.get(3).setPosition(map.getSpace(0,1));
            controller.addObserver(catcher);
            controller.useEffect(players.get(0), players, map);
            expectedValid.add("target");
            expectedNotSelectable.add("attacker");
            expectedNotSelectable.add("noTarget1");
            expectedNotReachable.add("noTarget2");

            Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
            Assert.assertArrayEquals(expectedNotSelectable.toArray(), catcher.message.getNotselectable().toArray());
            Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest_AttackerPosition_0_1(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();
        ArrayList<String> expectedNotSelectable = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(1,0));
            players.get(1).setPosition(map.getSpace(3,0));
            players.get(2).setPosition(map.getSpace(0,0));
            players.get(3).setPosition(map.getSpace(0,2));
            controller.addObserver(catcher);
            controller.useEffect(players.get(0), players, map);
            expectedValid.add("target");
            expectedNotSelectable.add("attacker");
            expectedNotSelectable.add("noTarget1");
            expectedNotReachable.add("noTarget2");

            Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
            Assert.assertArrayEquals(expectedNotSelectable.toArray(), catcher.message.getNotselectable().toArray());
            Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Catcher implements Observer<WhisperChooseEv>{
        WhisperChooseEv message;

        @Override
        public void update(WhisperChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("target");

        try {
            thrower.addObserver(controller);
            players.get(0).setPosition(map.getSpace(2,1));
            players.get(1).setPosition(map.getSpace(3,0));
            players.get(2).setPosition(map.getSpace(2,2));
            players.get(3).setPosition(map.getSpace(0,1));
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(players.get(1), model.getTarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<WhisperSetEv>{
        private String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new WhisperSetEv(target));
        }
    }
}
