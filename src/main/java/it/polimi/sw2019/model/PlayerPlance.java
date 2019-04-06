package it.polimi.sw2019.model;

import java.util.ArrayList;

public class PlayerPlance {

    private String[] damageTrack = new String[12];
    private boolean isFirstAdrenaline;
    private boolean isSecondAdrenaline;
    private boolean isKilled;
    private boolean isOverkilled;
    private boolean[] killed = new boolean[6];
    private ArrayList<Player> markedBy = new ArrayList<>();

    public boolean getFirstAdrenaline(){
        return isFirstAdrenaline;
    }
    public boolean getSecondAdrenaline(){
        return isSecondAdrenaline;
    }
    public boolean getKilled(){
        return isKilled;
    }
    public boolean getOverkilled(){
        return isOverkilled;
    }
    public void giveDamage(Player shooter, Integer quantity){

    }
    public void setMark(Player shooter){

    }
}
