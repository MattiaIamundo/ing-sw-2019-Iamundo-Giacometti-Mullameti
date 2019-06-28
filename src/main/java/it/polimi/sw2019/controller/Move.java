package it.polimi.sw2019.controller;

import it.polimi.sw2019.events.server_event.VCevent.MoveEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

import java.util.ArrayList;
import java.util.List;

/**Class Move: One of player`s basic action
 * @author Merita Mullameti
 */

public class Move implements Action {

    private Space moveto;
    private List<String> movesPlayerCanDo = new ArrayList<>();
    private int nrOfMoves = 0;

    public Move() {

    }

    /**
     * set the attribute moveto with the player answer
     * and call useAction with player as parameter
     * @param moveEv move event
     * @param player the player who send the request
     */
    public void handleEvent(MoveEv moveEv, Player player) {

        if( moveEv.getMoves().isEmpty() ) {
            //is the firstTime, the player press the button for the first time
            this.moveto = null;
        }
        else if ( moveEv.getMoves().equals("North") ) {
            this.moveto = player.getPosition().getNorth().getSpaceSecond();
        }
        else if (moveEv.getMoves().equals("East")) {
            this.moveto = player.getPosition().getEast().getSpaceSecond();
        }
        else if (moveEv.getMoves().equals("South")) {
            this.moveto = player.getPosition().getSouth().getSpaceSecond();
        }
        else if (moveEv.getMoves().equals("West")) {
            this.moveto = player.getPosition().getWest().getSpaceSecond();
        }
        else {
            //here if the player want to stop the move
            this.moveto = player.getPosition();
        }
        useAction( player );
    }

    public void useAction(Player player) {

        if (moveto == null) {
            //control on what position the player can set
            //and create the new MoveEv
            findDirection(player);
            //MoveEv moveEv = new MoveEv(movesPlayerCanDo);
            this.nrOfMoves++;

        } else {
            player.setPosition(moveto);
            //come notificare a tutti??
            //qui devo notificare al giocatore stesso
            //se vuole andare avanti se gli è concesso
        }

        //notifica generale + notifica alla persona
        //player.notify();
    }

    private void findDirection(Player player) {

        if ( !player.getPosition().getNorth().isWall() ) {
            this.movesPlayerCanDo.add("North");
        }
        if ( !player.getPosition().getEast().isWall() ) {
            this.movesPlayerCanDo.add("East");
        }
        if ( !player.getPosition().getSouth().isWall() ) {
            this.movesPlayerCanDo.add("South");
        }
        if ( !player.getPosition().getWest().isWall() ) {
            this.movesPlayerCanDo.add("West");
        }

    }


}