package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponEffectController_events.CyberbladeChooseEv;
import it.polimi.sw2019.events.weaponEffectController_events.CyberbladeSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Cyberblade;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CyberbladeContTest {
    private Logger logger = Logger.getLogger("test.CyberbladeCont");
    private ArrayList<Player> players;
    private Map map;

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target");
            game.addPlayers("notvalid");

            players = (ArrayList<Player>) game.getPlayers();
            map = game.getGameboard().getMap();

            players.get(0).setPosition(map.getSpace(0,2));
            players.get(1).setPosition(map.getSpace(0,2));
            players.get(2).setPosition(map.getSpace(1,2));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        Cyberblade cyberblade = new Cyberblade();
        CyberbladeCont cyberbladeCont = new CyberbladeCont(cyberblade);
        Catcher catcher = new Catcher();
        ArrayList<String> excpectedValid = new ArrayList<>();
        ArrayList<String> excpectedNotReachable = new ArrayList<>();

        cyberblade.addObserver(catcher);
        cyberbladeCont.useEffect(players.get(0), players, map);
        excpectedValid.add("target");
        excpectedNotReachable.add("notvalid");

        Assert.assertArrayEquals(excpectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertArrayEquals(excpectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
    }

    private class Catcher implements Observer<CyberbladeChooseEv> {
        private CyberbladeChooseEv message;

        @Override
        public void update(CyberbladeChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Cyberblade cyberblade = new Cyberblade();
        CyberbladeCont cyberbladeCont = new CyberbladeCont(cyberblade);
        Thrower thrower = new Thrower("target");

        thrower.addObserver(cyberbladeCont);
        cyberbladeCont.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals("target", cyberblade.getTarget().getNickname());
    }

    private class Thrower extends Observable<CyberbladeSetEv>{
        private String name;

        public Thrower(String name) {
            this.name = name;
        }

        public void throwMessage(){
            notify(new CyberbladeSetEv(name));
        }
    }
}
