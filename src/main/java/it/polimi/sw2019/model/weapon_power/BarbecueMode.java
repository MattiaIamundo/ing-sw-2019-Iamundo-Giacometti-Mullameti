package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.IllegalDirectionException;
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
    private Player attacker;
    private Scanner scanner = new Scanner(System.in);


    /**
     * @param attacker the player who throws the attack
     */
    @Override
    public void usePower(Player attacker){
        this.attacker = attacker;
        System.out.println("Choose firing direction:");
        while (true) {
            try {
                findDirection(scanner.nextLine());
                break;
            } catch (InvalidDirectionException e) {
                System.out.println(e.getMessage() + "isn't a direction\n");
            }catch (IllegalDirectionException e){
                System.out.println(e.getMessage()+"isn't a valid direction, select a direction that isn't a wall\n");
            }
        }
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition() == targetarea1){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 2);
            }else if (Table.getPlayers(i).getPosition() == targetarea2){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 1);
            }
        }
    }

    /**
     * This method verify the validity of direction inserted by the attacker
     * @param dir identify the chosen firing direction
     * @throws InvalidDirectionException caused by a mistake in the direction name
     * @throws IllegalDirectionException caused by selecting a direction that is a wall
     */
    private void findDirection(String dir) throws InvalidDirectionException, IllegalDirectionException {
        switch (dir) {
            case "north":
                if (attacker.getPosition().getNord().isWall()) {
                    throw new IllegalDirectionException(dir);
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
                    throw new IllegalDirectionException(dir);
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
                    throw new IllegalDirectionException(dir);
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
                    throw new IllegalDirectionException(dir);
                }
                targetarea1 = attacker.getPosition().getOvest().getSpaceSecond();
                if (targetarea1.getOvest().isWall()) {
                    targetarea2 = null;
                } else {
                    targetarea2 = targetarea1.getOvest().getSpaceSecond();
                }
                break;
            default:
                throw new InvalidDirectionException(dir);
        }
    }
}
