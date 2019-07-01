package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.LockRifleChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.LockRifleSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.LockRifle;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LockRifleContTest {
    private Logger logger = Logger.getLogger("test.LockRifleCont");
    private ArrayList<Player> players;
    private Map map;
    private LockRifle model = new LockRifle();
    private LockRifleCont controller = new LockRifleCont(model);

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
            players.get(2).setPosition(map.getSpace(0,0));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();

        controller.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedValid.add("target");
        expectedNotReachable.add("notarget");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
    }

    private class Catcher implements Observer<LockRifleChooseEv>{
        LockRifleChooseEv message;

        @Override
        public void update(LockRifleChooseEv message) {
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

    private class Thrower extends Observable<LockRifleSetEv>{
        private String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new LockRifleSetEv(target));
        }
    }
}
