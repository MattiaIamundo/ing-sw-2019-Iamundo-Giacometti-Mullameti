package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.LongBarrelChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.LongBarrelSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.LongBarrelMode;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LongBarrelModeContTest {
    private Logger logger = Logger.getLogger("test.LongBarrelModeCont");
    private ArrayList<Player> players;
    private Map map;
    private LongBarrelMode model = new LongBarrelMode();
    private LongBarrelCont controller = new LongBarrelCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target");
            game.addPlayers("noTarget");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(2,1));
            players.get(1).setPosition(map.getSpace(3,1));
            players.get(2).setPosition(map.getSpace(3,0));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();

        model.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedNotReachable.add("noTarget");
        expectedValid.add("target");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());

    }

    private class Catcher implements Observer<LongBarrelChooseEv>{
        LongBarrelChooseEv message;

        @Override
        public void update(LongBarrelChooseEv message) {
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

    private class Thrower extends Observable<LongBarrelSetEv>{
        String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new LongBarrelSetEv(target));
        }
    }
}
