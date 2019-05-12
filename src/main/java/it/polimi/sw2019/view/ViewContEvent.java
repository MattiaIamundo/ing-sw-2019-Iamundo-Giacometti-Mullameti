package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.nethandler.ViewContEventInt;

import static it.polimi.sw2019.view.AmmoView.showAmmo;

public class ViewContEvent implements ViewContEventInt {

    //ammo has to be deserialized
    @Override
    public void showTheAmmo(Ammo ammo) {
        showAmmo( ammo );
    }
}
