package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.SliceAndDiceChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.SliceAndDiceSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.exception.WeaponOutOfBoundException;
import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Cyberblade;
import it.polimi.sw2019.model.weapon_power.Shadowstep;
import it.polimi.sw2019.model.weapon_power.SliceAndDice;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SliceAndDiceContTest {
    private Logger logger = Logger.getLogger("test.SliceAndDiceCont");
    private ArrayList<Player> players;
    private Map map;
    private Cyberblade cyberblade = new Cyberblade();
    private Shadowstep shadowstep = new Shadowstep();
    private SliceAndDice sliceAndDice = new SliceAndDice();
    private SliceAndDiceCont controller = new SliceAndDiceCont(sliceAndDice);
    private DoubleAdditive weapon = new DoubleAdditive("Cyberblade", cyberblade, null, shadowstep, null, sliceAndDice, null, null, null, null);

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("prevTarget");
            game.addPlayers("target");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(0,1));
            players.get(0).addWeapon(weapon);
            players.get(1).setPosition(map.getSpace(0,1));
            players.get(2).setPosition(map.getSpace(0,1));
            cyberblade.setTarget(players.get(1));
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

        sliceAndDice.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expectedValid.add("target");
        expectedNotSelectable.add("attacker");
        expectedNotSelectable.add("prevTarget");

        Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertArrayEquals(expectedNotSelectable.toArray(), catcher.message.getNotselectable().toArray());
        Assert.assertEquals(players.get(0).getNickname(), catcher.message.getNotselectable().get(0));
    }

    private class Catcher implements Observer<SliceAndDiceChooseEv>{
        SliceAndDiceChooseEv message;

        @Override
        public void update(SliceAndDiceChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("target");

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(players.get(2), sliceAndDice.getTarget());
    }

    private class Thrower extends Observable<SliceAndDiceSetEv>{
        private String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new SliceAndDiceSetEv(target));
        }
    }
}
