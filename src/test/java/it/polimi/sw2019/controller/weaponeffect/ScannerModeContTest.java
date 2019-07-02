package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.ScannerModeChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.ScannerModeSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.ScannerMode;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScannerModeContTest {
    private Logger logger = Logger.getLogger("test.ScannerModeCont");
    private ArrayList<Player> players;
    private Map map;
    private ScannerMode model = new ScannerMode();
    private ScannerModeCont controller = new ScannerModeCont(model);

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

            players.get(0).setPosition(map.getSpace(1,1));
            players.get(1).setPosition(map.getSpace(1,2));
            players.get(2).setPosition(map.getSpace(1,0));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();

        controller.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedValid.add("target1");
        expectedValid.add("target2");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertTrue(catcher.message.getNotreachable().isEmpty());
    }

    private class Catcher implements Observer<ScannerModeChooseEv>{
        ScannerModeChooseEv message;

        @Override
        public void update(ScannerModeChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("target1", "target2", null);

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(players.get(1), model.getTarget1());
        Assert.assertEquals(players.get(2), model.getTarget2());
        Assert.assertNull(model.getTarget3());
    }

    private class Thrower extends Observable<ScannerModeSetEv>{
        String target1;
        String target2;
        String target3;

        public Thrower(String target1, String target2, String target3) {
            this.target1 = target1;
            this.target2 = target2;
            this.target3 = target3;
        }

        public void throwMessage(){
            notify(new ScannerModeSetEv(target1, target2, target3));
        }
    }
}
