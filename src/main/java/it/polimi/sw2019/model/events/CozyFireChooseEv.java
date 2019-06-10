package it.polimi.sw2019.model.events;

import java.util.ArrayList;

public class CozyFireChooseEv {
    private ArrayList<String> positions;

    public CozyFireChooseEv(ArrayList<String> positions) {
        this.positions = positions;
    }

    public ArrayList<String> getPositions() {
        return positions;
    }
}
