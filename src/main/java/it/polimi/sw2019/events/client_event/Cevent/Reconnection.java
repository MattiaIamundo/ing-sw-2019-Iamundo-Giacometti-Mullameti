package it.polimi.sw2019.events.client_event.Cevent;

public class Reconnection {

    private boolean firstTime;
    private String recon;

    public Reconnection(boolean firstTimee, String rec) {
        firstTime = firstTimee;
        recon = rec;
    }

    public boolean getFirstTime() {
        return firstTime;
    }

    public String getRecon() {
        return recon;
    }
}
