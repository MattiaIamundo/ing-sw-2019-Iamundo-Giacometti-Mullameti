package it.polimi.sw2019.model.events;

public class FlamethrowerSetEv implements LineFireSetEv{

    private String target1;
    private String target2;

    public FlamethrowerSetEv(String target1, String target2) {
        this.target1 = target1;
        this.target2 = target2;
    }

    public String getTarget1() {
        return target1;
    }

    public String getTarget2() {
        return target2;
    }
}
