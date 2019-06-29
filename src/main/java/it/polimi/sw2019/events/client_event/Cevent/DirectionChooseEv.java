package it.polimi.sw2019.events.client_event.Cevent;

import it.polimi.sw2019.events.NotifyReturn;

import java.util.ArrayList;
import java.util.List;

public class DirectionChooseEv extends NotifyReturn {

    private List<String> movesPlayerCanDo = new ArrayList<>(3);

    public DirectionChooseEv(List<String> movesPlayerCanDo) {
        this.movesPlayerCanDo = movesPlayerCanDo;
    }

    public List<String> getMovesPlayerCanDo() {
        return this.movesPlayerCanDo;
    }
}
