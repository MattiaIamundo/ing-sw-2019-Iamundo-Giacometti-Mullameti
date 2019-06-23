package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponEffectController_events.ScannerModeChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the alternative effect of Zx-2
 * @author Mattia Iamundo
 */
public class ScannerMode extends Observable<ScannerModeChooseEv> implements Power, SingleTarget{

    private Player target1;
    private Player target2;
    private Player target3;

    @Override
    public void usePower(Player attacker){
        target1.getPlance().setMark(attacker);
        target2.getPlance().setMark(attacker);
        target3.getPlance().setMark(attacker);
    }

    public void chooseTargets(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable){
        notify(new ScannerModeChooseEv(attacker, valid, notreachable));
    }

    @Override
    public void setTarget(Player target1) {
        this.target1 = target1;
    }

    public void setTarget2(Player target2) {
        this.target2 = target2;
    }

    public void setTarget3(Player target3) {
        this.target3 = target3;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
