package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.ExtraGrenadeChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.ExtraGrenadeSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.exception.WeaponOutOfBoundException;
import it.polimi.sw2019.model.Additive;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.ExtraGrenade;
import it.polimi.sw2019.model.weapon_power.GrenadeLauncher;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExtraGrenadeContTest {
    private Logger logger = Logger.getLogger("test.ExtraGrenadeCont");
    private ArrayList<Player> players;
    private Map map;
    private GrenadeLauncher basicEffect = new GrenadeLauncher();
    private ExtraGrenade optEffect = new ExtraGrenade();
    private Additive grenadeLauncher = new Additive("Grenade Launcher", basicEffect, null, optEffect, null, null, null);


    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("prevtarget");
            game.addPlayers("target");
            players = (ArrayList<Player>) game.getPlayers();
            map = game.getGameboard().getMap();

            players.get(0).setPosition(map.getSpace(1,0));
            players.get(0).addWeapon(grenadeLauncher);
            players.get(1).setPosition(map.getSpace(2,0));
            players.get(2).setPosition(map.getSpace(2,1));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }
    }

    @Test
    public void acquireTarget_PrevTargetAlreadyMoved(){
        Catcher catcher = new Catcher();
        ExtraGrenadeCont extraGrenadeCont = new ExtraGrenadeCont(optEffect);
        ArrayList<String> excpected = new ArrayList<>();

        try {
            basicEffect.setTarget(players.get(1));
            basicEffect.setMoveto(map.getSpace(2, 0));
            basicEffect.usePower(players.get(0));
            optEffect.addObserver(catcher);
            extraGrenadeCont.useEffect(players.get(0), players, map);
            excpected.add("0-0");
            excpected.add("1-0");
            excpected.add("1-1");
            excpected.add("2-0");
            excpected.add("2-1");
            excpected.add("3-0");
            excpected.add("3-1");

            Assert.assertArrayEquals(excpected.toArray(), catcher.message.getValidsquare().toArray());
            Assert.assertTrue(basicEffect.isIsmoved());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTarget_PrevTargetNotMoved(){
        Catcher catcher = new Catcher();
        ExtraGrenadeCont extraGrenadeCont = new ExtraGrenadeCont(optEffect);
        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> expectedMoveTo = new ArrayList<>();

        basicEffect.setTarget(players.get(1));
        basicEffect.setMoveto(null);
        basicEffect.usePower(players.get(0));
        optEffect.addObserver(catcher);
        extraGrenadeCont.useEffect(players.get(0), players, map);
        expected.add("0-0");
        expected.add("1-0");
        expected.add("1-1");
        expected.add("2-0");
        expected.add("2-1");
        expected.add("3-0");
        expected.add("3-1");
        expectedMoveTo.add("east");
        expectedMoveTo.add("north");
        expectedMoveTo.add("west");

        Assert.assertArrayEquals(expected.toArray(), catcher.message.getValidsquare().toArray());
        Assert.assertArrayEquals(expectedMoveTo.toArray(), catcher.message.getMoveto().toArray());
        Assert.assertFalse(basicEffect.isIsmoved());
    }

    private class Catcher implements Observer<ExtraGrenadeChooseEv>{
        private ExtraGrenadeChooseEv message;

        @Override
        public void update(ExtraGrenadeChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void update_PrevTargetMoved(){
        ExtraGrenadeCont extraGrenadeCont = new ExtraGrenadeCont(optEffect);
        Thrower thrower = new Thrower(null, "2-1");
        ArrayList<Player> expected = new ArrayList<>();

        try {
            basicEffect.setTarget(players.get(1));
            basicEffect.setMoveto(map.getSpace(2, 0));
            thrower.addObserver(extraGrenadeCont);
            extraGrenadeCont.useEffect(players.get(0), players, map);
            thrower.throwMessageMoved();
            expected.add(players.get(2));

            Assert.assertArrayEquals(expected.toArray(), optEffect.getPlayers().toArray());
            Assert.assertNull(thrower.moveto);
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void update_PrevTargetNotMoved(){
        ExtraGrenadeCont extraGrenadeCont = new ExtraGrenadeCont(optEffect);
        Thrower thrower = new Thrower("north", "2-1");
        ArrayList<Player> expected = new ArrayList<>();

        basicEffect.setTarget(players.get(1));
        basicEffect.setMoveto(null);
        thrower.addObserver(extraGrenadeCont);
        extraGrenadeCont.useEffect(players.get(0), players, map);
        thrower.throwMessageToMove();
        expected.add(players.get(2));

        Assert.assertArrayEquals(expected.toArray(), optEffect.getPlayers().toArray());
    }

    private class Thrower extends Observable<ExtraGrenadeSetEv>{
        String moveto;
        String square;

        public Thrower(String moveto, String square) {
            this.moveto = moveto;
            this.square = square;
        }

        public void throwMessageMoved(){
            notify(new ExtraGrenadeSetEv(square));
        }

        public void throwMessageToMove(){
            notify(new ExtraGrenadeSetEv(square, moveto));
        }
    }
}
