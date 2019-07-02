package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.weaponeffect_controller_events.TractorBeamChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.TractorBeamSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.TractorBeam;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TractorBeamContTest {
    private Logger logger = Logger.getLogger("test.TractorBeamCont");
    private ArrayList<Player> players;
    private Map map;
    private TractorBeam model = new TractorBeam();
    private TractorBeamCont controller = new TractorBeamCont(model);

    @Before
    public void setUp(){
        Game game = new Game();

        game.createMap("zero");
        game.addPlayers("attacker");
        game.addPlayers("target1");
        game.addPlayers("target2");
        game.addPlayers("target3");
        map = game.getGameboard().getMap();
        players = (ArrayList<Player>) game.getPlayers();
    }

    @Test
    public void acquireTargetTest_AttackerPosition_3_2(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedMovesTarget1 = new ArrayList<>();
        ArrayList<String> expectedMovesTarget2 = new ArrayList<>();
        ArrayList<String> expectedMovesTarget3 = new ArrayList<>();

        try {
            controller.addObserver(catcher);
            players.get(0).setPosition(map.getSpace(3,2));
            players.get(1).setPosition(map.getSpace(3,0));
            players.get(2).setPosition(map.getSpace(1,0));
            players.get(3).setPosition(map.getSpace(2,2));
            controller.useEffect(players.get(0), players, map);
            expectedValid.add("target3");
            expectedValid.add("target2");
            expectedValid.add("target1");

            expectedMovesTarget1.add("zero");
            expectedMovesTarget1.add("north");
            expectedMovesTarget1.add("west");
            expectedMovesTarget1.add("north-west");
            expectedMovesTarget1.add("north-north");

            expectedMovesTarget2.add("east");
            expectedMovesTarget2.add("east-east");
            expectedMovesTarget2.add("east-north");
            expectedMovesTarget2.add("north-north");

            expectedMovesTarget3.add("zero");
            expectedMovesTarget3.add("east");
            expectedMovesTarget3.add("south-south");
            expectedMovesTarget3.add("south");
            expectedMovesTarget3.add("west");
            expectedMovesTarget3.add("south-east");

            Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().keySet().toArray());
            Assert.assertArrayEquals(expectedMovesTarget1.toArray(), catcher.message.getValid().get("target1").toArray());
            Assert.assertArrayEquals(expectedMovesTarget2.toArray(), catcher.message.getValid().get("target2").toArray());
            Assert.assertArrayEquals(expectedMovesTarget3.toArray(), catcher.message.getValid().get("target3").toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    @Test
    public void acquireTargetTest_AttackerPosition_1_0(){
        Catcher catcher = new Catcher();
        ArrayList<String> expectedValid = new ArrayList<>();
        ArrayList<String> expectedMovesTarget1 = new ArrayList<>();
        ArrayList<String> expectedMovesTarget2 = new ArrayList<>();
        ArrayList<String> expectedMovesTarget3 = new ArrayList<>();

        try {
            controller.addObserver(catcher);
            players.get(0).setPosition(map.getSpace(1,0));
            players.get(1).setPosition(map.getSpace(2,0));
            players.get(2).setPosition(map.getSpace(3,2));
            players.get(3).setPosition(map.getSpace(0,2));
            controller.useEffect(players.get(0), players, map);
            expectedValid.add("target3");
            expectedValid.add("target2");
            expectedValid.add("target1");

            expectedMovesTarget1.add("zero");
            expectedMovesTarget1.add("north-east");
            expectedMovesTarget1.add("east");
            expectedMovesTarget1.add("north");
            expectedMovesTarget1.add("west");
            expectedMovesTarget1.add("west-west");
            expectedMovesTarget1.add("west-north");

            expectedMovesTarget2.add("south-south");
            expectedMovesTarget2.add("south");
            expectedMovesTarget2.add("west-south");

            expectedMovesTarget3.add("south-south");
            expectedMovesTarget3.add("east-south");

            Assert.assertArrayEquals(expectedValid.toArray(), catcher.message.getValid().keySet().toArray());
            Assert.assertArrayEquals(expectedMovesTarget1.toArray(), catcher.message.getValid().get("target1").toArray());
            Assert.assertArrayEquals(expectedMovesTarget2.toArray(), catcher.message.getValid().get("target2").toArray());
            Assert.assertArrayEquals(expectedMovesTarget3.toArray(), catcher.message.getValid().get("target3").toArray());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Catcher implements Observer<TractorBeamChooseEv>{
        TractorBeamChooseEv message;

        @Override
        public void update(TractorBeamChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest(){
        Thrower thrower = new Thrower("target1", "zero");

        try {
            players.get(0).setPosition(map.getSpace(3,2));
            players.get(1).setPosition(map.getSpace(3,0));
            players.get(2).setPosition(map.getSpace(1,0));
            players.get(3).setPosition(map.getSpace(2,2));
            thrower.addObserver(controller);
            controller.useEffect(players.get(0), players, map);
            thrower.throwMessage();

            Assert.assertEquals(players.get(1), model.getTarget());
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map boundaries");
        }
    }

    private class Thrower extends Observable<TractorBeamSetEv>{
        private String target;
        private String moveto;

        public Thrower(String target, String moveto) {
            this.target = target;
            this.moveto = moveto;
        }

        public void throwMessage(){
            notify(new TractorBeamSetEv(target, moveto));
        }
    }
}
