package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.FurnaceChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.FurnaceSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Furnace;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FurnaceContTest {
    private Logger logger = Logger.getLogger("test.FurnaceCont");
    private ArrayList<Player> players;
    private Map map;

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target1");
            game.addPlayers("notarget");
            map = game.getGameboard().getMap();
            players = (ArrayList<Player>) game.getPlayers();

            players.get(0).setPosition(map.getSpace(2,2));
            players.get(1).setPosition(map.getSpace(3,0));
            players.get(2).setPosition(map.getSpace(1,2));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest(){
        Catcher catcher = new Catcher();
        Furnace model = new Furnace();
        FurnaceCont controller = new FurnaceCont(model);
        ArrayList<String> expected = new ArrayList<>();

        model.addObserver(catcher);
        controller.useEffect(players.get(0), players, map);
        expected.add("yellow");

        Assert.assertArrayEquals(expected.toArray(), catcher.message.getRooms().toArray());
    }

    @Test
    public void acquireTargetTest_NoRoomAvailable(){
        Catcher catcher = new Catcher();
        Furnace model = new Furnace();
        FurnaceCont controller = new FurnaceCont(model);
        ArrayList<String> expected = new ArrayList<>();

        try {
            players.get(0).setPosition(map.getSpace(2,0));
            players.get(1).setPosition(map.getSpace(2,2));
            model.addObserver(catcher);
            controller.useEffect(players.get(0), players, map);

            Assert.assertTrue(catcher.message.getRooms().isEmpty());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Catcher implements Observer<FurnaceChooseEv>{
        FurnaceChooseEv message;

        @Override
        public void update(FurnaceChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("yellow");
        Furnace model = new Furnace();
        FurnaceCont controller = new FurnaceCont(model);
        ArrayList<Player> expectedTarget = new ArrayList<>();

        thrower.addObserver(controller);
        controller.useEffect(players.get(0), players, map);
        thrower.throwMessage();
        expectedTarget.add(players.get(1));

        Assert.assertArrayEquals(expectedTarget.toArray(), model.getTargets().toArray());
    }

    @Test
    public void updateTest_NoRoomSelected(){
        Thrower thrower = new Thrower(null);
        Furnace model = new Furnace();
        FurnaceCont controller = new FurnaceCont(model);

        try {
            players.get(0).setPosition(map.getSpace(2,0));
            players.get(1).setPosition(map.getSpace(2,2));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }

        Assert.assertTrue(model.getTargets().isEmpty());
    }

    private class Thrower extends Observable<FurnaceSetEv>{
        String room;

        public Thrower(String room) {
            this.room = room;
        }

        public void throwMessage(){
            notify(new FurnaceSetEv(room));
        }
    }
}
