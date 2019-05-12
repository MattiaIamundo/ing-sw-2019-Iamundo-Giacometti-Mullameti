package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.nethandler.ModViewEventInt;

public class ModViewEvent implements ModViewEventInt {

    //ammo has to be deserialized
    @Override
    public void updateAmmo (Ammo ammo) {
       // AmmoView.update( ammo );
    }
}
