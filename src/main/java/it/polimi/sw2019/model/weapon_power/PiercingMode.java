package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.*;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class implements the alternative effect of Railgun
 * @author Mattia Iamundo
 */
public class PiercingMode implements Power{

    private Space[] fireline = new Space[0];
    private Player target1 = null;
    private Player target2 = null;

    @Override
    public void usePower(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String answer;
        while (true) {
            try {
                acquireFiringLine(attacker);
                break;
            }catch (IllegalDirectionException e){
                System.out.println("There isn't players on the line of fire that you chose, please choose a different direction\n");
            }
        }
        while (true){
            try {
                acquireFirstTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't a player's nickname\n");
            }catch (IllegalPlayerException e){
                System.out.println("You can't select yourself as the target\n");
            }catch (UnreachablePlayerException e){
                System.out.println(e.getMessage()+" isn't on your line of fire, you can't select him as the target\n");
            }
        }
        if (listTarget().length > 1){
            System.out.println("Do you want to choose a second target? [yes, no]");
            answer = scanner.nextLine();
            if (answer.equals("yes")){
                while (true) {
                    try {
                        acquireSecondTarget(attacker);
                        break;
                    }catch (InvalidPlayerException e){
                        System.out.println(e.getMessage()+" isn't a player's nickname\n");
                    }catch (IllegalPlayerException e){
                        if (e.getCode() == ErrorCode.ATTACKERSELECTED){
                            System.out.println("You can't select yourself as the target\n");
                        }else {
                            System.out.println(e.getMessage()+" is already set as the first target, choose another player\n");
                        }
                    }catch (UnreachablePlayerException e){
                        System.out.println(e.getMessage()+" isn't on your line of fire, you can't select him as the target\n");
                    }
                }
            }
        }
        target1.getPlance().giveDamage(attacker, 2);
        if (target2 != null){
            target2.getPlance().giveDamage(attacker, 2);
        }
    }

    /**
     * This method acquire and check the validity of the first target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in player's nickname insertion
     * @throws IllegalPlayerException caused by selecting the attacker as the target
     * @throws UnreachablePlayerException caused by selecting as target a player that isn't on the line of fire of the attacker
     */
    private void acquireFirstTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        String[] players = listTarget();
        System.out.println("Insert the nickname of the first target, the available target are: "+Arrays.toString(players));
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (!Arrays.asList(players).contains(name)){
            throw new UnreachablePlayerException(name);
        }else {
            target1 = Table.getPlayers(i);
        }
    }

    /**
     * This method acquire and check the validity of the second target
     * @param attacker identify the attacker
     * @throws IllegalPlayerException caused by selecting as target the attacker or the first target of the power
     * @throws InvalidPlayerException caused by a mistake in the insertion of the player's nickname
     * @throws UnreachablePlayerException caused by selecting a player that isn't on the chosen line of fire
     */
    private void acquireSecondTarget(Player attacker) throws IllegalPlayerException, InvalidPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        String[] players = listTarget();
        System.out.println("Insert the nickname of the second target, the available target are: "+Arrays.toString(players));
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (!Arrays.asList(players).contains(name)){
            throw new UnreachablePlayerException(name);
        }else if (target1.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        } else {
            target2 = Table.getPlayers(i);
        }
    }

    /**
     * This method check if a player is on the line of fire chosen by the attacker
     * @return the list of the player that can be set as targets
     */
    private String[] listTarget(){
        ArrayList<String> player = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < fireline.length; j++) {
                if (Table.getPlayers(i).getPosition() == fireline[j]){
                    player.add(Table.getPlayers(i).getNickname());
                }
            }
        }
        return player.toArray(new String[0]);
    }

    /**
     * This method acquire and check the validity of the direction that represent the line of fire
     * @param attacker identify the attacker
     * @throws IllegalDirectionException caused by selecting a direction that doesn't contains any player
     */
    private void acquireFiringLine(Player attacker) throws IllegalDirectionException{
        Scanner scanner = new Scanner(System.in);
        ArrayList<Space> firingline = new ArrayList<>();
        Space position = attacker.getPosition();
        String dir;
        String[] availabledir = checkDirection(attacker.getPosition());

        while (true){
            System.out.println("Insert a firing direction, available: "+ Arrays.toString(availabledir));
            dir = scanner.nextLine();
            if (Arrays.asList(availabledir).contains(dir)){
                break;
            }else {
                System.out.println(dir+" isn't a valid firing direction, chose another one\n");
            }
        }
        switch (dir){
            case "north":
                while (position.getNord().getSpaceSecond() != null){
                    firingline.add(position);
                    position = position.getNord().getSpaceSecond();
                }
                break;
            case "west":
                while (position.getOvest().getSpaceSecond() != null){
                    firingline.add(position);
                    position = position.getOvest().getSpaceSecond();
                }
                break;
            case "south":
                while (position.getSud().getSpaceSecond() != null){
                    firingline.add(position);
                    position = position.getSud().getSpaceSecond();
                }
                break;
            case "east":
                while (position.getEst().getSpaceSecond() != null){
                    firingline.add(position);
                    position = position.getEst().getSpaceSecond();
                }
                break;
            default:
                break;
        }
        fireline = firingline.toArray(fireline);
        if (listTarget().length == 0){
            throw new IllegalDirectionException();
        }
    }

    /**
     * This method verify which direction can be selected to be the line of fire of the power
     * @param position identify the actual position of the attacker
     * @return the list of the valid directions that can be choose
     */
    private String[] checkDirection(Space position){
        ArrayList<String> direction = new ArrayList<>();

        if (position.getNord().getSpaceSecond() != null){
            direction.add("north");
        }
        if (position.getOvest().getSpaceSecond() != null){
            direction.add("west");
        }
        if (position.getSud().getSpaceSecond() != null){
            direction.add("south");
        }
        if (position.getEst().getSpaceSecond() != null){
            direction.add("east");
        }
        return direction.toArray(new String[0]);
    }
}
