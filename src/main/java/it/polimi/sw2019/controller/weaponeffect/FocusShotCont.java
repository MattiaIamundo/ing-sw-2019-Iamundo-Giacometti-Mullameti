package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.FocusShotSetEv;
import it.polimi.sw2019.model.weapon_power.FocusShot;
import it.polimi.sw2019.model.weapon_power.MachineGun;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class FocusShotCont implements Observer<FocusShotSetEv>, EffectController {

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
        ArrayList<String> targets = new ArrayList<>();
        MachineGun basiceffect;
        int i = 0;

        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Machine Gun"))){
            i++;
        }
        basiceffect = (MachineGun) attacker.listWeapon()[i].getPower();
        targets.add(basiceffect.getTarget1().getNickname());
        if (basiceffect.getTarget2() != null){
            targets.add(basiceffect.getTarget2().getNickname());
        }
        model.chooseTarget(attacker, targets);
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
