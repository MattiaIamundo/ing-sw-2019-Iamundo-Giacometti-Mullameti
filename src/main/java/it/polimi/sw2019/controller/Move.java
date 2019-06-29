package it.polimi.sw2019.controller;

import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.Cevent.DirectionChooseEv;
import it.polimi.sw2019.events.client_event.Cevent.NotifyEndMoveEv;
import it.polimi.sw2019.events.server_event.VCevent.MoveEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.List;

/**Class Move: One of player`s basic action
 * @author Merita Mullameti
 */

public class Move extends Observable<NotifyReturn> implements Action {

    private Space moveto = null;
    private List<String> movesPlayerCanDo = new ArrayList<>();
    private Game controller;
    private int nrOfMoves = 0;

    public Move(Game controller) {
        this.controller = controller;
    }

    /**
     * set the attribute moveto with the player answer
     * and call useAction with player as parameter
     * @param moveEv move event
     * @param player the player who send the request
     */
    public void handleEvent(MoveEv moveEv, Player player) {

        if( moveEv.getMove().isEmpty() ) {
            //is the firstTime, the player press the button for the first time
            this.moveto = null;
        }
        else if ( moveEv.getMove().equals("north") ) {
            this.moveto = player.getPosition().getNorth().getSpaceSecond();
        }
        else if (moveEv.getMove().equals("east")) {
            this.moveto = player.getPosition().getEast().getSpaceSecond();
        }
        else if (moveEv.getMove().equals("south")) {
            this.moveto = player.getPosition().getSouth().getSpaceSecond();
        }
        else if (moveEv.getMove().equals("west")) {
            this.moveto = player.getPosition().getWest().getSpaceSecond();
        }
        else {
            //here if the player want to stop the move
            this.moveto = player.getPosition();
        }
        useAction( player );
    }

    /**
     * if it is the first time, or it is after a single move,
     * a new event is send to the player to do another move
     * after a "stop by the player or three single move
     * the action is terminated
     * @param player the player who call the MoveEv
     */
    public void useAction(Player player) {

        if (moveto == null) {
            //control on what position the player can set
            //and create the new MoveEv
            controller.getTurnOf().setAction(this);
            findDirection(player);
            DirectionChooseEv directionChooseEv = new DirectionChooseEv(movesPlayerCanDo);
            directionChooseEv.setNickname(player.getNickname());
            //viene notificato al player che mosse vuole fare
            notify(directionChooseEv);

        }
        else if (player.getPosition().equals(this.moveto)) {
            //the player stop the action
            nrOfMoves = 0;
            controller.getTurnOf().addUsedAction();
            notify(new NotifyEndMoveEv(player.getNickname()));
        }
        else {
            if (nrOfMoves < 2) {
                //the player can choose another
                player.setPosition(moveto);
                nrOfMoves++;
                findDirection(player);
                DirectionChooseEv directionChooseEv = new DirectionChooseEv(movesPlayerCanDo);
                directionChooseEv.setNickname(player.getNickname());
                notify(directionChooseEv);
            }
            if (nrOfMoves == 3) {
                //the player finish the action
                nrOfMoves = 0;
                controller.getTurnOf().addUsedAction();
                notify(new NotifyEndMoveEv(player.getNickname()));
            }
        }

    }

    /**
     * find the possible directions which the player can choose
     * @param player the player who send the MoveEv
     */
    private void findDirection(Player player) {

        if ( !player.getPosition().getNorth().isWall() ) {
            this.movesPlayerCanDo.add("north");
        }
        if ( !player.getPosition().getEast().isWall() ) {
            this.movesPlayerCanDo.add("east");
        }
        if ( !player.getPosition().getSouth().isWall() ) {
            this.movesPlayerCanDo.add("south");
        }
        if ( !player.getPosition().getWest().isWall() ) {
            this.movesPlayerCanDo.add("west");
        }

        this.movesPlayerCanDo.add("stop");
    }


}