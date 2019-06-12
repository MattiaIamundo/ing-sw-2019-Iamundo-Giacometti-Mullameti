package it.polimi.sw2019.model.events;

public class ShockwaveSetEv {

    private String target1;
    private String target2;
    private String target3;

    public ShockwaveSetEv(String target1, String target2, String target3) {
        this.target1 = target1;
        this.target2 = target2;
        this.target3 = target3;
    }

    public ShockwaveSetEv(String target1, String target2) {
        this.target1 = target1;
        this.target2 = target2;
        target3 = null;
    }

    public ShockwaveSetEv(String target1) {
        this.target1 = target1;
        target2 = null;
        target3 = null;
    }

    public String getTarget1() {
        return target1;
    }

    public String getTarget2() {
        return target2;
    }

    public String getTarget3() {
        return target3;
    }
}
