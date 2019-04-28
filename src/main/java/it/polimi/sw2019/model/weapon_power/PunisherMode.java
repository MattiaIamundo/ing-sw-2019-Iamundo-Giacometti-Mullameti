package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.*;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class implements the alternative effect of Tractor Beam
 * @author Mattia Iamundo
 */
public class PunisherMode implements Power{

    private Player target;

    @Override
    public void usePower(Player attacker){
        while (true){
            try {
                acquireTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
            }catch (IllegalPlayerException e){
                System.out.println("You can't select yourself as a valid target\n");
            }catch (UnreachablePlayerException e){
                System.out.println("you can't select "+e.getMessage()+" because he isn't 0,1 or 2 moves away from you\n");
            }
        }
        target.setPosition(attacker.getPosition());
        target.getPlance().giveDamage(attacker, 3);
    }

    /**
     * This method acquire and check the validity of the target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of player's nickname
     * @throws IllegalPlayerException caused by selecting the attacker as the target
     * @throws UnreachablePlayerException caused by selecting a player that isn't 0,1 or 2 moves away from the attacker
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String[] validtargets = searchTargets(attacker);
        String name;
        System.out.println("Insert the nickname of a player that is 0, 1 or 2 moves away from you\n the valid targets are: "+ Arrays.toString(validtargets));
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (!Arrays.asList(validtargets).contains(name)){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
        }
    }

    /**
     * This method search for players who meet the requirements to be set as target
     * @param attacker identify the attacker
     * @return the list of the players that can be set as target
     */
    private String[] searchTargets(Player attacker){
        ArrayList<String> targets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition() == attacker.getPosition()){
                targets.add(Table.getPlayers(i).getNickname());
            }else if (isSearchPrimary(Table.getPlayers(i), attacker.getPosition())){
                targets.add(Table.getPlayers(i).getNickname());
            }else if (isSearchSecondary(Table.getPlayers(i), attacker.getPosition())){
                targets.add(Table.getPlayers(i).getNickname());
            }else if (isSearchTertiary(Table.getPlayers(i), attacker.getPosition())){
                targets.add(Table.getPlayers(i).getNickname());
            }
        }
        return targets.toArray(new String[0]);
    }

    /**
     * This method support searchTargets, check if a player is 1 move away on a cardinal direction
     * @param target identifies the player in exam
     * @param attckposition identify the position of the attacker
     * @return true if the player is 1 move away from the attacker, false otherwise
     */
    private boolean isSearchPrimary(Player target, Space attckposition){
        if (!attckposition.getNorth().isWall() && target.getPosition() == attckposition.getNorth().getSpaceSecond()){
            return true;
        }else if (!attckposition.getWest().isWall() && target.getPosition() == attckposition.getWest().getSpaceSecond()){
            return true;
        }else if (!attckposition.getSouth().isWall() && target.getPosition() == attckposition.getSouth().getSpaceSecond()){
            return true;
        }else if (!attckposition.getEast().isWall() && target.getPosition() == attckposition.getEast().getSpaceSecond()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * This method supports searchTargets, check if a player is 2 moves away not in the same cardinal direction
     * @param target identifies the player in exam
     * @param attckposition identify the attacker position
     * @return true if the player is 2 moves away from the attacker, false otherwise
     */
    private boolean isSearchSecondary(Player target, Space attckposition){
        if (!attckposition.getNorth().isWall()){
            if (!attckposition.getNorth().getSpaceSecond().getWest().isWall() && target.getPosition() == attckposition.getNorth().getSpaceSecond().getWest().getSpaceSecond()){
                return true;
            }else if (!attckposition.getNorth().getSpaceSecond().getEast().isWall() && target.getPosition() == attckposition.getNorth().getSpaceSecond().getEast().getSpaceSecond()){
                return true;
            }
        }
        if (!attckposition.getWest().isWall()){
            if (!attckposition.getWest().getSpaceSecond().getNorth().isWall() && target.getPosition() == attckposition.getWest().getSpaceSecond().getNorth().getSpaceSecond()){
                return true;
            }else if (!attckposition.getWest().getSpaceSecond().getSouth().isWall() && target.getPosition() == attckposition.getWest().getSpaceSecond().getSouth().getSpaceSecond()){
                return true;
            }
        }
        if (!attckposition.getSouth().isWall()){
            if (!attckposition.getSouth().getSpaceSecond().getWest().isWall() && target.getPosition() == attckposition.getSouth().getSpaceSecond().getWest().getSpaceSecond()){
                return true;
            }else if (!attckposition.getSouth().getSpaceSecond().getEast().isWall() && target.getPosition() == attckposition.getSouth().getSpaceSecond().getEast().getSpaceSecond()){
                return true;
            }
        }
        if (!attckposition.getEast().isWall()){
            if (!attckposition.getEast().getSpaceSecond().getSouth().isWall() && target.getPosition() == attckposition.getEast().getSpaceSecond().getSouth().getSpaceSecond()){
                return true;
            }else if (!attckposition.getEast().getSpaceSecond().getNorth().isWall() && target.getPosition() == attckposition.getEast().getSpaceSecond().getNorth().getSpaceSecond()){
                return true;
            }
        }
        return false;
    }

    /**
     * This method support searchTargets, check if a player is 2 moves away (along a cardinal direction) from the attacker
     * @param target identifies the player in exam
     * @param attckposition identify the attacker position
     * @return true if the player is 2 moves away, false otherwise
     */
    private boolean isSearchTertiary(Player target, Space attckposition){
        if (!attckposition.getNorth().isWall() && !attckposition.getNorth().getSpaceSecond().getNorth().isWall() && target.getPosition() == attckposition.getNorth().getSpaceSecond().getNorth().getSpaceSecond()){
            return true;
        }
        if (!attckposition.getWest().isWall() && !attckposition.getWest().getSpaceSecond().getWest().isWall() && target.getPosition() == attckposition.getWest().getSpaceSecond().getWest().getSpaceSecond()){
            return true;
        }
        if (!attckposition.getSouth().isWall() && !attckposition.getSouth().getSpaceSecond().getSouth().isWall() && target.getPosition() == attckposition.getSouth().getSpaceSecond().getSouth().getSpaceSecond()){
            return true;
        }
        if (!attckposition.getEast().isWall() && !attckposition.getEast().getSpaceSecond().getEast().isWall() && target.getPosition() == attckposition.getEast().getSpaceSecond().getEast().getSpaceSecond()){
            return true;
        }
        return false;
    }
}
