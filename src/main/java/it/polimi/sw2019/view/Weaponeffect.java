package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Events.TargetAcquisitionEv;

public abstract class Weaponeffect extends ObservableByGame implements Observer<TargetAcquisitionEv> {
    @Override
    public void update(TargetAcquisitionEv message) {

    }
}
