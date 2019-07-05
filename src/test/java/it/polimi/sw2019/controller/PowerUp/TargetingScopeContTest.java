package it.polimi.sw2019.controller.PowerUp;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.controller.powerup.TargetingScopeCont;
import it.polimi.sw2019.events.powerup_events.TargetingScopeChooseEv;
import it.polimi.sw2019.events.powerup_events.TargetingScopeSetEv;
import it.polimi.sw2019.model.AmmoTriple;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.powerup.TargetingScope;
import it.polimi.sw2019.model.weapon_power.PlasmaGun;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Logger;

public class TargetingScopeContTest {
    Logger logger = Logger.getLogger("test.controller.PowerUp.TargetingScopeCont");
    ArrayList<Player> players;
    Map map;

    @Before
    public void setUp(){
        Game game = new Game();
        PlasmaGun weaponEffect = new PlasmaGun();

        game.createMap("zero");
        game.addPlayers("attacker");
        game.addPlayers("target");
        players = (ArrayList<Player>) game.getPlayers();
        map = game.getGameboard().getMap();

        players.get(0).addAmmo(new AmmoTriple("red", "blue", "yellow", null));
        weaponEffect.setTarget(players.get(1));
        weaponEffect.usePower(players.get(0));
    }

    @Test
    public void usePowerUpTest(){
        TargetingScopeCont controller = new TargetingScopeCont(new TargetingScope(), players);
        Catcher catcher = new Catcher();
        ArrayList<String> expectedTarget = new ArrayList<>();
        ArrayList<String> expectedAmmo = new ArrayList<>();

        controller.addObserver(catcher);
        controller.usePowerUp(players.get(0));
        expectedAmmo.add("red");
        expectedAmmo.add("blue");
        expectedAmmo.add("yellow");
        expectedTarget.add("target");

        Assert.assertArrayEquals(expectedTarget.toArray(), catcher.message.getTargets().toArray());
        Assert.assertArrayEquals(expectedAmmo.toArray(), catcher.message.getAmmos().toArray());
    }

    private class Catcher implements Observer<TargetingScopeChooseEv>{
        TargetingScopeChooseEv message;

        @Override
        public void update(TargetingScopeChooseEv message) {
            this.message = message;
        }
    }

    @Test
    public void updateTest_RedAmmo(){
        TargetingScopeCont controller = new TargetingScopeCont(new TargetingScope(), players);
        Thrower thrower = new Thrower("target", "red");

        thrower.addObserver(controller);
        controller.usePowerUp(players.get(0));
        thrower.throwMessage();

        Assert.assertEquals(0, players.get(0).getAmmo()[0]);
        Assert.assertEquals(3, players.get(1).getPlance().getDamageTrack().size());
    }

    @Test
    public void updateTest_BlueAmmo(){
        TargetingScopeCont controller = new TargetingScopeCont(new TargetingScope(), players);
        Thrower thrower = new Thrower("target", "blue");

        thrower.addObserver(controller);
        controller.usePowerUp(players.get(0));
        thrower.throwMessage();

        Assert.assertEquals(0, players.get(0).getAmmo()[1]);
        Assert.assertEquals(3, players.get(1).getPlance().getDamageTrack().size());
    }

    @Test
    public void updateTest_YellowAmmo(){
        TargetingScopeCont controller = new TargetingScopeCont(new TargetingScope(), players);
        Thrower thrower = new Thrower("target", "yellow");

        thrower.addObserver(controller);
        controller.usePowerUp(players.get(0));
        thrower.throwMessage();

        Assert.assertEquals(0, players.get(0).getAmmo()[2]);
        Assert.assertEquals(3, players.get(1).getPlance().getDamageTrack().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void updateTest_NoAmmo(){
        TargetingScopeCont controller = new TargetingScopeCont(new TargetingScope(), players);
        Thrower thrower = new Thrower("target", "");

        thrower.addObserver(controller);
        controller.usePowerUp(players.get(0));
        thrower.throwMessage();
    }

    private class Thrower extends Observable<TargetingScopeSetEv>{
        private String target;
        private String ammo;

        public Thrower(String target, String ammo) {
            this.target = target;
            this.ammo = ammo;
        }

        public void throwMessage(){
            notify(new TargetingScopeSetEv(target, ammo));
        }
    }
}
