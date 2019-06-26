package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.exception.WeaponOutOfBoundException;
import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.FragmentingWarhead;
import it.polimi.sw2019.model.weapon_power.RocketJump;
import it.polimi.sw2019.model.weapon_power.RocketLauncher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FragmentingWarheadContTest {
    private Logger logger = Logger.getLogger("test.FragmentingWarheadCont");
    private ArrayList<Player> players;
    private Map map;
    private RocketLauncher basiceffect = new RocketLauncher();
    private RocketJump rocketJump = new RocketJump();
    private FragmentingWarhead fragmentingWarhead = new FragmentingWarhead();
    private DoubleAdditive rocketlauncher = new DoubleAdditive("Rocket Launcher", basiceffect, null, rocketJump, null, fragmentingWarhead, null, null, null, null);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("prevtarget");
            game.addPlayers("target1");
            game.addPlayers("target2");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(2,1));
            players.get(0).addWeapon(rocketlauncher);
            players.get(1).setPosition(map.getSpace(3,0));
            players.get(2).setPosition(map.getSpace(3,0));
            players.get(3).setPosition(map.getSpace(3,0));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }
    }

    @Test
    public void initializeTest_PrevTargetMoved(){
        FragmentingWarheadCont controller = new FragmentingWarheadCont(fragmentingWarhead);
        ArrayList<Player> expectedTargets = new ArrayList<>();

        try {
            basiceffect.setTarget(players.get(1), map.getSpace(3,1));
            basiceffect.usePower(players.get(0));
            controller.useEffect(players.get(0), players, map);
            expectedTargets.add(players.get(2));
            expectedTargets.add(players.get(3));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }

        Assert.assertEquals(players.get(1), fragmentingWarhead.getTarget());
        Assert.assertArrayEquals(expectedTargets.toArray(), fragmentingWarhead.getPlayers().toArray());
    }

    @Test
    public void initializeTest_PrevTargetNotMoved(){
        FragmentingWarheadCont controller = new FragmentingWarheadCont(fragmentingWarhead);
        ArrayList<Player> expectedTargets = new ArrayList<>();

        try {
            basiceffect.setTarget(players.get(1), map.getSpace(3,0));
            basiceffect.usePower(players.get(0));
            controller.useEffect(players.get(0), players, map);
            expectedTargets.add(players.get(1));
            expectedTargets.add(players.get(2));
            expectedTargets.add(players.get(3));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }

        Assert.assertNull(fragmentingWarhead.getTarget());
        Assert.assertArrayEquals(expectedTargets.toArray(), fragmentingWarhead.getPlayers().toArray());
    }
}
