package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implement the basic effect of Machine Gun
 * @author Mattia Iamundo
 */
public class TwoTargetDamage implements Power{

    Player target1 = null; //save the first target
    Player target2 = null; //save the second target [optional]

    @Override
    public void usePower(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String answer;
        while (true){
            try {
                acquireFisrstTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
            }catch (IllegalPlayerException e){
                System.out.println("You can't select yourself as the target\n");
            }catch (UnreachablePlayerException e){
                System.out.println("You can't select "+e.getMessage()+", you can't see him\n");
            }
        }
        target1.getPlance().giveDamage(attacker, 1);
        if (isSecondAviable(attacker)) {
            System.out.println("Would you want to select a second target? [yes, no]");
            answer = scanner.nextLine().toLowerCase();
            if (answer.equals("yes")){
                while (true){
                    try {
                        acquireSecondTarget(attacker);
                        break;
                    }catch (InvalidPlayerException e){
                        System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
                    }catch (IllegalPlayerException e){
                        if (e.getCode() == ErrorCode.ATTACKERSELECTED){
                            System.out.println("You can't select yourself as the target\n");
                        }else {
                            System.out.println(e.getMessage()+" is already selected as your first target\n");
                        }
                    }catch (UnreachablePlayerException e){
                        System.out.println("You can't select "+e.getMessage()+", you can't see him\n");
                    }
                }
                target2.getPlance().giveDamage(attacker, 1);
            }else {
                target2 = null;
            }
        }else {
            target2 = null;
        }
    }

    /**
     * This method acquire and check the validity of the first target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of player's nickname
     * @throws IllegalPlayerException caused by selecting as target the attacker
     * @throws UnreachablePlayerException caused by selecting a player that the attacker can't see
     */
    private void acquireFisrstTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException{
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of a player that you can see");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (!Table.getPlayers(i).isVisible(attacker)){
            throw new UnreachablePlayerException(name);
        }else {
            target1 = Table.getPlayers(i);
        }
    }

    /**
     * This method acquire and check the validity of the second target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of player's nickname
     * @throws IllegalPlayerException caused by selecting as target the attacker or the first target
     * @throws UnreachablePlayerException caused by selecting a player that the attacker can't see
     */
    private void acquireSecondTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException{
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of the second target, you must see him");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (Table.getPlayers(i) == target1){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }else if (!Table.getPlayers(i).isVisible(attacker)){
            throw new UnreachablePlayerException(name);
        }else {
            target2 = Table.getPlayers(i);
        }
    }

    /**
     * This method check the availability of a second target
     * @param attacker identify the attacker
     * @return true if there is a player that the attacker can see and is not the first target, false otherwise
     */
    private boolean isSecondAviable(Player attacker){
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != attacker) && (Table.getPlayers(i) != target1) && (Table.getPlayers(i).isVisible(attacker))){
                return true;
            }
        }
        return false;
    }
}
