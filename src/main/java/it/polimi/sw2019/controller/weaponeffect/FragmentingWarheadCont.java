package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.FragmentingWarhead;
import it.polimi.sw2019.model.weapon_power.PlasmaGun;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.RocketLauncher;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FragmentingWarheadCont implements EffectController{

    private FragmentingWarhead model;
    private Player attacker;
    private ArrayList<Player> players;

    public FragmentingWarheadCont(Power model) {
        this.model = (FragmentingWarhead) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        initialize();
    }

    private void initialize(){
        Logger logger = Logger.getLogger("controller.FragmentingWarHead");
        ArrayList<Player> targets = new ArrayList<>();
        Space origin;
        Weapon rocketlauncher;
        try {
            rocketlauncher = attacker.getWeapon("Rocket Launcher");
            origin = ((RocketLauncher) rocketlauncher.getPower()).getOrigin();
            for (Player player : players){
                if (player.getPosition() == origin){
                    targets.add(player);
                }
            }
            model.setPlayers(targets);
            if (targets.contains(((RocketLauncher) rocketlauncher.getPower()).getTarget())){
                model.setTarget(null);
            }else {
                model.setTarget(((RocketLauncher) rocketlauncher.getPower()).getTarget());
            }
            model.usePower(attacker);
        }catch (InexistentWeaponException e){
            logger.log(Level.SEVERE, e.getMessage()+" doesn't have Rocket Launcher");
        }
    }
}
