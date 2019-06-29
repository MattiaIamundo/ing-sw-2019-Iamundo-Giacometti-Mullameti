package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.FocusShotChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.FocusShotSetEv;
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

public class FocusShotContTest {
    private Logger logger = Logger.getLogger("test.FocusShotCont");
    private ArrayList<Player> players;
    private Map map;
    private MachineGun basiceffect = new MachineGun();
    private FocusShot focusShot = new FocusShot();
    private TurretTripod turretTripod = new TurretTripod();
    private DoubleAdditive machinegun = new DoubleAdditive("Machine Gun", basiceffect, null, focusShot, null, turretTripod, null, null, null, null);

    @Before
    public void asetUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("prevtarget1");
            game.addPlayers("prevtarget2");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(1,1));
            players.get(0).addWeapon(machinegun);
            players.get(1).setPosition(map.getSpace(2,1));
            players.get(2).setPosition(map.getSpace(2,2));
            basiceffect.setTarget(players.get(1));
            basiceffect.setTarget2(players.get(2));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }
    }

    @Test
    public void acquireTargetTest(){
        FocusShotCont controller = new FocusShotCont(focusShot);
        Catcher catcher = new Catcher();
        ArrayList<String> expected = new ArrayList<>();

        focusShot.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expected.add("prevtarget1");
        expected.add("prevtarget2");

        Assert.assertArrayEquals(expected.toArray(), catcher.message.getTargets().toArray());
    }

    private class Catcher implements Observer<FocusShotChooseEv>{
        FocusShotChooseEv message;

        @Override
        public void update(FocusShotChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("prevtarget1");
        FocusShotCont controller = new FocusShotCont(focusShot);

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(players.get(1), focusShot.getTarget());
    }

    private class Thrower extends Observable<FocusShotSetEv>{
        String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new FocusShotSetEv(target));
        }
    }
}
