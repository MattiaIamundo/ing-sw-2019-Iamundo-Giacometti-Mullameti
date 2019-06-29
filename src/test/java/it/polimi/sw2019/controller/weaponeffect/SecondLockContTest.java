package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.SecondLockChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.SecondLockSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.exception.WeaponOutOfBoundException;
import it.polimi.sw2019.model.Additive;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.LockRifle;
import it.polimi.sw2019.model.weapon_power.SecondLock;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecondLockContTest {
    private Logger logger = Logger.getLogger("test.SecondLockCont");
    private ArrayList<Player> players;
    private Map map;
    private LockRifle lockRifle = new LockRifle();
    private SecondLock secondLock = new SecondLock();
    private SecondLockCont controller = new SecondLockCont(secondLock);
    private Additive weapon = new Additive("Lock Rifle", lockRifle, null, secondLock, null, null, null);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("prevTarget");
            game.addPlayers("target");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(3,0));
            players.get(0).addWeapon(weapon);
            players.get(1).setPosition(map.getSpace(3,1));
            players.get(2).setPosition(map.getSpace(2,1));
            lockRifle.setTarget(players.get(1));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();

        secondLock.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedValid.add("target");
        expectedNotReachable.add("attacker");
        expectedNotReachable.add("prevTarget");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotselectable().toArray());
        Assert.assertTrue(catcher.message.getNotreachable().isEmpty());
    }

    private class Catcher implements Observer<SecondLockChooseEv>{
        SecondLockChooseEv message;

        @Override
        public void update(SecondLockChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("target");

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(players.get(2), secondLock.getTarget());
    }

    private class Thrower extends Observable<SecondLockSetEv>{
        private String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new SecondLockSetEv(target));
        }
    }
}
