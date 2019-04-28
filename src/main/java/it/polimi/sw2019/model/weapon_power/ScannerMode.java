package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the alternative effect of Zx-2
 * @author Mattia Iamundo
 */
public class ScannerMode implements Power{

    private Player target1;
    private Player target2;
    private Player target3;

    @Override
    public void usePower(Player attacker){
        while (true){
            try {
                acquireFirstTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
            }catch (IllegalPlayerException e){
                System.out.println("You can't select yourself as the target\n");
            }catch (UnreachablePlayerException e){
                System.out.println("You can't see "+e.getMessage()+"\n");
            }
        }
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
                    System.out.println(e.getMessage()+" is already selected as target\n");
                }
            }catch (UnreachablePlayerException e){
                System.out.println("You can't see "+e.getMessage()+"\n");
            }
        }
        while (true){
            try {
                acquireThirdTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
            }catch (IllegalPlayerException e){
                if (e.getCode() == ErrorCode.ATTACKERSELECTED){
                    System.out.println("You can't select yourself as the target\n");
                }else {
                    System.out.println(e.getMessage()+" is already selected as target\n");
                }
            }catch (UnreachablePlayerException e){
                System.out.println("You can't see "+e.getMessage()+"\n");
            }
        }
        target1.getPlance().setMark(attacker);
        target2.getPlance().setMark(attacker);
        target3.getPlance().setMark(attacker);
    }

    /**
     * This method acquire and check the validity of the first target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of player's nickname
     * @throws IllegalPlayerException caused by selecting the attacker as target
     * @throws UnreachablePlayerException caused by selecting a player that the attacker can't see
     */
    private void acquireFirstTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException{
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of the first target, you must see it");
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
        System.out.println("Insert the nickname of the second target, you must see it");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (target1 == Table.getPlayers(i)){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }else if (!Table.getPlayers(i).isVisible(attacker)){
            throw new UnreachablePlayerException(name);
        }else {
            target2 = Table.getPlayers(i);
        }
    }

    /**
     * This method acquire and check the validity of the third target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of player's nickname
     * @throws IllegalPlayerException caused by selecting as target the attacker, the first or the second target
     * @throws UnreachablePlayerException caused by selecting a player that the attacker can't see
     */
    private void acquireThirdTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException{
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of the third target, you must see it");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if ((target1 == Table.getPlayers(i)) || (target2 == Table.getPlayers(i))){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }else if (!Table.getPlayers(i).isVisible(attacker)){
            throw new UnreachablePlayerException(name);
        }else {
            target3 = Table.getPlayers(i);
        }
    }
}
