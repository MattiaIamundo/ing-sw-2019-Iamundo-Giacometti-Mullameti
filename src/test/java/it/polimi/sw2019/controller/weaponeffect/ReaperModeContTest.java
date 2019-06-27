package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.ReaperMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReaperModeContTest {
    private Logger logger = Logger.getLogger("test.ReaperModeCont");
    private ArrayList<Player> players;
    private Map map;
    private ReaperMode model = new ReaperMode();
    private ReaperModeCont controller = new ReaperModeCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target1");
            game.addPlayers("target2");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            for (Player player : players){
                player.setPosition(map.getSpace(0,1));
            }
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        ArrayList<Player> expectedTargets = new ArrayList<>();

        controller.useEffect(players.get(0), players, map);
        expectedTargets.add(players.get(1));
        expectedTargets.add(players.get(2));

        Assert.assertArrayEquals(expectedTargets.toArray(), model.getTargets().toArray());
    }
}
