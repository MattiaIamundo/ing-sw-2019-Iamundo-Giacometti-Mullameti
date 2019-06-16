package it.polimi.sw2019.model.events;

public class BlackHoleSetEv {
    private String target1;
    private String target2;

    public BlackHoleSetEv(String target1, String target2) {
        this.target1 = target1;
        this.target2 = target2;
    }

    public BlackHoleSetEv(String target1) {
        this.target1 = target1;
        target2 = null;
    }

    public String getTarget1() {
        return target1;
    }

    public String getTarget2() {
        return target2;
    }
}
