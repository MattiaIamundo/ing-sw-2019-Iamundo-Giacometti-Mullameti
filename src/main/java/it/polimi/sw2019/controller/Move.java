package it.polimi.sw2019.controller;

import it.polimi.sw2019.events.server_event.VCevent.MoveEv;
import it.polimi.sw2019.exception.IllegalDirectionException;
import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.model.Connection;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

import java.util.ArrayList;
import java.util.List;

/**Class Move: One of player`s basic action
 * @author Merita Mullameti
 */

public class Move implements  Action {

    private Space moveto;
    private List<String> movesPlayerCanDo = new ArrayList<>();

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
            MoveEv moveEv = new MoveEv(movesPlayerCanDo);


        } else {
            player.setPosition(moveto);
            //come notificare a tutti??
            //qui devo notificare al giocatore stesso
            //se vuole andare avanti se gli è concesso
        }
        //notifica generale + notifica alla persona
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
/*


    public void findDirection(Player player) throws InvalidDirectionException, IllegalDirectionException {
        String direction;
        String nrSpaces;
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the direction you want to move to : ");
        direction = scanner.nextLine();

        switch (direction) {

            case "north":
                if (player.getPosition().getNorth().isWall()) {
                    throw new IllegalDirectionException(direction);
                } else {

                    moveto = player.getPosition().getNorth().getSpaceSecond();
                    player.setPosition(moveto);

                }
                break;

            case "south":
                if (player.getPosition().getSouth().isWall()) {
                    throw new IllegalDirectionException(direction);
                } else {

                    moveto = player.getPosition().getSouth().getSpaceSecond();
                    player.setPosition(moveto);

                }
                break;
            case "west":
                if (player.getPosition().getWest().isWall()) {
                    throw new IllegalDirectionException(direction);
                } else {

                    moveto = player.getPosition().getWest().getSpaceSecond();
                    player.setPosition(moveto);
                }
                break;
            case "east":
                if (player.getPosition().getEast().isWall()) {
                    throw new IllegalDirectionException(direction);
                } else {

                    moveto = player.getPosition().getEast().getSpaceSecond();
                    player.setPosition(moveto);

                }
                break;
            default:
                throw new InvalidDirectionException(direction);
        }

    }

 */


}