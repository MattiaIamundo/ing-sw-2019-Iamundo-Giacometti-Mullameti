package it.polimi.sw2019.events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.client_event.Cevent.DirectionChooseEv;
import it.polimi.sw2019.events.client_event.Cevent.NotifyEndMoveEv;
import it.polimi.sw2019.events.client_event.Cevent.NotifyEndReloadEv;
import it.polimi.sw2019.events.client_event.Cevent.WeaponReloadChooseEv;
import it.polimi.sw2019.events.client_event.MVevent.NotifyGrabEv;
import it.polimi.sw2019.events.client_event.MVevent.NotifyMoveEv;
import it.polimi.sw2019.events.client_event.MVevent.NotifyReloadEv;
import it.polimi.sw2019.events.server_event.VCevent.GrabEv;
import it.polimi.sw2019.events.server_event.VCevent.MoveEv;
import it.polimi.sw2019.events.server_event.VCevent.PowerupEv;
import it.polimi.sw2019.events.server_event.VCevent.ReloadEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.BarbecueChooseEv;
import it.polimi.sw2019.view.PlayerRemoteView;

public class ExecutorEventImp implements ExecutorEvent {

    public ExecutorEventImp() {

    }


    public void handleObject(MoveEv moveEv, Game game){
        game.handleEvent(moveEv);
    }

    public void handleObject(ReloadEv reloadEv, Game game){
        game.handleEvent(reloadEv);
    }

    public void handleObject(GrabEv grabEv, Game game){
        game.handleEvent(grabEv);
    }

    public void handleObject(PowerupEv powerupEv, Game game){
        //game.handleEvent(powerupEv);
    }






    public void updateObject(NotifyMoveEv moveEv, Game game) {
        game.update(moveEv);
    }

    public void updateObject(NotifyGrabEv grabEv, Game game) {
        game.update(grabEv);
    }

    public void updateObject(NotifyReloadEv reloadEv, Game game) {
        game.update(reloadEv);
    }

    public void updateObject(NotifyEndMoveEv endMoveEv, Game game) {
        game.update(endMoveEv);
    }

    public void updateObject(NotifyEndReloadEv endReloadEv, Game game) {
        game.update(endReloadEv);
    }

    public void updateObject(DirectionChooseEv directionChooseEv, Game game) {
        game.update(directionChooseEv);
    }

    public void updateObject(WeaponReloadChooseEv weaponReloadChooseEv, Game game) {
        game.update(weaponReloadChooseEv);
    }

    public void updateObject(BarbecueChooseEv barbecueChooseEv, Game game){
        game.update(barbecueChooseEv);
    }
}
