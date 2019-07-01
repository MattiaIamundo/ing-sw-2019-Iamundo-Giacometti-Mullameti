package it.polimi.sw2019.controller;

import it.polimi.sw2019.events.client_event.Cevent.DirectionChooseEv;
import it.polimi.sw2019.events.server_event.VCevent.GrabEv;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.view.ObservableByGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Grab: One of player`s basic action
 * @author Merita Mullameti
 */
public class Grab extends ObservableByGame implements Action {

    private Ammo ammo = null;
    private Space newPosition = null;
    private Game controller;
    private List<String> movePlayerCanDo = new ArrayList<>();

    public Grab (Game controller) {
        this.controller = controller;
    }

    public void handleEvent(GrabEv grabEv, Player player) {

        if( grabEv.getMove().isEmpty() ) {
            //first time
            this.newPosition = null;
        }
        else if ( grabEv.getMove().equals("north") ) {
            this.newPosition = player.getPosition().getNorth().getSpaceSecond();
        }
        else if (grabEv.getMove().equals("east")) {
            this.newPosition = player.getPosition().getEast().getSpaceSecond();
        }
        else if (grabEv.getMove().equals("south")) {
            this.newPosition = player.getPosition().getSouth().getSpaceSecond();
        }
        else if (grabEv.getMove().equals("west")) {
            this.newPosition = player.getPosition().getWest().getSpaceSecond();
        }
        else {
            //here if the player want to stop the move
            this.newPosition = player.getPosition();
        }

        useAction(player);
    }

    public void useAction(Player player) {

        if(newPosition == null) {
            controller.getTurnOf().setAction(this);
            findDirection(player);
            notify(new DirectionChooseEv(player.getNickname(), movePlayerCanDo));
        }
        else {

            player.setPosition(this.newPosition);
            this.ammo = ((SpaceAmmo) newPosition).takeAmmo();
            player.addAmmo(this.ammo);
        }

    }

    private void findDirection(Player player) {

        if ( !player.getPosition().getNorth().isWall() && isSpaceAmmo( player.getPosition().getNorth().getSpaceSecond() ) ) {
            this.movePlayerCanDo.add("north");
        }
        if ( !player.getPosition().getEast().isWall() && isSpaceAmmo( player.getPosition().getEast().getSpaceSecond() ) ) {
            this.movePlayerCanDo.add("east");
        }
        if ( !player.getPosition().getSouth().isWall() && isSpaceAmmo( player.getPosition().getSouth().getSpaceSecond() ) ) {
            this.movePlayerCanDo.add("south");
        }
        if ( !player.getPosition().getWest().isWall() && isSpaceAmmo( player.getPosition().getWest().getSpaceSecond() ) ) {
            this.movePlayerCanDo.add("west");
        }
        if ( isSpaceAmmo( player.getPosition() ) )  {
            this.movePlayerCanDo.add("stop");
        }

    }

    private boolean isSpaceAmmo(Space position) {

        return position instanceof SpaceAmmo;
    }


}

