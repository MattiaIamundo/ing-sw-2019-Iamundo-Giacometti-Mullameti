package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.events.WeaponSelectEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class Shoot implements Action, Observer<WeaponSelectEv> {

    public static void useAction(Player attacker){
        ArrayList<String> weapons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (attacker.listWeapon()[i] != null && attacker.listWeapon()[i].getIsLoad()){
                weapons.add(attacker.listWeapon()[i].getName());
            }
        }
    }

    @Override
    public void update(WeaponSelectEv message) {
        Weapon weapon;
        Player attacker;

        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getNickname().equals(message.getNickname())){
                attacker = Table.getPlayers(i);
                for (int j = 0; j < 3; j++) {
                    if (attacker.listWeapon()[j].getName().equals(message.getWeapon())){
                        //Server.WeaponeffectManager.acquirepower()
                    }
                }
            }
        }
    }
}
