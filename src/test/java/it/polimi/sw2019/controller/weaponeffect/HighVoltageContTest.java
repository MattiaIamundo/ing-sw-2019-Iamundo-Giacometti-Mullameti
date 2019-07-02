package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.HighVoltageChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.HighVoltageSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.exception.WeaponOutOfBoundException;
import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.ChainReaction;
import it.polimi.sw2019.model.weapon_power.HighVoltage;
import it.polimi.sw2019.model.weapon_power.Thor;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HighVoltageContTest {
    private Logger logger = Logger.getLogger("test.HighVoltageCont");
    private ArrayList<Player> players;
    private Map map;
    private Thor thor = new Thor();
    private ChainReaction chainReaction = new ChainReaction();
    private HighVoltage highVoltage = new HighVoltage();
    private HighVoltageCont controller = new HighVoltageCont(highVoltage);
    private DoubleAdditive weapon = new DoubleAdditive("T.H.O.R.", thor, null, chainReaction, null, highVoltage, null, null, null, null);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("firstTarget");
            game.addPlayers("secondTarget");
            game.addPlayers("target");
            game.addPlayers("noTarget");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(0,1));
            players.get(0).addWeapon(weapon);
            players.get(1).setPosition(map.getSpace(1,0));
            players.get(2).setPosition(map.getSpace(2,1));
            players.get(3).setPosition(map.getSpace(1,2));
            players.get(4).setPosition(map.getSpace(1,1));

            thor.setTarget(players.get(1));
            chainReaction.setTarget(players.get(2));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, "you already have 3 weapons");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedNotSelectable = new ArrayList<>();
        ArrayList<String> expectedNotReachable = new ArrayList<>();

        controller.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedValid.add("target");
        expectedNotSelectable.add("attacker");
        expectedNotSelectable.add("firstTarget");
        expectedNotSelectable.add("secondTarget");
        expectedNotReachable.add("noTarget");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertArrayEquals(expectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
        Assert.assertArrayEquals(expectedNotSelectable.toArray(), catcher.message.getNotselectable().toArray());
    }

    private class Catcher implements Observer<HighVoltageChooseEv>{
        private HighVoltageChooseEv message;

        @Override
        public void update(HighVoltageChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("target");

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(players.get(3), highVoltage.getTarget());
    }

    private class Thrower extends Observable<HighVoltageSetEv>{
        private String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new HighVoltageSetEv(target));
        }
    }
}
