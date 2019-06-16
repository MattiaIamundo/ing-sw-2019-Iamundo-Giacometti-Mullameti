package it.polimi.sw2019.model.events;

public class WhisperSetEv implements TargetSetEv{

    private String target;

    public WhisperSetEv(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }
}
