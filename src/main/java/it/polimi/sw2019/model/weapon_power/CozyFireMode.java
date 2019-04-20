package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.IllegalDirectionException;
import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the alternative power of the Furnace
 * @author Mattia Iamundo
 */
public class CozyFireMode implements Power{
    private Space targetarea;
    private String direction;

    @Override
    public void usePower(Player attacker){
        while (true){
            try {
                acquireSpace(attacker);
                break;
            }catch (IllegalDirectionException e){
                System.out.println(e.getMessage()+"isn't a valid direction, please choose one that isn't a wall\n");
            }catch (InvalidDirectionException e){
                System.out.println(e.getMessage()+"isn't a direction\n");
            }
        }
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition() == targetarea){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 1);
                Table.getPlayers(i).getPlance().setMark(attacker);
            }
        }
    }

    /**
     * This method acquire and verify the validity of the target space
     * @param attacker identify the attacker
     * @throws InvalidDirectionException caused by a mistake in the direction name
     * @throws IllegalDirectionException caused by selecting a direction in which there is a wall
     */
    private void acquireSpace(Player attacker) throws InvalidDirectionException, IllegalDirectionException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indicates the space that you want design as the target [north, west, south, east]\n");
        direction = scanner.nextLine();
        switch (direction){
            case "north":
                if (attacker.getPosition().getNord().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    targetarea = attacker.getPosition().getNord().getSpaceSecond();
                    break;
                }
            case "west":
                if (attacker.getPosition().getOvest().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    targetarea = attacker.getPosition().getOvest().getSpaceSecond();
                    break;
                }
            case "south":
                if (attacker.getPosition().getSud().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    targetarea = attacker.getPosition().getSud().getSpaceSecond();
                    break;
                }
            case "east":
                if (attacker.getPosition().getEst().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    targetarea = attacker.getPosition().getEst().getSpaceSecond();
                    break;
                }
            default:
                throw new InvalidDirectionException(direction);
        }
    }
}
