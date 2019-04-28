package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

/**
 * This class implements the second optional effect of Rocket Launcher
 * @author Mattia Iamundo
 */
public class FragmentingWarhead implements Power{

    private RocketLauncher basiceffect;

    @Override
    public void usePower(Player attacker){
        int i = 0;
        while ((i< 3) && !(attacker.listWeapon()[i].getName().equals("RocketLauncher"))){
            i++;
        }
        basiceffect = (RocketLauncher) attacker.listWeapon()[i].getPower();
        for (int j = 0; j < 5; j++) {
            if ((basiceffect.origin == Table.getPlayers(i).getPosition()) && (attacker != Table.getPlayers(i))){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 1);
            }
        }
        if (basiceffect.target.getPosition() != basiceffect.origin){
            basiceffect.target.getPlance().giveDamage(attacker, 1);
        }
    }
}
