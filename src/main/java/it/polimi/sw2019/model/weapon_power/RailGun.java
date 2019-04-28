package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.*;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class implements the basic effect of Railgun
 * @author Mattia Iamundo
 */
public class RailGun implements Power{

    private Space[] fireline = null;
    private Player target = null;

    @Override
    public void usePower(Player attacker){
        while (true){
            try {
                fireline = loadFireLine(attacker);
                break;
            }catch (IllegalDirectionException e){
                System.out.println(e.getMessage()+" isn't a valid direction, there isn't any player in this direction\n");
            }
        }
        while (true){
            try {
                acquireTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
            }catch (IllegalPlayerException e){
                System.out.println("You can't select yourself as the target\n");
            }catch (UnreachablePlayerException e){
                System.out.println(e.getMessage()+" isn't on your firing line\n");
            }
        }
        target.getPlance().giveDamage(attacker, 3);
    }

    /**
     * This method acquire and check the validity of the target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of the player's nickname
     * @throws IllegalPlayerException caused by selecting the attacker as the target
     * @throws UnreachablePlayerException caused by selecting as target a player that isn't on the selected line of fire
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of a player that is on your firing line");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (!Arrays.asList(fireline).contains(Table.getPlayers(i).getPosition())){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }

    /**
     * This method load the squares that compose the line of fire
     * @param attacker identify the attacker
     * @return the list of the valid positions on which the target can be selected
     * @throws IllegalDirectionException caused by selecting a line of fire without any player to select
     */
    private Space[] loadFireLine(Player attacker) throws IllegalDirectionException{
        ArrayList<Space> fireline = new ArrayList<>();
        String direction;
        Space position = attacker.getPosition();
        while (true){
            try {
                direction = acquireDirection(attacker);
                break;
            }catch (InvalidDirectionException e){
                System.out.println(e.getMessage()+" isn't a cardinal direction\n");
            }catch (IllegalDirectionException e){
                System.out.println(e.getMessage()+" isn't a valid direction, you are at the end of the map, choose another direction\n");
            }
        }
        while (position != null){
            switch (direction){
                case "north":
                    fireline.add(position);
                    position = position.getNorth().getSpaceSecond();
                    break;
                case "west":
                    fireline.add(position);
                    position = position.getWest().getSpaceSecond();
                    break;
                case "south":
                    fireline.add(position);
                    position = position.getSouth().getSpaceSecond();
                    break;
                case "east":
                    fireline.add(position);
                    position = position.getEast().getSpaceSecond();
                    break;
            }
        }
        for (int i = 0; i < 5; i++) {
            if ((fireline.contains(Table.getPlayers(i).getPosition())) && (Table.getPlayers(i) != attacker)){
                return fireline.toArray(new Space[0]);
            }
        }
        throw new IllegalDirectionException(direction);
    }

    /**
     * This method acquire and check the validity of the direction of the line of fire
     * @param attacker identify the attacker
     * @return the selected direction of fire
     * @throws InvalidDirectionException caused by a mistake in the insertion of the direction's name
     * @throws IllegalDirectionException caused by selecting a direction that doesn't permit to acquire any square (e.g. when you are on a border of the map)
     */
    private String acquireDirection(Player attacker) throws InvalidDirectionException, IllegalDirectionException {
        Scanner scanner = new Scanner(System.in);
        String dir;
        System.out.println("Insert the direction of fire");
        dir = scanner.nextLine();
        switch (dir){
            case "north":
                if (attacker.getPosition().getNorth().getSpaceSecond() == null){
                    throw new IllegalDirectionException(dir);
                }else {
                    return dir;
                }
            case "west":
                if (attacker.getPosition().getWest().getSpaceSecond() == null){
                    throw new IllegalDirectionException(dir);
                }else {
                    return dir;
                }
            case "south":
                if (attacker.getPosition().getSouth().getSpaceSecond() == null){
                    throw new IllegalDirectionException(dir);
                }else {
                    return dir;
                }
            case "east":
                if (attacker.getPosition().getEast().getSpaceSecond() == null){
                    throw new IllegalDirectionException(dir);
                }else {
                    return dir;
                }
            default:
                throw new InvalidDirectionException(dir);
        }
    }
}
