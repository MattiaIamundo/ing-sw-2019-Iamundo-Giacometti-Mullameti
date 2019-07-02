package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.NanoTracerChooseEv;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.NanoTracerSetEv;
import it.polimi.sw2019.model.weapon_power.NanoTracerMode;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class NanoTracerCont extends VisibleTargetCont implements Observer<NanoTracerSetEv> {

    private NanoTracerMode realmodel;

    public NanoTracerCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (NanoTracerMode) realmodel;
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
        notify(new NanoTracerChooseEv(attacker.getNickname(), valid, notreachable));
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
    public void update(NanoTracerSetEv message) {
        ArrayList<Player> targets = new ArrayList<>();

        super.update(message);
        for (Player player : players){
            if (player.getPosition() == realmodel.getTarget().getPosition()){
                targets.add(player);
            }
        }
        realmodel.setTargets(targets);
        realmodel.usePower(attacker);
    }
}
