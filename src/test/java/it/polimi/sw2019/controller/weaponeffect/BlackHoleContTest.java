package it.polimi.sw2019.controller;

import it.polimi.sw2019.controller.weaponeffect.BlackHoleCont;
import it.polimi.sw2019.events.weaponEffectController_events.BlackHoleChooseEv;
import it.polimi.sw2019.events.weaponEffectController_events.BlackHoleSetEv;
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

public class BlackHoleContTest2 {
    private ArrayList<Player> players;
    private Map map;
    private Logger logger = Logger.getLogger("test.controller.BlackHoleCont");
    private Vortex vortex = new Vortex();

    @Before
    public void setUp(){
        Game game = new Game();
        game.createMap("zero");
        String rechargecost[] = {"red", "blue"};
        String extracost[] = {"red"};
        game.addPlayers("attacker");
        game.addPlayers("target");
        game.addPlayers("notarget");
        players = (ArrayList<Player>) game.getPlayers();
        map = game.getGameboard().getMap();

        try {
            players.get(0).addWeapon(new Alternative("Vortex Cannon", vortex, null, new BlackHole(), extracost, null, rechargecost));
            ((Vortex) players.get(0).getWeapons().get(0).getPower()).setVortex(map.getSpace(1,1));
            players.get(0).setPosition(map.getSpace(1,0));
            players.get(1).setPosition(map.getSpace(1,2));
            players.get(0).setPosition(map.getSpace(0,1));
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of the map bound");
        }
    }

    @Test
    public void acquireTargetTest(){
        BlackHole model = new BlackHole();
        Capturer capturer = new Capturer();
        BlackHoleCont blackHoleCont = new BlackHoleCont(model);
        ArrayList<String> expected = new ArrayList<>();

        try {
            vortex.setTarget(new Player("prevtarget", 0, map.getSpace(1,1), new PlayerPlance()));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "space not exist");
        }
        model.addObserver(capturer);
        blackHoleCont.useEffect(players.get(0), players, map);
        expected.add("target");

        Assert.assertArrayEquals(expected.toArray(), capturer.message.getTargets().toArray());
        Assert.assertEquals("attacker", capturer.message.getAttacker());
        Assert.assertEquals("prevtarget", capturer.message.getNotselectable());
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
        thrower.throwMessage("target");
        acquireTargetTest();

        Assert.assertEquals("target", model.getTarget1().getNickname());
        Assert.assertNull(model.getTarget2());
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
