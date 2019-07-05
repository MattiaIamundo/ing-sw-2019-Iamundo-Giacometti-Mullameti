package it.polimi.sw2019.controller.PowerUp;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.controller.powerup.TagbackGrenadeCont;
import it.polimi.sw2019.events.powerup_events.TagbackGrenadeChooseEv;
import it.polimi.sw2019.events.powerup_events.TagbackGrenadeSetEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.powerup.TagbackGrenade;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TagbackGrenadeContTest {
    Logger logger = Logger.getLogger("test.controller.PowerUp.TagbackGrenadeCont");
    ArrayList<Player> players;
    Map map;

    @Before
    public void setUp(){
        Game game = new Game();

        try {
            game.createMap("zero");
            game.addPlayers("attacker");
            game.addPlayers("target");
            players = (ArrayList<Player>) game.getPlayers();
            map = game.getGameboard().getMap();

            players.get(0).setPosition(map.getSpace(0,0));
            players.get(1).setPosition(map.getSpace(0,2));
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "out of map");
        }
    }

    @Test
    public void usePowerUpTest(){
        TagbackGrenadeCont controller = new TagbackGrenadeCont(new TagbackGrenade(), players);
        Catcher catcher = new Catcher();

        controller.addObserver(catcher);
        players.get(1).getPlance().giveDamage(players.get(0), 1);
        controller.usePowerUp(players.get(1));

        Assert.assertEquals("attacker", catcher.message.getTarget());
    }

    private class Catcher implements Observer<TagbackGrenadeChooseEv>{
        TagbackGrenadeChooseEv message;

        @Override
        public void update(TagbackGrenadeChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void update_HitTarget_Test(){
        TagbackGrenadeCont controller = new TagbackGrenadeCont(new TagbackGrenade(), players);
        Thrower thrower = new Thrower(true);

        thrower.addObserver(controller);
        players.get(1).getPlance().giveDamage(players.get(0), 1);
        controller.usePowerUp(players.get(1));
        thrower.throwMessage();

        Assert.assertNotNull(players.get(0).getPlance().getDamageTrack());
    }

    @Test
    public void update_NotHitTarget_Test(){
        TagbackGrenadeCont controller = new TagbackGrenadeCont(new TagbackGrenade(), players);
        Thrower thrower = new Thrower(false);

        thrower.addObserver(controller);
        players.get(1).getPlance().giveDamage(players.get(0), 1);
        controller.usePowerUp(players.get(1));
        thrower.throwMessage();

        Assert.assertTrue(players.get(0).getPlance().getDamageTrack().isEmpty());
    }

    private class Thrower extends Observable<TagbackGrenadeSetEv>{
        private Boolean hitIt;

        public Thrower(Boolean hitIt) {
            this.hitIt = hitIt;
        }

        public void throwMessage(){
            notify(new TagbackGrenadeSetEv(hitIt));
        }
    }
}
