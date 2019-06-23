package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class ExtraGrenadeChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> validsquare;
    private ArrayList<String> moveto;

    public ExtraGrenadeChooseEv(Player attacker, ArrayList<String> validsquare) {
        this.attacker = attacker;
        this.validsquare = validsquare;
        moveto = null;
    }

    public ExtraGrenadeChooseEv(Player attacker, ArrayList<String> validsquare, ArrayList<String> moveto) {
        this.attacker = attacker;
        this.validsquare = validsquare;
        this.moveto = moveto;
    }

    @Override
    public String getAttacker() {
        return attacker.getNickname();
    }

    public ArrayList<String> getValidsquare() {
        return validsquare;
    }

    public ArrayList<String> getMoveto() {
        return moveto;
    }
}
