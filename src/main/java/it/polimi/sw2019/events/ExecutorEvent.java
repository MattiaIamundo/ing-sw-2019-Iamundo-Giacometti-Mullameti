package it.polimi.sw2019.events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.server_event.VCevent.GrabEv;
import it.polimi.sw2019.events.server_event.VCevent.MoveEv;
import it.polimi.sw2019.events.server_event.VCevent.PowerupEv;
import it.polimi.sw2019.events.server_event.VCevent.ReloadEv;

public interface ExecutorEvent {

    void handleObject(MoveEv moveEv, Game game);

    void handleObject(ReloadEv reloadEv, Game game);

    void handleObject(GrabEv grabEv, Game game);

    void handleObject(PowerupEv powerupEv, Game game);
}
