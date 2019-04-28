package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.*;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the basic effect of Shotgun
 * @author Mattia Iamundo
 */
public class ThreeDamageMove implements Power{

    private Player target = null;

    @Override
    public void usePower(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String answer;
        while (true){
            try {
                acquireTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
            }catch (IllegalPlayerException e){
                System.out.println("You can't select yourself as the target\n");
            }catch (UnreachablePlayerException e){
                System.out.println("You can't select "+e.getMessage()+", he isn't on your square\n");
            }
        }
        target.getPlance().giveDamage(attacker, 3);
        System.out.println("Would you want to move the target? [yes, no]");
        answer = scanner.nextLine().toLowerCase();
        if (answer.equals("yes")){
            while (true){
                try {
                    moveTarget(target);
                    break;
                }catch (IllegalDirectionException e){
                    System.out.println(e.getMessage()+" is in front of a wall, you can't move the target in that direction\n");
                }catch (InvalidDirectionException e){
                    System.out.println(e.getMessage()+" isn't a cardinal direction\n");
                }
            }
        }
    }

    /**
     * This method acquire and check the validity of the target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of the player's nickname
     * @throws IllegalPlayerException caused by selecting as the target the attacker
     * @throws UnreachablePlayerException caused by selecting a player that isn't on the same square of the attacker
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException{
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of a player that is on your square");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (attacker.getPosition() != Table.getPlayers(i).getPosition()){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }

    /**
     * This method move the target, if the selected direction is a valid one
     * @param target identify the target of the attack
     * @throws InvalidDirectionException caused by a mistake in the insertion of the direction's name
     * @throws IllegalDirectionException caused by selecting a direction which is in front of a wall
     */
    private void moveTarget(Player target) throws InvalidDirectionException, IllegalDirectionException {
        Scanner scanner = new Scanner(System.in);
        String direction;
        System.out.println("Insert the direction in which you intend to move the target");
        direction = scanner.nextLine().toLowerCase();
        switch (direction){
            case "north":
                if (target.getPosition().getNorth().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    target.setPosition(target.getPosition().getNorth().getSpaceSecond());
                    break;
                }
            case "west":
                if (target.getPosition().getWest().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    target.setPosition(target.getPosition().getWest().getSpaceSecond());
                    break;
                }
            case "south":
                if (target.getPosition().getSouth().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    target.setPosition(target.getPosition().getSouth().getSpaceSecond());
                    break;
                }
            case "east":
                if (target.getPosition().getEast().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    target.setPosition(target.getPosition().getEast().getSpaceSecond());
                    break;
                }
            default:
                throw new InvalidDirectionException(direction);
        }
    }
}
