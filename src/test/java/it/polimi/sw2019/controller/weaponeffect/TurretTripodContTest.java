package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.TurretTripodChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.TurretTripodSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.exception.WeaponOutOfBoundException;
import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.FocusShot;
import it.polimi.sw2019.model.weapon_power.MachineGun;
import it.polimi.sw2019.model.weapon_power.TurretTripod;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TurretTripodContTest {
    private Logger logger = Logger.getLogger("test.TurretTripodCont");
    private ArrayList<Player> players;
    private Map map;
    private MachineGun machineGun = new MachineGun();
    private FocusShot focusShot = new FocusShot();
    private TurretTripod turretTripod = new TurretTripod();
    private TurretTripodCont controller = new TurretTripodCont(turretTripod);
    private DoubleAdditive weapon = new DoubleAdditive("Machine Gun", machineGun, null, focusShot, null, turretTripod, null, null, null, null);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target1");
            game.addPlayers("target2");
            game.addPlayers("target3");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(2,1));
            players.get(0).addWeapon(weapon);
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }
    }

    @Test
    public void acquireTargetTest_OnlyTwoTargetVisible(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedNotSelectable = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();

        try {
            turretTripod.addObserver(catcher);
            players.get(1).setPosition(map.getSpace(3,0));
            players.get(2).setPosition(map.getSpace(2,0));
            players.get(3).setPosition(map.getSpace(0,0));
            machineGun.setTarget(players.get(1));
            machineGun.setTarget2(players.get(2));
            focusShot.setTarget(players.get(1));
            controller.useEffect(players.get(0), players, map);
            expectedNotSelectable.add("attacker");
            expectedNotSelectable.add("target1");
            expectedNotSelectable.add("target2");
            expectedNotReachable.add("target3");

            Assert.assertTrue(catcher.message.getValid().isEmpty());
            Assert.assertArrayEquals(expectedNotSelectable.toArray(), catcher.message.getNotselectable().toArray());
            Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
            Assert.assertEquals(catcher.message.getAttacker(), catcher.message.getNotselectable().get(0));
            Assert.assertEquals(players.get(2), turretTripod.getPrevioustarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest_ThreeTargetVisible(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedNotSelectable = new ArrayList<>();
        ArrayList<String> expectedvalid = new ArrayList<>();

        try {
            turretTripod.addObserver(catcher);
            players.get(1).setPosition(map.getSpace(3,0));
            players.get(2).setPosition(map.getSpace(2,0));
            players.get(3).setPosition(map.getSpace(3,1));
            machineGun.setTarget(players.get(1));
            machineGun.setTarget2(players.get(2));
            focusShot.setTarget(players.get(1));
            controller.useEffect(players.get(0), players, map);
            expectedNotSelectable.add("attacker");
            expectedNotSelectable.add("target1");
            expectedNotSelectable.add("target2");
            expectedvalid.add("target3");

            Assert.assertArrayEquals(expectedvalid.toArray(), catcher.message.getValid().toArray());
            Assert.assertArrayEquals(expectedNotSelectable.toArray(), catcher.message.getNotselectable().toArray());
            Assert.assertArrayEquals(expectedvalid.toArray(), catcher.message.getValid().toArray());
            Assert.assertEquals(catcher.message.getAttacker(), catcher.message.getNotselectable().get(0));
            Assert.assertEquals(players.get(2), turretTripod.getPrevioustarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest_SingleTargetVisible(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedNotSelectable = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();

        try {
            turretTripod.addObserver(catcher);
            players.get(1).setPosition(map.getSpace(3,0));
            players.get(2).setPosition(map.getSpace(0,0));
            players.get(3).setPosition(map.getSpace(0,0));
            machineGun.setTarget(players.get(1));
            machineGun.setTarget2(null);
            focusShot.setTarget(players.get(2));
            controller.useEffect(players.get(0), players, map);
            expectedNotSelectable.add("attacker");
            expectedNotSelectable.add("target1");
            expectedNotReachable.add("target2");
            expectedNotReachable.add("target3");

            Assert.assertTrue(catcher.message.getValid().isEmpty());
            Assert.assertArrayEquals(expectedNotSelectable.toArray(), catcher.message.getNotselectable().toArray());
            Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
            Assert.assertEquals(catcher.message.getAttacker(), catcher.message.getNotselectable().get(0));
            Assert.assertEquals(players.get(1), turretTripod.getPrevioustarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Catcher implements Observer<TurretTripodChooseEv>{
        TurretTripodChooseEv message;

        @Override
        public void update(TurretTripodChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest_TargetSelected(){
        Thrower thrower = new Thrower("target3");

        try {
            players.get(1).setPosition(map.getSpace(3,0));
            players.get(2).setPosition(map.getSpace(2,0));
            players.get(3).setPosition(map.getSpace(3,1));
            machineGun.setTarget(players.get(1));
            machineGun.setTarget2(players.get(2));
            focusShot.setTarget(players.get(1));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(players.get(3), turretTripod.getTarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void updateTest_NoTargetSelected(){
        Thrower thrower = new Thrower(null);

        try {
            players.get(1).setPosition(map.getSpace(3,0));
            players.get(2).setPosition(map.getSpace(2,0));
            players.get(3).setPosition(map.getSpace(0,0));
            machineGun.setTarget(players.get(1));
            machineGun.setTarget2(players.get(2));
            focusShot.setTarget(players.get(1));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertNull(turretTripod.getTarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<TurretTripodSetEv>{
        private String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new TurretTripodSetEv(target));
        }
    }
}
