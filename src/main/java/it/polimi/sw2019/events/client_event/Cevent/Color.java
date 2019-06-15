package it.polimi.sw2019.events.client_event.Cevent;

import java.util.ArrayList;
import java.util.List;

public class Color {

    private boolean firstTime;
    private boolean duplicated;
    private List<String> colors = new ArrayList<>(5);

    public Color(boolean firstTime, boolean duplicated, List<String> nickname) {
        this.firstTime = firstTime;
        this.duplicated = duplicated;
        this.colors = nickname;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public boolean isDuplicated() {
        return duplicated;
    }

    public List<String> getColors() {
        return colors;
    }
}
