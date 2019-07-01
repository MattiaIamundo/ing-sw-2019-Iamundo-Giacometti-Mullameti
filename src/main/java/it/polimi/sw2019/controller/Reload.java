package it.polimi.sw2019.controller;

import it.polimi.sw2019.events.client_event.Cevent.NotifyEndReloadEv;
import it.polimi.sw2019.events.client_event.Cevent.WeaponReloadChooseEv;
import it.polimi.sw2019.events.server_event.VCevent.ReloadEv;
import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.view.ObservableByGame;

import java.util.ArrayList;
import java.util.List;


public class Reload extends ObservableByGame implements Action {

    private String nameWeapon;
    private List<String> weaponCanBeReloaded = new ArrayList<>(3);
    private Game controller;

    public Reload(Game controller) {
        this.controller = controller;
    }

    public void handleEvent(ReloadEv reloadEv, Player player) {

        if ( reloadEv.getWeaponToReload() == null ) {
            //is the first time
            controller.getTurnOf().setAction(this);
        }
        else {
            this.nameWeapon = reloadEv.getWeaponToReload();
            useAction(player);
        }
        //search the weapon
        findWeaponToReload(player);

        if (this.weaponCanBeReloaded.isEmpty()) {
            //send event to end this turn and end this action
            notify( new NotifyEndReloadEv( player.getNickname() ) );
        }
        else {
            //send event to choose the weapon
            notify( new WeaponReloadChooseEv(player.getNickname(), this.weaponCanBeReloaded) );
        }

    }


    private void findWeaponToReload(Player player) {

        for( Weapon w : player.getWeapons() ) {

            if ( !w.getIsLoad() ) {

                boolean canBeReload = true;
                //how to understand if the player can choose a weapon to reload
                for( int i = 0; i < 2; i++ ) {

                    int[] playerAmmo = ( int[] ) player.getCopyAmmo();

                    switch (w.getCost()[i]) {

                        case "red":
                            if ( playerAmmo[0] - 1 < 0 ) {
                                canBeReload = false;
                            }
                            else {
                                playerAmmo[0]--;
                            }
                            break;
                        case "blue":
                            if ( playerAmmo[1] - 1 < 0 ) {
                                canBeReload = false;
                            }
                            else {
                                playerAmmo[1]--;
                            }
                            break;
                        case "yellow":
                            if ( playerAmmo[2] - 1 < 0 ) {
                                canBeReload = false;
                            }
                            else {
                                playerAmmo[2]--;
                            }
                            break;
                        default:
                            //this cost is null, so there is not any cost to pay for the player
                            break;
                    }

                }
                //if the possible reload can be done
                if ( canBeReload ) {
                    this.weaponCanBeReloaded.add(w.getName());
                }

            }//END getIsLoad

        }//END foreach weapon

    }


    public void useAction(Player player){

        try{
            //to do the minus to the ammo
            cancelAmmo(player);
            player.getWeapon( this.nameWeapon ).setIsLoad( player.getNickname(), true);

        } catch (InexistentWeaponException ex) {
            //do nothing here
        }
    }

    private void cancelAmmo(Player player) {

        for( Weapon weapon : player.getWeapons() ) {

            if ( weapon.getName().equals(this.nameWeapon) ) {

                for( int i = 0; i < 2; i++ ) {

                    switch (weapon.getCost()[i]) {

                        case "red":
                            player.getAmmo()[0]--;
                            break;
                        case "blue":
                            player.getAmmo()[1]--;
                            break;
                        case "yellow":
                            player.getAmmo()[2]--;
                            break;
                        default:
                            //this cost is null, so there is not any cost to pay for the player
                            break;
                    }
                }

            }
        }
    }
}
