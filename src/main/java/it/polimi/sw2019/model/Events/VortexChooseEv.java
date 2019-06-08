package it.polimi.sw2019.model.Events;

import java.util.ArrayList;
import java.util.HashMap;

public class VortexChooseEv {
    private HashMap<String, ArrayList<String>> validchoices;

    public VortexChooseEv(HashMap<String, ArrayList<String>> validchoices) {
        this.validchoices = validchoices;
    }

    public HashMap<String, ArrayList<String>> getValidchoices() {
        return validchoices;
    }
}
