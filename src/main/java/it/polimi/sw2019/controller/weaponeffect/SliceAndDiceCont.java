package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.SliceAndDiceChooseEv;
import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weaponeffect_controller_events.SliceAndDiceSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.Cyberblade;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.SliceAndDice;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SliceAndDiceCont extends EffectController implements Observer<SliceAndDiceSetEv>{

    private SliceAndDice model;
    private Player attacker;
    private ArrayList<Player> players;

    public SliceAndDiceCont(Power model) {
        this.model = (SliceAndDice) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        acquireTarget();
    }

    private void acquireTarget(){
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> notselectable = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();

        notselectable.add(attacker.getNickname());
        initialize(notselectable);
        for (Player player : players){
            if (!(notselectable.contains(player.getNickname())) && (player.getPosition() == attacker.getPosition())){
                valid.add(player.getNickname());
            }else if (!(notselectable.contains(player.getNickname())) && (player.getPosition() != attacker.getPosition())){
                notreachable.add(player.getNickname());
            }
        }
        notify(new SliceAndDiceChooseEv(attacker.getNickname(), valid, notselectable, notreachable));
    }

    private void initialize(ArrayList<String> notselectable){
        Logger logger = Logger.getLogger("controller.SlinceAndDice");
        Weapon cyberblade;

        try {
            cyberblade = attacker.getWeapon("Cyberblade");
            notselectable.add(((Cyberblade) cyberblade.getPower()).getTarget().getNickname());
        }catch (InexistentWeaponException e){
            logger.log(Level.SEVERE, e.getMessage()+" doesn't have Cyberblade");
        }
    }

    @Override
    public void update(SliceAndDiceSetEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget())){
                model.setTarget(player);
                model.usePower(attacker);
                break;
            }
        }
    }
}
