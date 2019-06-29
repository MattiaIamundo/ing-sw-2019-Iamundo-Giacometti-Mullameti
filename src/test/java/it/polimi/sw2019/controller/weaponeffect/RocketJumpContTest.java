package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.RocketJumpChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.RocketJumpSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.RocketJump;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RocketJumpContTest {
    private Logger logger = Logger.getLogger("test.RocketJumpCont");
    private ArrayList<Player> players;
    private Map map;
    private RocketJump model = new RocketJump();
    private RocketJumpCont controller = new RocketJumpCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        game.createMap("zero");
        game.addPlayers("attacker");
        map = game.getGameboard().getMap();
        players = (ArrayList<Player>) game.getPlayers();
    }

    @Test
    public void acquireDirections_Position_0_2(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedDirections = new ArrayList<>();

        try {
            model.addObserver(catcher);
            players.get(0).setPosition(map.getSpace(0,2));
            controller.useEffect(players.get(0), players, map);
            expectedDirections.add("east");
            expectedDirections.add("south-south");
            expectedDirections.add("south");
            expectedDirections.add("east-east");
            expectedDirections.add("east-south");

            Assert.assertArrayEquals(expectedDirections.toArray(), catcher.message.getPositions().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireDirections_Position_0_0(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedDirections = new ArrayList<>();

        try {
            model.addObserver(catcher);
            players.get(0).setPosition(map.getSpace(0,0));
            controller.useEffect(players.get(0), players, map);
            expectedDirections.add("east");
            expectedDirections.add("north");
            expectedDirections.add("east-east");
            expectedDirections.add("east-north");
            expectedDirections.add("north-north");

            Assert.assertArrayEquals(expectedDirections.toArray(), catcher.message.getPositions().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireDirections_Position_2_0(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedDirections = new ArrayList<>();

        try {
            model.addObserver(catcher);
            players.get(0).setPosition(map.getSpace(2,0));
            controller.useEffect(players.get(0), players, map);
            expectedDirections.add("north-east");
            expectedDirections.add("east");
            expectedDirections.add("north");
            expectedDirections.add("west");
            expectedDirections.add("west-west");
            expectedDirections.add("north-north");
            expectedDirections.add("west-north");

            Assert.assertArrayEquals(expectedDirections.toArray(), catcher.message.getPositions().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireDirections_Position_2_2(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedDirections = new ArrayList<>();

        try {
            model.addObserver(catcher);
            players.get(0).setPosition(map.getSpace(2,2));
            controller.useEffect(players.get(0), players, map);
            expectedDirections.add("east");
            expectedDirections.add("south-south");
            expectedDirections.add("south");
            expectedDirections.add("west");
            expectedDirections.add("south-east");
            expectedDirections.add("west-west");
            expectedDirections.add("west-south");

            Assert.assertArrayEquals(expectedDirections.toArray(), catcher.message.getPositions().toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Catcher implements Observer<RocketJumpChooseEv>{
        RocketJumpChooseEv message;

        @Override
        public void update(RocketJumpChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("west");

        try {
            players.get(0).setPosition(map.getSpace(2,2));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(map.getSpace(1,2), model.getMoveto());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<RocketJumpSetEv>{
        String direction;

        public Thrower(String direction) {
            this.direction = direction;
        }

        public void throwMessage(){
            notify(new RocketJumpSetEv(direction));
        }
    }
}
