package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Weapon;

public class WeaponView extends ObservableByGame implements Observer<Weapon> {

    private Weapon weapon;
    private UIinterface ui;

    public WeaponView() {}
    public WeaponView(UIinterface userImp) {
        ui = userImp;
    }

    protected void showWeapon() {

    }

    /**
     * this method show the update that one player did
     */
    public void update(Weapon message) {
        showWeapon();
    }
}
