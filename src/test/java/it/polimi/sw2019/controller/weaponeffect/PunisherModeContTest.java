package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.PunisherModeChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.PunisherModeSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.PunisherMode;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PunisherModeContTest {
    private Logger logger = Logger.getLogger("test.PunisherModeCont");
    private ArrayList<Player> players;
    private Map map;
    private PunisherMode model = new PunisherMode();
    private PunisherModeCont controller = new PunisherModeCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        game.createMap("zero");
        game.addPlayers("attacker");
        game.addPlayers("target");
        game.addPlayers("noTarget");
        map = game.getGameboard().getMap();
        players = (ArrayList<Player>) game.getPlayers();
    }

    @Test
    public void acquireTargetTest_Position_3_2(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(3,2));
            players.get(1).setPosition(map.getSpace(2,1));
            players.get(2).setPosition(map.getSpace(1,1));
            model.addObserver(catcher);
            controller.useEffect(players.get(0), players, map);
            expectedValid.add("target");
            expectedNotReachable.add("noTarget");

            Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
            Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest_Position_0_0(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(2,0));
            players.get(2).setPosition(map.getSpace(2,2));
            model.addObserver(catcher);
            controller.useEffect(players.get(0), players, map);
            expectedValid.add("target");
            expectedNotReachable.add("noTarget");

            Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
            Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Catcher implements Observer<PunisherModeChooseEv>{
        private PunisherModeChooseEv message;

        @Override
        public void update(PunisherModeChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("target");

        try {
            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(2,0));
            players.get(2).setPosition(map.getSpace(2,2));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(players.get(1), model.getTarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<PunisherModeSetEv>{
        private String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new PunisherModeSetEv(target));
        }
    }
}
