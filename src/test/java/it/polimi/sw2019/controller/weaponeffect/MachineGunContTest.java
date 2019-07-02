package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.MachineGunChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.MachineGunSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.MachineGun;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MachineGunContTest {
    private Logger logger = Logger.getLogger("test.MachineGunCont");
    private ArrayList<Player> players;
    private Map map;
    private MachineGun model = new MachineGun();
    private MachineGunCont controller = new MachineGunCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target1");
            game.addPlayers("target2");
            game.addPlayers("noTarget");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(1,1));
            players.get(1).setPosition(map.getSpace(1,2));
            players.get(2).setPosition(map.getSpace(1,0));
            players.get(3).setPosition(map.getSpace(3,2));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();

        controller.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedValid.add("target1");
        expectedValid.add("target2");
        expectedNotReachable.add("noTarget");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
    }

    private class Catcher implements Observer<MachineGunChooseEv>{
        MachineGunChooseEv message;

        @Override
        public void update(MachineGunChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest_SingleTarget(){
        Thrower thrower = new Thrower("target1", null);

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(players.get(1), model.getTarget1());
        Assert.assertNull(model.getTarget2());
    }

    @Test
    public void updateTest_DoubleTarget(){
        Thrower thrower = new Thrower("target1", "target2");

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(players.get(1), model.getTarget1());
        Assert.assertEquals(players.get(2), model.getTarget2());
    }

    private class Thrower extends Observable<MachineGunSetEv>{
        private String target1;
        private String target2;

        public Thrower(String target1, String target2) {
            this.target1 = target1;
            this.target2 = target2;
        }

        public void throwMessage(){
            notify(new MachineGunSetEv(target1, target2));
        }
    }
}
