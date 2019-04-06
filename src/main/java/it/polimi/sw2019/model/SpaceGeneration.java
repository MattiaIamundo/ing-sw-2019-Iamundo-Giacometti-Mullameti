package it.polimi.sw2019.model;

public class SpaceGeneration extends Space {

    private Weapon[] weapon = new Weapon[3];

    public Weapon takeWeapon(Integer weaponNum){
        return weapon[weaponNum];
    }
    public Weapon[] listWeapon(){
        return weapon;
    }
}
