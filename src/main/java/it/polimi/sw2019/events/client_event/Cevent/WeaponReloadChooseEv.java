package it.polimi.sw2019.events.client_event.Cevent;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;

import java.util.ArrayList;
import java.util.List;

public class WeaponReloadChooseEv implements NotifyReturn {

    private String nickname;
    private List<String> weaponCanBeReloaded = new ArrayList<>(3);

    public WeaponReloadChooseEv(String nickname, List<String> weaponCanBeReloaded) {
        this.nickname = nickname;
        this.weaponCanBeReloaded = weaponCanBeReloaded;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<String> getWeaponCanBeReloaded() {
        return this.weaponCanBeReloaded;
    }

    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
