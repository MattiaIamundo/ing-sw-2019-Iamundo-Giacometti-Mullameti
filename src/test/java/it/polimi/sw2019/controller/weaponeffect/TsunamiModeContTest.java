package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.TsunamiMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TsunamiModeContTest {
    private Logger logger = Logger.getLogger("test.TsunamiModeCont");
    private ArrayList<Player> players;
    private Map map;
    private TsunamiMode model = new TsunamiMode();
    private TsunamiModeCont controller = new TsunamiModeCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        game.createMap("zero");
        game.addPlayers("attacker");
        game.addPlayers("target1");
        game.addPlayers("target2");
        map = game.getGameboard().getMap();
        players = (ArrayList<Player>) game.getPlayers();
    }

    @Test
    public void acquireTargetTest_AttackerPosition_3_2(){
        ArrayList<Player> expectedTarget = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(3,2));
            players.get(1).setPosition(map.getSpace(2,2));
            players.get(2).setPosition(map.getSpace(3,1));
            controller.useEffect(players.get(0), players, map);
            expectedTarget.add(players.get(1));
            expectedTarget.add(players.get(2));

            Assert.assertArrayEquals(expectedTarget.toArray(), model.getTargets().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest_AttackerPosition_0_0(){
        ArrayList<Player> expectedTarget = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(0,1));
            players.get(2).setPosition(map.getSpace(1,0));
            controller.useEffect(players.get(0), players, map);
            expectedTarget.add(players.get(1));
            expectedTarget.add(players.get(2));

            Assert.assertArrayEquals(expectedTarget.toArray(), model.getTargets().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }
}
