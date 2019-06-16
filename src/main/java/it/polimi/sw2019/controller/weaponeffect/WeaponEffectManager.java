package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Alternative;
import it.polimi.sw2019.model.DoubleAdditive;
import it.polimi.sw2019.model.events.PowerSelectEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class WeaponEffectManager implements Observer<PowerSelectEv> {
    private Player attacker;
    private Weapon weapon;
    private Power power;
    private String basicpower = "basic";

    public void acquirePower(Weapon weapon, Player attacker){
        ArrayList<String> powers = new ArrayList<>();
        if (weapon instanceof Alternative){
            powers.add(basicpower);
            powers.add(((Alternative) weapon).getAlternativePower().toString());
            weapon.choosePower(powers);
        }else if ((weapon instanceof DoubleAdditive) && (weapon.getName().equals("Plasma Gun") || weapon.getName().equals("Rocket Launcher") || weapon.getName().equals("Cyberblade"))){
            powers.add(basicpower);
            powers.add(((DoubleAdditive) weapon).getFirstAdditivePower().toString());
            weapon.choosePower(powers);
        }else {
            power = weapon.getPower();

        }
    }

    @Override
    public void update(PowerSelectEv message) {
        if (message.getPower().equals(basicpower)){
            power = weapon.getPower();
            //Iterate on the list of effectcontroller and for each call method .useEffect()
        }else if (weapon instanceof Alternative){
            power = ((Alternative) weapon).getAlternativePower();
            //Iterate on the list of effectcontroller and for each call method .useEffect()
        }else {
            power = ((DoubleAdditive) weapon).getFirstAdditivePower();
            //Iterate on the list of effectcontroller and for each call method .useEffect()
        }
    }
}
