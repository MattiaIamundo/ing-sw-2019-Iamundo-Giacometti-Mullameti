package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponEffectController_events.RailGunChooseEv;
import it.polimi.sw2019.events.weaponEffectController_events.RailGunSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.RailGun;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RailGunContTest {
    private Logger logger = Logger.getLogger("test.RailGunCont");
    private ArrayList<Player> players;
    private Map map;
    private RailGun model = new RailGun();
    private RailGunCont controller = new RailGunCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(3,1));
            players.get(1).setPosition(map.getSpace(0,1));
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
        expectedValid.add("target");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getTargets().get("west").toArray());
    }

    private class Catcher implements Observer<RailGunChooseEv>{
        RailGunChooseEv message;

        @Override
        public void update(RailGunChooseEv message) {
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

    private class Thrower extends Observable<RailGunSetEv>{
        private String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new RailGunSetEv(target));
        }
    }
}
