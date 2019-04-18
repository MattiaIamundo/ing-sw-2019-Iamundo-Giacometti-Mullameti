package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the alternative attack for the Flamethrower
 * @author Mattia Iamundo
 */
public class BarbecueMode implements Power{

    private Space targetarea1;
    private Space targetarea2;
    private Scanner scanner = new Scanner(System.in);

    /**
     * @param attacker the player who throws the attack
     */
    @Override
    public void usePower(Player attacker){
        System.out.println("Choose firing direction:");
        try {
            findDirection(scanner.nextLine(), attacker);
        }
        catch (InvalidDirectionException e){
            System.out.println(e.getMessage()+"is an invalid direction, insert a valid direction\n");
            usePower(attacker);
        }
        for (int i=0; i <= 5; i++){
            if (Table.getPlayers(i).getPosition() == targetarea1){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 2);
            }else if (Table.getPlayers(i).getPosition() == targetarea2){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 1);
            }
        }
    }

    /**
     * The method find the two Space identified by the direction given by the Attacker
     * @param dir identifies the direction of the attack (e.g. north, south, east, west)
     * @param attacker identifies the Player that use the power
     */
    private void findDirection(String dir, Player attacker) throws InvalidDirectionException {
        switch (dir) {
            case "north":
                if (attacker.getPosition().getNord().isWall()) {
                    throw new InvalidDirectionException("north");
                }
                targetarea1 = attacker.getPosition().getNord().getSpaceSecond();
                if (targetarea1.getNord().isWall()) {
                    targetarea2 = null;
                } else {
                    targetarea2 = targetarea1.getNord().getSpaceSecond();
                }
                break;

            case "east":
                if (attacker.getPosition().getEst().isWall()) {
                    throw new InvalidDirectionException("east");
                }
                targetarea1 = attacker.getPosition().getEst().getSpaceSecond();
                if (targetarea1.getEst().isWall()) {
                    targetarea2 = null;
                } else {
                    targetarea2 = targetarea1.getEst().getSpaceSecond();
                }
                break;

            case "south":
                if (attacker.getPosition().getSud().isWall()) {
                    throw new InvalidDirectionException("south");
                }
                targetarea1 = attacker.getPosition().getSud().getSpaceSecond();
                if (targetarea1.getSud().isWall()) {
                    targetarea2 = null;
                } else {
                    targetarea2 = targetarea1.getSud().getSpaceSecond();
                }
                break;

            case "west":
                if (attacker.getPosition().getOvest().isWall()) {
                    throw new InvalidDirectionException("west");
                }
                targetarea1 = attacker.getPosition().getOvest().getSpaceSecond();
                if (targetarea1.getOvest().isWall()) {
                    targetarea2 = null;
                } else {
                    targetarea2 = targetarea1.getOvest().getSpaceSecond();
                }
        }
    }
}
