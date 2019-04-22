package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.*;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the basic effect of Flamethrower
 * @author Mattia Iamundo
 */
public class Flamethrower implements Power{

    private Player target1 = null;
    private Player target2 = null;
    private Space targetarea1 = null;
    private Space targetarea2 = null;
    private int spacenumber;

    @Override
    public void usePower(Player attacker){
        acquireDirection(attacker);
        if (spacenumber == 1){
            acquireTargetFirst(attacker);
            target1.getPlance().giveDamage(attacker, 1);
        }else {
            acquireTargetFirst(attacker);
            acquireTargetSecond(attacker);
            target1.getPlance().giveDamage(attacker, 1);
            target2.getPlance().giveDamage(attacker, 1);
        }
    }

    /**
     * This method acquire the fire direction
     * @param attacker identify the attacker
     */
    private void acquireDirection(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String dir;
        System.out.println("Do you want to attack 1 or 2 space, [1, 2]");
        spacenumber = scanner.nextInt();
        while (true){
            try {
                System.out.println("Indicates the firing direction, [north, west, south, east]");
                dir = scanner.nextLine();
                checkDirection(attacker, dir);
                break;
            }catch (IllegalDirectionException e){
                System.out.println(e.getMessage()+" is ahead a wall, it can't be selected as a valid direction\n");
            }catch (InvalidDirectionException e){
                System.out.println(e.getMessage()+" isn't a direction\n");
            }
        }
    }

    /**
     * This method verify the validity of the selected firing direction
     * @param attacker identify the attacker
     * @param direction identify the selected direction
     * @throws InvalidDirectionException caused by a mistake in the direction name
     * @throws IllegalDirectionException caused by selecting a direction that is in front of a wall
     */
    private void checkDirection(Player attacker, String direction) throws InvalidDirectionException, IllegalDirectionException{
        switch (direction){
            case "north":
                if (attacker.getPosition().getNord().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    targetarea1 = attacker.getPosition().getNord().getSpaceSecond();
                    if (targetarea1.getNord().isWall()){
                        targetarea2 = null;
                    }else {
                        targetarea2 = targetarea1.getNord().getSpaceSecond();
                    }
                    break;
                }
            case "west":
                if (attacker.getPosition().getOvest().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    targetarea1 = attacker.getPosition().getOvest().getSpaceSecond();
                    if (targetarea1.getOvest().isWall()){
                        targetarea2 = null;
                    }else {
                        targetarea2 = targetarea1.getOvest().getSpaceSecond();
                    }
                    break;
                }
            case "south":
                if (attacker.getPosition().getSud().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    targetarea1 = attacker.getPosition().getSud().getSpaceSecond();
                    if (targetarea1.getSud().isWall()){
                        targetarea2 = null;
                    }else {
                        targetarea2 = targetarea1.getSud().getSpaceSecond();
                    }
                    break;
                }
            case "east":
                if (attacker.getPosition().getEst().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    targetarea1 = attacker.getPosition().getEst().getSpaceSecond();
                    if (targetarea1.getEst().isWall()){
                        targetarea2 = null;
                    }else {
                        targetarea2 = targetarea1.getEst().getSpaceSecond();
                    }
                    break;
                }
            default:
                throw new InvalidDirectionException(direction);
        }
    }

    /**
     * This method acquire the name of the target on the first target area
     * @param attacker identify the attacker
     */
    private void acquireTargetFirst(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String name;
        while (true){
            System.out.println("Insert the name of a player on the first target area");
            name = scanner.nextLine();
            try {
                target1 = verifyTargetValidity(attacker, name, targetarea1);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't a player's nickname\n");
            }catch (IllegalPlayerException e){
                System.out.println("You can't select yourself as a target\n");
            }catch (UnreachablePlayerException e){
                System.out.println(e.getMessage()+" isn't on the first target area\n");
            }
        }
    }

    /**
     * This method acquire the target on the second target area, if this option is taken
     * @param attacker identify the attacker
     */
    private void acquireTargetSecond(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String name;
        while (true){
            System.out.println("Insert the name of a player on the second target area\n");
            name = scanner.nextLine();
            try {
                target2 = verifyTargetValidity(attacker, name, targetarea2);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't a player's nickname\n");
            }catch (IllegalPlayerException e){
                System.out.println("You can't select yourself as a valid target\n");
            }catch (UnreachablePlayerException e){
                System.out.println(e.getMessage()+" isn't on the second target area\n");
            }
        }
    }

    /**
     * This method verify the validity of the selected player
     * @param attacker identify the attacker
     * @param name identify the nickname of the selected target
     * @param area identify the target area on which the target must be
     * @return the chosen player if it's a valid choice
     * @throws InvalidPlayerException caused by a mistake in the player's nickname
     * @throws IllegalPlayerException caused by selecting the attacker as the target
     * @throws UnreachablePlayerException caused by selecting a player that isn't on the right target area
     */
    private Player verifyTargetValidity(Player attacker, String name, Space area) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
        int i = 0;
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (Table.getPlayers(i).getNickname().equals(attacker.getNickname())){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (Table.getPlayers(i).getPosition() != area){
            throw new UnreachablePlayerException(name);
        }
        return Table.getPlayers(i);
    }
}
