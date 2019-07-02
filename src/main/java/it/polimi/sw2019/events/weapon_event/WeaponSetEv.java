package it.polimi.sw2019.events.weapon_event;

public  class WeaponSetEv {
    private String weapon;
    private String nickname;

    public WeaponSetEv(String weapon, String nickname) {
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
