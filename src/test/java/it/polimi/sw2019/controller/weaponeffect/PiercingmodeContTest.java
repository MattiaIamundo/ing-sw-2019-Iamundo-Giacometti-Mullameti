package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponEffectController_events.PiercingModeChooseEv;
import it.polimi.sw2019.events.weaponEffectController_events.PiercingModeSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.PiercingMode;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PiercingmodeContTest {
    private Logger logger = Logger.getLogger("test.PiercingModeCont");
    private ArrayList<Player> players;
    private Map map;
    private PiercingMode model = new PiercingMode();
    private PiercingModeCont controller = new PiercingModeCont(model);

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
            players.get(1).setPosition(map.getSpace(1,1));
            players.get(2).setPosition(map.getSpace(0,1));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();

        model.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedValid.add("target1");
        expectedValid.add("target2");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getTargets().get("west").toArray());
    }

    private class Catcher implements Observer<PiercingModeChooseEv>{
        private PiercingModeChooseEv message;

        @Override
        public void update(PiercingModeChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest_SingleTarget(){
        Thrower thrower = new Thrower("target1", null);

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage1();

        Assert.assertEquals(players.get(1), model.getTarget1());
        Assert.assertNull(model.getTarget2());
    }

    @Test
    public void updateTest_DoubleTarget(){
        Thrower thrower = new Thrower("target1", "target2");

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage2();

        Assert.assertEquals(players.get(1), model.getTarget1());
        Assert.assertEquals(players.get(2), model.getTarget2());
    }

    private class Thrower extends Observable<PiercingModeSetEv>{
        private String target1;
        private String target2;

        public Thrower(String target1, String target2) {
            this.target1 = target1;
            this.target2 = target2;
        }

        public void throwMessage1(){
            notify(new PiercingModeSetEv(target1));
        }

        public void throwMessage2(){
            notify(new PiercingModeSetEv(target1, target2));
        }
    }
}
