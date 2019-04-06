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

    }
    public boolean getSecondAdrenaline(){

    }
    public boolean getKilled(){

    }
    public boolean getOverkilled(){

    }
    public void giveDamage(Player shooter, Integer quantity){

    }
    public void setMark(Player shooter){

    }
}
