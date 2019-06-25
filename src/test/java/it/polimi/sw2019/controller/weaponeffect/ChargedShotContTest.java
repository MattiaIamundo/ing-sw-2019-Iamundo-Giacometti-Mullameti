package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.exception.WeaponOutOfBoundException;
import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.ChargedShot;
import it.polimi.sw2019.model.weapon_power.PhaseGlide;
import it.polimi.sw2019.model.weapon_power.PlasmaGun;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChargedShotContTest {
    private ArrayList<Player> players;
    private Map map;
    private PlasmaGun model = new PlasmaGun();
    private PhaseGlide phaseGlide = new PhaseGlide();
    private ChargedShot chargedShot = new ChargedShot();
    private DoubleAdditive plasmaGun = new DoubleAdditive("Plasma Gun", model, null, phaseGlide, null, chargedShot, null, null, null, null);

    @Before
    public void setUp(){
        Game game = new Game();
        Logger logger = Logger.getLogger("test.ChargedShotCont");

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            model.setTarget(players.get(1));
            players.get(0).setPosition(map.getSpace(0,0));
            players.get(0).addWeapon(plasmaGun);
            players.get(1).setPosition(map.getSpace(1,0));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapon");
        }
    }

    @Test
    public void acquireTargetTest(){
        ChargedShotCont chargedShotCont = new ChargedShotCont(chargedShot);
        ArrayList<String> excpected = new ArrayList<>();

        excpected.add("attacker");
        chargedShotCont.useEffect(players.get(0), players, map);

        Assert.assertArrayEquals(excpected.toArray(), model.getTarget().getPlance().getDamageTrack().toArray());
    }
}
