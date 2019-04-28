package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the basic effect of Shockwave
 * @author Mattia Iamundo
 */
public class Shockwave implements Power{

    private Player target1 = null;
    private Player target2 = null;
    private Player target3 = null;

    @Override
    public void usePower(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String answer;
        while (true){
            try {
                acquireFirstTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't an existing players nickname\n");
            }catch (IllegalPlayerException e){
                System.out.println("You can't select yourself as the target\n");
            }catch (UnreachablePlayerException e){
                System.out.println(e.getMessage()+" isn't exactly 1 move away from you\n");
            }
        }
        target1.getPlance().giveDamage(attacker, 1);
        System.out.println("Would you want to select a second target? [yes, no]");
        answer = scanner.nextLine();
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
                    }else if (e.getCode() == ErrorCode.NOTSELECTABLE){
                        System.out.println("You select "+e.getMessage()+" as your first target, you can't select him again\n");
                    }else {
                        System.out.println(e.getMessage()+" is on the same square of your first target, you can't select him as a valid target\n");
                    }
                }catch (UnreachablePlayerException e){
                    System.out.println(e.getMessage()+" isn't exactly 1 move away from you\n");
                }
            }
            target2.getPlance().giveDamage(attacker, 1);
            System.out.println("Would you want to select a third target? [yes, no]");
            answer = scanner.nextLine();
            if (answer.equals("yes")){
                while (true){
                    try {
                        acquireThirdTarget(attacker);
                        break;
                    }catch (InvalidPlayerException e){
                        System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
                    }catch (IllegalPlayerException e){
                        if (e.getCode() == ErrorCode.ATTACKERSELECTED){
                            System.out.println("You can't select yourself as the target\n");
                        }else if (e.getCode() == ErrorCode.NOTSELECTABLE){
                            if (target1.getNickname().equals(e.getMessage())){
                                System.out.println("You already select "+e.getMessage()+" as your first target, you can't select him again\n");
                            }else {
                                System.out.println("You already select "+e.getMessage()+" as your second target, you can't select him again\n");
                            }
                        }else if (e.getCode() == ErrorCode.SAMESQUARE){
                            System.out.println("You can't select "+e.getMessage()+" because he's on the same square of your first or second target\n");
                        }
                    }catch (UnreachablePlayerException e){
                        System.out.println(e.getMessage()+" isn't exactly 1 move away from you\n");
                    }
                }
                target3.getPlance().giveDamage(attacker, 1);
            }
        }
    }

    /**
     * This method acquire and check the validity of the first target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of the player's nickname
     * @throws IllegalPlayerException caused by selecting as target the attacker
     * @throws UnreachablePlayerException caused by selecting a player that isn't exactly 1 move away from the attacker
     */
    private void acquireFirstTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException{
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of the first target, it must be exactly 1 move away from you");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (isInvalidPosition(attacker, Table.getPlayers(i))){
            throw new UnreachablePlayerException(name);
        }else {
            target1 = Table.getPlayers(i);
        }
    }

    /**
     * This method acquire and check the validity of the second target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of the player's nickname
     * @throws IllegalPlayerException caused by selecting as target the attacker, or the first target, or a player on the same square of the first target
     * @throws UnreachablePlayerException caused by selecting a player that isn't exactly 1 move away from the attacker
     */
    private void acquireSecondTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException{
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of the second target, it must be exactly 1 move away from you and not on the same square of the first one");
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
        }else if (Table.getPlayers(i).getPosition() == target1.getPosition()){
            throw new IllegalPlayerException(name, ErrorCode.SAMESQUARE);
        }else if (isInvalidPosition(attacker, Table.getPlayers(i))){
            throw new UnreachablePlayerException(name);
        }else {
            target2 = Table.getPlayers(i);
        }
    }

    /**
     * This method acquire and check the validity of the third target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of the player's nickname
     * @throws IllegalPlayerException caused by selecting as target the attacker, or the first/second target or a player on the same square of first/second target
     * @throws UnreachablePlayerException caused by selecting a player that isn't exactly 1 move away from the attacker
     */
    private void acquireThirdTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException{
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the third target's nickname, it must be 1 move away from you and on a different square compared to the first and second target");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if ((Table.getPlayers(i) == target1) || (Table.getPlayers(i) == target2)){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }else if ((Table.getPlayers(i).getPosition() == target1.getPosition()) || (Table.getPlayers(i).getPosition() == target2.getPosition())){
            throw new IllegalPlayerException(name, ErrorCode.SAMESQUARE);
        }else if (isInvalidPosition(attacker, Table.getPlayers(i))){
            throw new UnreachablePlayerException(name);
        }else {
            target3 = Table.getPlayers(i);
        }
    }

    /**
     * This method check if a player is exactly 1 move away from the attacker
     * @param attacker identify the attacker
     * @param target identify the player, that maybe will be the target
     * @return false if the player is exactly 1 move away from the attacker, true otherwise
     */
    private boolean isInvalidPosition(Player attacker, Player target){
        if (!attacker.getPosition().getNorth().isWall() && target.getPosition() == attacker.getPosition().getNorth().getSpaceSecond()){
            return false;
        }
        if (!attacker.getPosition().getWest().isWall() && target.getPosition() == attacker.getPosition().getWest().getSpaceSecond()){
            return false;
        }
        if (!attacker.getPosition().getSouth().isWall() && target.getPosition() == attacker.getPosition().getSouth().getSpaceSecond()){
            return false;
        }
        if (!attacker.getPosition().getEast().isWall() && target.getPosition() == attacker.getPosition().getEast().getSpaceSecond()){
            return false;
        }
        return true;
    }
}
