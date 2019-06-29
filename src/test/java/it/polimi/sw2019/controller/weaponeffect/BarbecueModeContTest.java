package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.BarbecueSetEv;
import it.polimi.sw2019.model.weapon_power.BarbecueMode;
import it.polimi.sw2019.view.Observable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BarbecueModeContTest {
    private ArrayList<Player> players;
    Map map;

    @Before
    public void setUp(){
        Logger logger = Logger.getLogger("Test.controller.BarbecueModeCont");
        Game game = new Game();

        game.createMap("zero");
        game.addPlayers("attacker");
        game.addPlayers("target1");
        game.addPlayers("target2");
        game.addPlayers("notarget");

        players = (ArrayList<Player>) game.getPlayers();
        map = game.getGameboard().getMap();
        try {
            players.get(0).setPosition(map.getSpace(2, 0)); //This is the attacker
            players.get(1).setPosition(map.getSpace(2, 2)); //This is a valid target on North
            players.get(2).setPosition(map.getSpace(3,0)); //This is a valid target on East
            players.get(3).setPosition(map.getSpace(1,1)); //This isn't a valid target
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE,"Coordinates not valid");
        }
    }

    @Test
    public void testAcquireDirection(){
        BarbecueModeCont barbecueModeCont = new BarbecueModeCont(new BarbecueMode());
        ArrayList<String> expected = new ArrayList<>();

        expected.add("east");
        expected.add("north");
        barbecueModeCont.useEffect(players.get(0), players, map);
        ArrayList<String> obtained = new ArrayList<>(barbecueModeCont.getDirections().keySet());
        Assert.assertTrue(isEquals(expected, obtained));
    }

    private Boolean isEquals(ArrayList<String> expected, ArrayList<String> obtained){
        for (String waited : expected){
            if (!obtained.contains(waited)){
                return false;
            }
        }
        return true;
    }

    @Test
    public void testUpdateWithValidDirection(){
        BarbecueMode model = new BarbecueMode();
        BarbecueModeCont barbecueModeCont = new BarbecueModeCont(model);
        Thrower thrower = new Thrower("north");

        thrower.addObserver(barbecueModeCont);
        barbecueModeCont.useEffect(players.get(0), players, map);
        thrower.throwNotify();
        try {
            Assert.assertEquals(map.getSpace(2, 1), model.getTargetarea1());
            Assert.assertEquals(map.getSpace(2,2), model.getTargetarea2());
        }catch (InvalidSpaceException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateWithSingleSquare(){
        BarbecueMode model = new BarbecueMode();
        BarbecueModeCont barbecueModeCont = new BarbecueModeCont(model);
        Thrower thrower = new Thrower("east");

        thrower.addObserver(barbecueModeCont);
        barbecueModeCont.useEffect(players.get(0), players, map);
        thrower.throwNotify();
        try {
            Assert.assertEquals(map.getSpace(3, 0), model.getTargetarea1());
            Assert.assertNull(model.getTargetarea2());
        }catch (InvalidSpaceException e){
            e.printStackTrace();
        }
    }

    private class Thrower extends Observable<BarbecueSetEv> {
        private String direction;

        public Thrower(String direction) {
            this.direction = direction;
        }

        public void throwNotify(){
            notify(new BarbecueSetEv(direction));
        }
    }
}
