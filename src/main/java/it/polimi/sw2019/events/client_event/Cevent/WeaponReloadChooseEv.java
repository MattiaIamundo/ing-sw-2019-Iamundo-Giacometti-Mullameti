package it.polimi.sw2019.events.client_event.Cevent;

import it.polimi.sw2019.events.NotifyReturn;

import java.util.ArrayList;
import java.util.List;

public class WeaponReloadChooseEv extends NotifyReturn {

    private List<String> weaponCanBeReloaded = new ArrayList<>(3);

    public WeaponReloadChooseEv(String nickname, List<String> weaponCanBeReloaded) {
        super(nickname);
        this.weaponCanBeReloaded = weaponCanBeReloaded;
    }

    public List<String> getWeaponCanBeReloaded() {
        return this.weaponCanBeReloaded;
    }
}
