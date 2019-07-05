package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the alternative effect of Zx-2
 * @author Mattia Iamundo
 */
public class ScannerMode extends SingleTarget implements Power{
    private Player target2;
    private Player target3;

    @Override
    public void usePower(Player attacker){
        super.setMark(attacker);
        if (target2 != null) {
            target2.getPlance().setMark(attacker);
        }
        if (target3 != null) {
            target3.getPlance().setMark(attacker);
        }
    }

    public Player getTarget2() {
        return target2;
    }

    public void setTarget2(Player target2) {
        this.target2 = target2;
    }

    public Player getTarget3() {
        return target3;
    }

    public void setTarget3(Player target3) {
        this.target3 = target3;
    }
}
