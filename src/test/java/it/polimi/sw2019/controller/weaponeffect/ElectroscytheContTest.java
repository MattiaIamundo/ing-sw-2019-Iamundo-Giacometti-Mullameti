package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Electroscythe;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ElectroscytheContTest {
    private Logger logger = Logger.getLogger("test.Electroscythecont");
    private ArrayList<Player> players;
    private Map map;

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target");
            game.addPlayers("notarget");
            players = (ArrayList<Player>) game.getPlayers();
            map = game.getGameboard().getMap();

            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(0,0));
            players.get(2).setPosition(map.getSpace(2,2));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetsTest(){
        Electroscythe electroscythe = new Electroscythe();
        ElectroscytheCont electroscytheCont = new ElectroscytheCont(electroscythe);
        ArrayList<Player> excpectedTarget = new ArrayList<>();

        electroscytheCont.useEffect(players.get(0), players, map);
        excpectedTarget.add(players.get(1));

        Assert.assertArrayEquals(excpectedTarget.toArray(), electroscythe.getPlayers().toArray());
    }
}
