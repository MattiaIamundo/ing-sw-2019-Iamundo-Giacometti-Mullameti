package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.IllegalDirectionException;
import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

import java.util.Scanner;

/**
 * This class implement the first optional effect of Cyberblade
 * @author Mattia Iamundo
 */
public class Shadowstep implements Power{

    @Override
    public void usePower(Player attacker){
        while (true){
            try {
                attacker.setPosition(acquireDirection(attacker));
                break;
            }catch (InvalidDirectionException e){
                System.out.println(e.getMessage()+" isn't a cardinal direction\n");
            }catch (IllegalDirectionException e){
                System.out.println(e.getMessage()+" isn't a valid direction, it's in front of a wall\n");
            }
        }
    }

    /**
     * This method acquire and check the validity of the direction in which the attacker intent to move
     * @param attacker identify the attacker
     * @return the square on which the attacker will be moved
     * @throws InvalidDirectionException caused by a mistake in the insertion of the direction's name
     * @throws IllegalDirectionException caused by selecting a direction that is in front of a wall
     */
    private Space acquireDirection(Player attacker) throws InvalidDirectionException, IllegalDirectionException{
        Scanner scanner = new Scanner(System.in);
        String direction;
        System.out.println("Insert the cardinal direction in which you intend to move");
        direction = scanner.nextLine();
        direction = direction.toLowerCase();
        switch (direction){
            case "north":
                if (attacker.getPosition().getNorth().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    return attacker.getPosition().getNorth().getSpaceSecond();
                }
            case "west":
                if (attacker.getPosition().getWest().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    return attacker.getPosition().getWest().getSpaceSecond();
                }
            case "south":
                if (attacker.getPosition().getSouth().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    return attacker.getPosition().getSouth().getSpaceSecond();
                }
            case "east":
                if (attacker.getPosition().getEast().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    return attacker.getPosition().getEast().getSpaceSecond();
                }
            default:
                throw new InvalidDirectionException(direction);
        }
    }
}
