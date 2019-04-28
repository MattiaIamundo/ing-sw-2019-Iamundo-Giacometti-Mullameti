package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.*;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the alternative effect of PowerGlove
 * @author Mattia Iamundo
 */
public class RocketFistMode implements Power{

    private String direction = null;
    private Space targetarea = null;
    private Player target = null;

    @Override
    public void usePower(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String answer;
        while (true){
            try {
                acquireDirection(attacker.getPosition());
                break;
            }catch (InvalidDirectionException e){
                System.out.println(e.getMessage()+" isn't a cardinal direction\n");
            }catch (IllegalDirectionException e){
                System.out.println(e.getMessage()+" is in front of a wall\n");
            }
        }
        attacker.setPosition(targetarea);
        System.out.println("Would you want to hit a player? [yes, no]");
        answer = scanner.nextLine();
        if (answer.equals("yes")){
            while (true){
                try {
                    acquireTarget(attacker);
                    break;
                }catch (InvalidPlayerException e){
                    System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
                }catch (IllegalPlayerException e){
                    System.out.println("You can't select yourself as the target\n");
                }catch (UnreachablePlayerException e){
                    System.out.println(e.getMessage()+" isn't on your square\n");
                }
            }
            target.getPlance().giveDamage(attacker, 2);
        }
        System.out.println("Would you want to move 1 more square? [yes, no]");
        answer = scanner.nextLine();
        if (answer.equals("yes")){
            try {
                secondMove(direction);
            }catch (IllegalDirectionException e){
                System.out.println("Second movement isn't possible\n");
                return;
            }
            attacker.setPosition(targetarea);
            System.out.println("Would you want to hit a player? [yes, no]");
            answer = scanner.nextLine();
            if (answer.equals("yes")){
                while (true){
                    try {
                        acquireTarget(attacker);
                        break;
                    }catch (InvalidPlayerException e){
                        System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
                    }catch (IllegalPlayerException e){
                        System.out.println("You can't select yourself as the target\n");
                    }catch (UnreachablePlayerException e){
                        System.out.println(e.getMessage()+" isn't on your square\n");
                    }
                }
                target.getPlance().giveDamage(attacker, 2);
            }
        }
    }

    /**
     * This method acquire and check the validity of the target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of player's nickname
     * @throws IllegalPlayerException caused by selecting the attacker as the target
     * @throws UnreachablePlayerException caused by selecting a player that isn't on the same square of the target
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
        }else if (Table.getPlayers(i).getPosition() != attacker.getPosition()){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }

    /**
     * This method acquire and check the validity of the direction in which the attacker intend to move
     * @param attackerposition identify the position of the attacker
     * @throws IllegalDirectionException caused by selecting a direction that is in front of a wall
     * @throws InvalidDirectionException caused by a mistake in the insertion of direction's name
     */
    private void acquireDirection(Space attackerposition) throws IllegalDirectionException, InvalidDirectionException{
        Scanner scanner = new Scanner(System.in);
        String direction;
        System.out.println("Insert the direction in which you intend to move");
        direction = scanner.nextLine();
        switch (direction){
            case "north":
                if (attackerposition.getNorth().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    targetarea = attackerposition.getNorth().getSpaceSecond();
                    break;
                }
            case "west":
                if (attackerposition.getWest().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    targetarea = attackerposition.getWest().getSpaceSecond();
                    break;
                }
            case "south":
                if (attackerposition.getSouth().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    targetarea = attackerposition.getSouth().getSpaceSecond();
                    break;
                }
            case "east":
                if (attackerposition.getEast().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    targetarea = attackerposition.getEast().getSpaceSecond();
                    break;
                }
            default:
                throw new InvalidDirectionException(direction);
        }
        this.direction = direction;
    }

    /**
     * This method check the validity of a second move in the same direction of the first one
     * @param dir identify the first move direction
     * @throws IllegalDirectionException caused by a non valid movement, caused by the presence of a wall, in the previously selected direction
     */
    private void secondMove(String dir) throws IllegalDirectionException{
        switch (dir){
            case "north":
                if (targetarea.getNorth().isWall()){
                    throw new IllegalDirectionException();
                }else {
                    targetarea = targetarea.getNorth().getSpaceSecond();
                    break;
                }
            case "west":
                if (targetarea.getWest().isWall()){
                    throw new IllegalDirectionException(dir);
                }else {
                    targetarea = targetarea.getWest().getSpaceSecond();
                    break;
                }
            case "south":
                if (targetarea.getSouth().isWall()){
                    throw new IllegalDirectionException(dir);
                }else {
                    targetarea = targetarea.getSouth().getSpaceSecond();
                    break;
                }
            case "east":
                if (targetarea.getEast().isWall()){
                    throw new IllegalDirectionException(dir);
                }else {
                    targetarea = targetarea.getEast().getSpaceSecond();
                    break;
                }
        }
    }
}
