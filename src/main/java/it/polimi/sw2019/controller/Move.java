package it.polimi.sw2019.controller;

import it.polimi.sw2019.exception.IllegalDirectionException;
import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import java.util.Scanner;

/**Class Move: One of player`s basic action
 * @author Merita Mullameti
 */

public class Move implements  Action {

    private static Space moveto;
    Scanner scanner = new Scanner(System.in);

    public void useAction(Player player) {

        while (true) {

            System.out.println("In what direction do you want to move ?");
            String direction = scanner.nextLine();
            try {

                findDirection(player);
                break;

            } catch (InvalidDirectionException e) {

                System.out.println(e.getMessage() + "is an invalid direction! \n");

            } catch (IllegalDirectionException e) {

                System.out.println(e.getMessage() + "is an invalid direction, there is a wall! \n");
            }
        }
    }
    /**
     * @param player the player  who is going to be attacked using this power up card
     */

    protected static void findDirection(Player player) throws InvalidDirectionException, IllegalDirectionException {
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

}