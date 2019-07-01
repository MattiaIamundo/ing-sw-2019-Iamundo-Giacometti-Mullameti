package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.HellionChooseEv;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.HellionSetEv;
import it.polimi.sw2019.model.weapon_power.Hellion;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class HellionCont extends VisibleTargetCont implements Observer<HellionSetEv> {

    private Hellion realmodel;

    public HellionCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Hellion) realmodel;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTarget(notSelectable());
    }

    @Override
    protected void acquireTarget(ArrayList<String> notselctable) {
        super.acquireTarget(notselctable);
        notselctable.remove(attacker.getNickname());
        notreachable.addAll(notselctable);
        notify(new HellionChooseEv(attacker.getNickname(), valid, notreachable));
    }

    private ArrayList<String> notSelectable(){
        ArrayList<String> notselectable = new ArrayList<>();

        for (Player player : players){
            if (player.getPosition() == attacker.getPosition()){
                notselectable.add(player.getNickname());
            }
        }
        return notselectable;
    }

    @Override
    public void update(HellionSetEv message) {
        ArrayList<Player> targets = new ArrayList<>();

        super.update(message);
        for (Player player : players){
            if (player.getPosition() == realmodel.getTarget().getPosition()){
                targets.add(player);
            }
        }
        realmodel.setMarkTargets(targets);
        realmodel.usePower(attacker);
    }
}
