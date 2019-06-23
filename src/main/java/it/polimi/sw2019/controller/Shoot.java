package it.polimi.sw2019.controller;

import it.polimi.sw2019.controller.weaponeffect.WeaponEffectManager;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weaponEffectController_events.WeaponSelectEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class Shoot implements Action, Observer<WeaponSelectEv> {

    private ArrayList<Player> players;
    private Map map;
    private WeaponEffectManager weaponEffectManager;

    public Shoot(ArrayList<Player> players, Map map) {
        this.players = players;
        this.map = map;
        weaponEffectManager = new WeaponEffectManager(players, map);
    }

    public void useAction(Player attacker){
        ArrayList<String> weapons = new ArrayList<>();
        for (Weapon weapon : attacker.getWeapons()){
            if (weapon.getIsLoad()){
                weapons.add(weapon.getName());
            }
        }
    }

    @Override
    public void update(WeaponSelectEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getNickname())){
                for (Weapon weapon : player.getWeapons()){
                    if (weapon.getName().equals(message.getWeapon())){
                        weaponEffectManager.acquirePower(weapon, player);
                    }
                }
            }
        }
    }
}
