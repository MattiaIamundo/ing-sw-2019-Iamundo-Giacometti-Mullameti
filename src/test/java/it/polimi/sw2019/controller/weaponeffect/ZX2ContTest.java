package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.ZX2ChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.ZX2SetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.ZX2;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZX2ContTest {
    private Logger logger = Logger.getLogger("test.Zx2Cont");
    private ArrayList<Player> players;
    private Map map;
    private ZX2 model = new ZX2();
    private ZX2Cont controller = new ZX2Cont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(1,2));
            players.get(1).setPosition(map.getSpace(1,1));
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
        expectedValid.add("target");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertTrue(catcher.message.getNotreachable().isEmpty());
    }

    private class Catcher implements Observer<ZX2ChooseEv>{
        ZX2ChooseEv message;

        @Override
        public void update(ZX2ChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("target");

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(players.get(1), model.getTarget());
    }

    private class Thrower extends Observable<ZX2SetEv>{
        private String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new ZX2SetEv(target));
        }
    }
}
