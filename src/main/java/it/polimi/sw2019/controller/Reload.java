package it.polimi.sw2019.controller;

import it.polimi.sw2019.events.server_event.VCevent.ReloadEv;
import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.view.ObservableByGame;

import java.util.ArrayList;
import java.util.List;


public class Reload extends ObservableByGame implements Action {

    private String nameWeapon;
    private Game controller;

    public Reload(Game controller) {
        this.controller = controller;
    }

    public void handleEvent(ReloadEv reloadEv, Player player) {

        if ( reloadEv.getWeaponToReload() == null ) {
            //is the first time
            ReloadEv newReloadEv1 = new ReloadEv();
            findWeaponToReload( newReloadEv1 ,player);

            //inviare la notifica tramite notify al solo giocatore richiedente...
        }
        else {
            this.nameWeapon = reloadEv.getWeaponToReload();
            useAction(player);
        }


    }


    private void findWeaponToReload(ReloadEv reloadEv ,Player player) {

        for( Weapon w : player.getWeapons() ) {

            if ( !w.getIsLoad() ) {
                // capire come dedurre se il player può caricare o meno
                reloadEv.getWeaponCanBeReload().add( w.getName() );
            }
        }
    }


    public void useAction(Player player){

        try{
            player.getWeapon( nameWeapon );
            player.getWeapon( nameWeapon ).setIsLoad(true);
            //diminuire le ammo del personaggio
            //inviare la notifica tramite notify ai giocatori...

        } catch (InexistentWeaponException ex) {

        }
    }

}
