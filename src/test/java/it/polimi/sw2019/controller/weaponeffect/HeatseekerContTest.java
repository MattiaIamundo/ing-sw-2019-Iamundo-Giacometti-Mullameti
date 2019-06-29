package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.HeatseekerChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.HeatseekerSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Heatseeker;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HeatseekerContTest {
    private Logger logger = Logger.getLogger("test.HeatseekerCont");
    private ArrayList<Player> players;
    private Map map;
    private Heatseeker model = new Heatseeker();
    private HeatseekerCont controller = new HeatseekerCont(model);

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

            players.get(0).setPosition(map.getSpace(2,1));
            players.get(1).setPosition(map.getSpace(0,1));
            players.get(2).setPosition(map.getSpace(3,1));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNOtReachable = new ArrayList<>();

        model.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedValid.add("target");
        expectedNOtReachable.add("notarget");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertArrayEquals(expectedNOtReachable.toArray(), catcher.message.getNotreachable().toArray());
    }

    private class Catcher implements Observer<HeatseekerChooseEv>{
        HeatseekerChooseEv message;

        @Override
        public void update(HeatseekerChooseEv message) {
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
        Assert.assertFalse(players.get(1).getPlance().getDamageTrack().isEmpty());
    }

    private class Thrower extends Observable<HeatseekerSetEv>{
        String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new HeatseekerSetEv(target));
        }
    }
}
