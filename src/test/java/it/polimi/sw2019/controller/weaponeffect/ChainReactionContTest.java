package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponEffectController_events.ChainReactChooseEv;
import it.polimi.sw2019.events.weaponEffectController_events.ChainReactSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.exception.WeaponOutOfBoundException;
import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.ChainReaction;
import it.polimi.sw2019.model.weapon_power.HighVoltage;
import it.polimi.sw2019.model.weapon_power.Thor;
import it.polimi.sw2019.model.weapon_power.Vortex;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChainReactionContTest {
    private Logger logger = Logger.getLogger("test.ChainReaction");
    private ArrayList<Player> players;
    private Map map;
    private Thor model = new Thor();
    private ChainReaction chainReaction = new ChainReaction();
    private HighVoltage highVoltage = new HighVoltage();

    @Before
    public void setUp(){
        Game game = new Game();
        DoubleAdditive thor = new DoubleAdditive("T.H.O.R.", model, null, chainReaction, "blue", highVoltage, "blue", null, null, null);

        game.createMap("zero");
        game.addPlayers("attacker");
        game.addPlayers("prevtarget");
        game.addPlayers("target");
        game.addPlayers("notreachable");
        map = game.getGameboard().getMap();
        players = (ArrayList<Player>) game.getPlayers();

        try {
            players.get(0).setPosition(map.getSpace(0,1));
            players.get(0).addWeapon(thor);
            players.get(1).setPosition(map.getSpace(1,0));
            players.get(2).setPosition(map.getSpace(2,1));
            players.get(3).setPosition(map.getSpace(2,2));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of the map boundaries");
        }catch (WeaponOutOfBoundException e){
            logger.log(Level.SEVERE, e.getMessage()+" already have 3 weapons");
        }
        model.setTarget(players.get(1));
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        ChainReactionCont chainReactionCont = new ChainReactionCont(chainReaction);
        ArrayList<String> excpectedValid = new ArrayList<>();
        ArrayList<String> excpectedNotSelectable = new ArrayList<>();
        ArrayList<String> excpectedNotReachable = new ArrayList<>();

        chainReaction.addObserver(catcher);
        chainReactionCont.useEffect(players.get(0), players, map);
        excpectedValid.add("target");
        excpectedNotSelectable.add("attacker");
        excpectedNotSelectable.add("prevtarget");
        excpectedNotReachable.add("notreachable");

        Assert.assertArrayEquals(excpectedValid.toArray(), catcher.message.getValid().toArray());
        Assert.assertArrayEquals(excpectedNotSelectable.toArray(), catcher.message.getNotselectable().toArray());
        Assert.assertArrayEquals(excpectedNotReachable.toArray(), catcher.message.getNotreachable().toArray());
    }

    private class Catcher implements Observer<ChainReactChooseEv>{
        protected ChainReactChooseEv message;

        @Override
        public void update(ChainReactChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("target");
        ChainReactionCont chainReactionCont = new ChainReactionCont(chainReaction);

        thrower.addObserver(chainReactionCont);
        chainReactionCont.useEffect(players.get(0), players, map);
        thrower.throwMessage();

        Assert.assertEquals(chainReaction.getTarget(), players.get(2));
    }

    private class Thrower extends Observable<ChainReactSetEv> {
        String target;

        public Thrower(String target) {
            this.target = target;
        }

        public void throwMessage(){
            notify(new ChainReactSetEv(target));
        }
    }
}
