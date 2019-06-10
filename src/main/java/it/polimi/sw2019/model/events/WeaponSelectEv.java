package it.polimi.sw2019.model.events;

public abstract class WeaponSelectEv {
    private String weapon;
    private String nickname;

    public WeaponSelectEv(String weapon, String nickname) {
        this.weapon = weapon;
        this.nickname = nickname;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getNickname() {
        return nickname;
    }
}
