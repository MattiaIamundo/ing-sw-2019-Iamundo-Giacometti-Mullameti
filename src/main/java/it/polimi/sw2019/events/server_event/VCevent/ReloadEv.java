package it.polimi.sw2019.events.server_event.VCevent;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

import java.util.ArrayList;
import java.util.List;

public class ReloadEv extends ActionEv {

    private String weaponToReload = null;
    private List<String> weaponCanBeReload = new ArrayList<>();

    public String getWeaponToReload() {
        return weaponToReload;
    }

    public void setWeaponToReload(String weaponToReload) {
        this.weaponToReload = weaponToReload;
    }

    public List<String> getWeaponCanBeReload() {
        return weaponCanBeReload;
    }

    public void setWeaponCanBeReload(List<String> weaponCanBeReload) {
        this.weaponCanBeReload = weaponCanBeReload;
    }

    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
