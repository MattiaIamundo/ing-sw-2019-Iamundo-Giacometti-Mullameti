package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.FocusShotChooseEv;
import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.FocusShotSetEv;
import it.polimi.sw2019.model.weapon_power.FocusShot;
import it.polimi.sw2019.model.weapon_power.MachineGun;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FocusShotCont extends Observable<FocusShotChooseEv> implements Observer<FocusShotSetEv>, EffectController {

    private FocusShot model;
    private Player attacker;
    private ArrayList<Player> players;
    private Map map;

    public FocusShotCont(Power model) {
        this.model = (FocusShot) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTarget();
    }

    public void acquireTarget(){
        Logger logger = Logger.getLogger("controller.FocusShot");
        ArrayList<String> targets = new ArrayList<>();
        MachineGun basiceffect;

        try {
            basiceffect = (MachineGun) attacker.getWeapon("Machine Gun").getPower();
            targets.add(basiceffect.getTarget().getNickname());
            if (basiceffect.getTarget2() != null) {
                targets.add(basiceffect.getTarget2().getNickname());
            }
            notify(new FocusShotChooseEv(attacker.getNickname(), targets));
        }catch (InexistentWeaponException e){
            logger.log(Level.SEVERE,e.getMessage()+" doesn't have Machine Gun");
        }
    }

    @Override
    public void update(FocusShotSetEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget())){
                model.setTarget(player);
                break;
            }
        }
        model.usePower(attacker);
    }
}
