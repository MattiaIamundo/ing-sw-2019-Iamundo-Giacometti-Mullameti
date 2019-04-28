package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.*;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the alternative effect of Sledgehammer
 * @author Mattia Iamundo
 */
public class PulverizeMode implements Power{

    private Player target = null;

    @Override
    public void usePower(Player attacker){
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
        target.getPlance().giveDamage(attacker, 3);
        while (true){
            try {
                moveTarget(target);
                break;
            }catch (InvalidDirectionException e){
                System.out.println(e.getMessage()+" isn't a cardinal direction\n");
            }catch (IllegalDirectionException e){
                System.out.println("You can't move the target on "+e.getMessage()+", it's in front of a wall\n");
            }
        }
    }

    /**
     * This method acquire and check the validity of the target
     * @param attacker identify the target
     * @throws InvalidPlayerException caused by a mistake in the player's nickname insertion
     * @throws IllegalPlayerException caused by selecting the attacker as the target
     * @throws UnreachablePlayerException caused by selecting a player that isn't on the same square of the attacker
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
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
     * This method manage the possibility to move the target after the attack
     * @param target identify the target
     * @throws InvalidDirectionException caused by a mistake in the name of the direction
     * @throws IllegalDirectionException caused by selecting a direction that doesn't permit any movement
     */
    private void moveTarget(Player target) throws InvalidDirectionException, IllegalDirectionException {
        Scanner scanner = new Scanner(System.in);
        String answer;
        int move = 0;
        System.out.println("If you want to move the target insert the direction, otherwise press enter:");
        answer = scanner.nextLine();
        if (answer.isEmpty()){
            return;
        }else {
            switch (answer){
                case "north":
                    if (target.getPosition().getNorth().isWall()){
                        throw new IllegalDirectionException(answer);
                    }else if (target.getPosition().getNorth().getSpaceSecond().getNorth().isWall()){
                        target.setPosition(target.getPosition().getNorth().getSpaceSecond());
                        return;
                    }else {
                        System.out.println("would you want move the target 1 or 2 square away?\n");
                        move = scanner.nextInt();
                        if (move == 1){
                            target.setPosition(target.getPosition().getNorth().getSpaceSecond());
                        }else {
                            target.setPosition(target.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond());
                        }
                        return;
                    }
                case "west":
                    if (target.getPosition().getWest().isWall()){
                        throw new IllegalDirectionException(answer);
                    }else if (target.getPosition().getWest().getSpaceSecond().getWest().isWall()){
                        target.setPosition(target.getPosition().getWest().getSpaceSecond());
                        return;
                    }else {
                        System.out.println("would you want move the target 1 or 2 square away?\n");
                        move = scanner.nextInt();
                        if (move == 1){
                            target.setPosition(target.getPosition().getWest().getSpaceSecond());
                        }else {
                            target.setPosition(target.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond());
                        }
                        return;
                    }
                case "south":
                    if (target.getPosition().getSouth().isWall()){
                        throw new IllegalDirectionException(answer);
                    }else if (target.getPosition().getSouth().getSpaceSecond().getSouth().isWall()){
                        target.setPosition(target.getPosition().getSouth().getSpaceSecond());
                        return;
                    }else {
                        System.out.println("would you want move the target 1 or 2 square away?\n");
                        move = scanner.nextInt();
                        if (move == 1){
                            target.setPosition(target.getPosition().getSouth().getSpaceSecond());
                        }else {
                            target.setPosition(target.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond());
                        }
                        return;
                    }
                case "east":
                    if (target.getPosition().getEast().isWall()){
                        throw new IllegalDirectionException(answer);
                    }else if (target.getPosition().getEast().getSpaceSecond().getEast().isWall()){
                        target.setPosition(target.getPosition().getEast().getSpaceSecond());
                        return;
                    }else {
                        System.out.println("would you want move the target 1 or 2 square away?\n");
                        move = scanner.nextInt();
                        if (move == 1){
                            target.setPosition(target.getPosition().getEast().getSpaceSecond());
                        }else {
                            target.setPosition(target.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond());
                        }
                        return;
                    }
                default:
                    throw new InvalidDirectionException(answer);
            }
        }
    }
}
