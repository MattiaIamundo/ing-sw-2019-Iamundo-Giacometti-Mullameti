package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponEffectController_events.PhaseGlideChooseEv;
import it.polimi.sw2019.events.weaponEffectController_events.PhaseGlideSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.PhaseGlide;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhaseGlideContTest {
    private Logger logger = Logger.getLogger("test.PhaseGlideCont");
    private ArrayList<Player> players;
    private Map map;
    private PhaseGlide model = new PhaseGlide();
    private PhaseGlideCont controller = new PhaseGlideCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(2,1));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireDirecyionTest(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedDirections = new ArrayList<>();

        model.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedDirections.add("north-east");
        expectedDirections.add("east");
        expectedDirections.add("south");
        expectedDirections.add("north");
        expectedDirections.add("north-west");
        expectedDirections.add("south-east");
        expectedDirections.add("south-west");

        Assert.assertArrayEquals(expectedDirections.toArray(), catcher.message.getPositions().toArray());
    }


    private class Catcher implements Observer<PhaseGlideChooseEv>{
        private PhaseGlideChooseEv message;

        @Override
        public void update(PhaseGlideChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("north");

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        try {
            Assert.assertEquals(map.getSpace(2, 2), model.getMoveto());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<PhaseGlideSetEv>{
        private String direction;

        public Thrower(String direction) {
            this.direction = direction;
        }

        public void throwMessage(){
            notify(new PhaseGlideSetEv(direction));
        }
    }
}
