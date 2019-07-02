package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.HellionChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.HellionSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Hellion;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HellionContTest {
    private Logger logger = Logger.getLogger("test.HellionCont");
    private ArrayList<Player> players;
    private Map map;
    private Hellion model = new Hellion();
    private HellionCont controller = new HellionCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target1");
            game.addPlayers("target2");
            game.addPlayers("notarget");
            game.addPlayers("attackerSquare");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(2,2));
            players.get(1).setPosition(map.getSpace(2,1));
            players.get(2).setPosition(map.getSpace(2,1));
            players.get(3).setPosition(map.getSpace(0,2));
            players.get(4).setPosition(map.getSpace(2,2));
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
        expectedValid.add("target1");
        expectedValid.add("target2");
        expectedNotReachable.add("notarget");
        expectedNotReachable.add("attackerSquare");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
    }

    private class Catcher implements Observer<HellionChooseEv>{
        HellionChooseEv message;

        @Override
        public void update(HellionChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("target1");
        ArrayList<Player> expectedMarked = new ArrayList<>();

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();
        expectedMarked.add(players.get(1));
        expectedMarked.add(players.get(2));

        Assert.assertEquals(players.get(1), model.getTarget());
        Assert.assertArrayEquals(expectedMarked.toArray(), model.getMarkTargets().toArray());
    }

    private class Thrower extends Observable<HellionSetEv>{
        String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new HellionSetEv(target));
        }
    }
}
