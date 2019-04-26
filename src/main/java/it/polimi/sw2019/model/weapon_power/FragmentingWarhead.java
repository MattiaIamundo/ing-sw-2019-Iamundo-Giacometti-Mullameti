package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

/**
 * This class implements the second optional effect of Rocket Launcher
 * @author Mattia Iamundo
 */
public class FragmentingWarhead implements Power{

    @Override
    public void usePower(Player attacker){
        RocketLauncher basiceffect;
        int i = 0;

        while ((i< 3) && !(attacker.listWeapon()[i].getName().equals("RocketLauncher"))){
            i++;
        }
        basiceffect = (RocketLauncher) attacker.listWeapon()[i].getPower();
        for (int j = 0; j < 5; j++) {
            if (basiceffect.origin == Table.getPlayers(i).getPosition()){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 1);
            }
        }
        if (basiceffect.moveto != null){
            basiceffect.target.getPlance().giveDamage(attacker, 1);
        }
    }
}
