package it.polimi.sw2019.controller;

import it.polimi.sw2019.events.weapon_event.WeaponChooseEv;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weapon_event.WeaponSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class Shoot extends Observable<WeaponChooseEv> implements Action, Observer<WeaponSetEv> {

    private ArrayList<Player> players;
    private WeaponEffectManager weaponEffectManager;

    public Shoot(ArrayList<Player> players, Map map, Game game) {
        this.players = players;
        this.addObserver(game);
        weaponEffectManager = new WeaponEffectManager(players, map, game);
    }

    public void useAction(Player attacker){
        ArrayList<String> weapons = new ArrayList<>();
        for (Weapon weapon : attacker.getWeapons()){
            if (weapon.getIsLoad()){
                weapons.add(weapon.getName());
            }
        }
        notify(new WeaponChooseEv(attacker.getNickname(), weapons));
    }

    @Override
    public void update(WeaponSetEv message) {
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
