package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.IllegalDirectionException;
import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.model.Player;

import java.util.Scanner;

/**
 * This class implements the first optional effect of Rocket Launcher
 * @author Mattia Iamundo
 */
public class RocketJump implements Power{

    @Override
    public void usePower(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String answer;
        while (true){
            try {
                moveAttacker(attacker);
                break;
            }catch (InvalidDirectionException e){
                System.out.println(e.getMessage()+" isn't a cardinal direction\n");
            }catch (IllegalDirectionException e){
                System.out.println(e.getMessage()+" is in front of a wall, you can't choose this one as a valid direction\n");
            }
        }
        System.out.println("Would you want to move 1 more square? [yes, no]");
        answer = scanner.nextLine();
        if (answer.equals("yes")){
            while (true){
                try {
                    moveAttacker(attacker);
                    break;
                }catch (InvalidDirectionException e){
                    System.out.println(e.getMessage()+" isn't a cardinal direction\n");
                }catch (IllegalDirectionException e){
                    System.out.println(e.getMessage()+" is in front of a wall, you can't choose this one as a valid direction\n");
                }
            }
        }
    }

    /**
     * This method acquire and check the validity of the moving direction
     * @param attacker identify the attacker
     * @throws InvalidDirectionException caused by a mistake in the insertion of the name of the direction
     * @throws IllegalDirectionException caused by selecting a direction that is in front of a wall
     */
    private void moveAttacker(Player attacker) throws InvalidDirectionException, IllegalDirectionException {
        Scanner scanner = new Scanner(System.in);
        String direction;
        System.out.println("Insert the direction in which you intend to move");
        direction = scanner.nextLine();
        switch (direction){
            case "north":
                if (attacker.getPosition().getNorth().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    attacker.setPosition(attacker.getPosition().getNorth().getSpaceSecond());
                    break;
                }
            case "west":
                if (attacker.getPosition().getWest().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    attacker.setPosition(attacker.getPosition().getWest().getSpaceSecond());
                    break;
                }
            case "south":
                if (attacker.getPosition().getSouth().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    attacker.setPosition(attacker.getPosition().getSouth().getSpaceSecond());
                    break;
                }
            case "east":
                if (attacker.getPosition().getEast().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    attacker.setPosition(attacker.getPosition().getEast().getSpaceSecond());
                    break;
                }
            default:
                throw new InvalidDirectionException(direction);
        }
    }
}
