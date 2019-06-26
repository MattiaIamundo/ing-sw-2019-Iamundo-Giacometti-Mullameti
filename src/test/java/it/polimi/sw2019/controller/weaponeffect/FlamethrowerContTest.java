package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponEffectController_events.FlamethrowerChooseEv;
import it.polimi.sw2019.events.weaponEffectController_events.FlamethrowerSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Flamethrower;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlamethrowerContTest {
    private Logger logger = Logger.getLogger("test.FlamethrowerCont");
    private ArrayList<Player> players;
    private Map map;

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

            players.get(0).setPosition(map.getSpace(3,2));
            players.get(1).setPosition(map.getSpace(2,2));
            players.get(2).setPosition(map.getSpace(1,2));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        Flamethrower model = new Flamethrower();
        FlamethrowerCont controller = new FlamethrowerCont(model);
        ArrayList<String> expectedDirections = new ArrayList<>();
        ArrayList<String> expectedTarget1 = new ArrayList<>();
        ArrayList<String> expectedTarget2 = new ArrayList<>();

        model.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedDirections.add("west");
        expectedTarget1.add("target1");
        expectedTarget2.add("target2");

        Assert.assertArrayEquals(expectedDirections.toArray(), catcher.message.getFirstline().keySet().toArray());
        Assert.assertArrayEquals(expectedDirections.toArray(), catcher.message.getSecondline().keySet().toArray());
        Assert.assertArrayEquals(expectedTarget1.toArray(), catcher.message.getFirstline().get("west").toArray());
        Assert.assertArrayEquals(expectedTarget2.toArray(), catcher.message.getSecondline().get("west").toArray());
    }

    private class Catcher implements Observer<FlamethrowerChooseEv> {
        FlamethrowerChooseEv message;

        @Override
        public void update(FlamethrowerChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest_OnlyTarget1(){
        Thrower thrower = new Thrower("target1", null);
        Flamethrower model = new Flamethrower();
        FlamethrowerCont controller = new FlamethrowerCont(model);

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(players.get(1), model.getTarget1());
        Assert.assertNull(model.getTarget2());
    }

    @Test
    public void updateTest_OnlyTarget2(){
        Thrower thrower = new Thrower(null, "target2");
        Flamethrower model = new Flamethrower();
        FlamethrowerCont controller = new FlamethrowerCont(model);

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(players.get(2), model.getTarget2());
        Assert.assertNull(model.getTarget1());
    }

    @Test
    public void updateTest_BothTargets(){
        Thrower thrower = new Thrower("target1", "target2");
        Flamethrower model = new Flamethrower();
        FlamethrowerCont controller = new FlamethrowerCont(model);

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(players.get(1), model.getTarget1());
        Assert.assertEquals(players.get(2), model.getTarget2());
    }

    private class Thrower extends Observable<FlamethrowerSetEv> {
        String target1;
        String target2;

        public Thrower(String target1, String target2) {
            this.target1 = target1;
            this.target2 = target2;
        }

        public void throwMessage(){
            notify(new FlamethrowerSetEv(target1, target2));
        }
    }
}
