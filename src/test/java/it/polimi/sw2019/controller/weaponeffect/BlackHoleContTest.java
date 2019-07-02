package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.BlackHoleChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.BlackHoleSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.exception.WeaponOutOfBoundException;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.weapon_power.BlackHole;
import it.polimi.sw2019.model.weapon_power.Vortex;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlackHoleContTest {
    private ArrayList<Player> players;
    private Map map;
    private Logger logger = Logger.getLogger("test.controller.BlackHoleCont");
    private Vortex vortex = new Vortex();
    private BlackHole blackHole = new BlackHole();
    private BlackHoleCont controller = new BlackHoleCont(blackHole);
    private Alternative weapon = new Alternative("Vortex Cannon", vortex, null, blackHole, null, null, null);

    @Before
    public void setUp(){
        Game game = new Game();
        game.createMap("zero");
        game.addPlayers("attacker");
        game.addPlayers("target");
        game.addPlayers("target2");
        game.addPlayers("notarget");
        game.addPlayers("prevtarget");
        players = (ArrayList<Player>) game.getPlayers();
        map = game.getGameboard().getMap();

        try {
            vortex.setTarget(players.get(4));
            players.get(0).addWeapon(weapon);
            ((Vortex) weapon.getPower()).setVortex(map.getSpace(1,1));
            players.get(0).setPosition(map.getSpace(1,0));
            players.get(1).setPosition(map.getSpace(1,2));
            players.get(2).setPosition(map.getSpace(1,0));
            players.get(3).setPosition(map.getSpace(0,1));
            players.get(4).setPosition(map.getSpace(1,1));
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of the map bound");
        }
    }

    @Test
    public void acquireTargetTest(){
        Capturer capturer = new Capturer();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNotSelectable = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();

        controller.addObserver(capturer);
        controller.useEffect(players.get(0), players, map);
        expectedValid.add("target");
        expectedValid.add("target2");
        expectedNotSelectable.add("attacker");
        expectedNotSelectable.add("prevtarget");
        expectedNotReachable.add("notarget");

        Assert.assertArrayEquals(expectedValid.toArray(), capturer.message.getValid().toArray());
        Assert.assertArrayEquals(expectedNotSelectable.toArray(), capturer.message.getNotselectable().toArray());
        Assert.assertArrayEquals(expectedNotReachable.toArray(), capturer.message.getNotreachable().toArray());
    }

    private class Capturer implements Observer<BlackHoleChooseEv> {
        protected BlackHoleChooseEv message;

        @Override
        public void update(BlackHoleChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateWithSingleTargetTest(){
        BlackHole model = new BlackHole();
        BlackHoleCont blackHoleCont = new BlackHoleCont(model);
        Thrower thrower = new Thrower();

        thrower.addObserver(blackHoleCont);
        blackHoleCont.useEffect(players.get(0), players, map);
        thrower.throwMessage("target");

        Assert.assertEquals("target", model.getTarget1().getNickname());
        Assert.assertNull(model.getTarget2());
    }

    @Test
    public void updateWithTwoTarget(){
        BlackHole model = new BlackHole();
        BlackHoleCont blackHoleCont = new BlackHoleCont(model);
        Thrower thrower = new Thrower();

        thrower.addObserver(blackHoleCont);
        blackHoleCont.useEffect(players.get(0), players, map);
        thrower.throwMessage("target", "target2");

        Assert.assertEquals("target", model.getTarget1().getNickname());
        Assert.assertEquals("target2", model.getTarget2().getNickname());
    }

    private class Thrower extends Observable<BlackHoleSetEv>{
        protected BlackHoleSetEv message;

        public void throwMessage(String target1, String target2){
            notify(new BlackHoleSetEv(target1,target2));
        }

        public void throwMessage(String target){
            notify(new BlackHoleSetEv(target));
        }
    }
}
