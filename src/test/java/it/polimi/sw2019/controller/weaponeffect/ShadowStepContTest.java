package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.ShadowstepChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.ShadowstepSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Shadowstep;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShadowStepContTest {
    private Logger logger = Logger.getLogger("test.ShadowStepCont");
    private ArrayList<Player> players;
    private Map map;
    private Shadowstep model = new Shadowstep();
    private ShadowstepCont controller = new ShadowstepCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        game.createMap("zero");
        game.addPlayers("attacker");
        map = game.getGameboard().getMap();
        players = (ArrayList<Player>) game.getPlayers();
    }

    @Test
    public void acquireDirectionTest_Position_2_1(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedDirection = new ArrayList<>();

        try {
            controller.addObserver(catcher);
            players.get(0).setPosition(map.getSpace(2,1));
            controller.useEffect(players.get(0), players, map);
            expectedDirection.add("east");
            expectedDirection.add("south");
            expectedDirection.add("north");

            Assert.assertArrayEquals(expectedDirection.toArray(), catcher.message.getPositions().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireDirectionTest_Position_3_0(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedDirection = new ArrayList<>();

        try {
            controller.addObserver(catcher);
            players.get(0).setPosition(map.getSpace(3,0));
            controller.useEffect(players.get(0), players, map);
            expectedDirection.add("north");
            expectedDirection.add("west");

            Assert.assertArrayEquals(expectedDirection.toArray(), catcher.message.getPositions().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Catcher implements Observer<ShadowstepChooseEv>{
        ShadowstepChooseEv message;

        @Override
        public void update(ShadowstepChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("north");

        try {
            players.get(0).setPosition(map.getSpace(2,1));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(map.getSpace(2,2), model.getMoveto());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<ShadowstepSetEv>{
        private String moveto;

        public Thrower(String moveto) {
            this.moveto = moveto;
        }

        public void throwMessage(){
            notify(new ShadowstepSetEv(moveto));
        }
    }
}
