package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.ShadowstepChooseEv;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.ShadowstepSetEv;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.Shadowstep;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class ShadowstepCont extends MovePlayer implements Observer<ShadowstepSetEv> {

    private Shadowstep realmodel;

    public ShadowstepCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Shadowstep) realmodel;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquirePosition(attacker.getPosition(), true);
    }

    @Override
    protected void acquirePosition(Space playerpos, boolean singlespace) {
        super.acquirePosition(playerpos, singlespace);
        notify(new ShadowstepChooseEv(attacker.getNickname(), new ArrayList<>(positions.keySet())));
    }

    @Override
    public void update(ShadowstepSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
