package it.polimi.sw2019.model.Events;

import it.polimi.sw2019.model.Space;

import java.util.ArrayList;
import java.util.HashMap;

public class TracBeamChooseEv {
    private HashMap<String, ArrayList<String>> valid = new HashMap<>();
    private ArrayList<String> notselectable;

    public TracBeamChooseEv(HashMap<String, ArrayList<String>> valid, ArrayList<String> notselectable) {
        this.valid = valid;
        this.notselectable = notselectable;
    }

    public HashMap<String, ArrayList<String>> getValid() {
        return valid;
    }

    public ArrayList<String> getNotselectable() {
        return notselectable;
    }
}
