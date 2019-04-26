package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MoveDamage implements Power{
    //base effect for TractorBeam
    private Space targetspace = null; //save the position where the target is moved
    private Player target = null;

    @Override
    public void usePower(Player attacker){
        while (true){
            try {
                selectTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
            }catch (IllegalPlayerException e){
                System.out.println("You can't select yourself as a valid target\n");
            }catch (UnreachablePlayerException e){
                System.out.println(e.getMessage()+" can't be moved to a valid position, try with another player\n");
            }
        }
        target.getPlance().giveDamage(attacker, 1);
    }

    /**
     * This method acquire and check the validity of the target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of the target's nickname
     * @throws IllegalPlayerException caused by selecting the attacker as the target
     * @throws UnreachablePlayerException caused by moving the target to a square that isn't visible to the attacker
     */
    private void selectTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        Space originposition;
        String answer;
        String name;
        System.out.println("Insert the nickname of the player that you set as the target");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else {
            originposition = Table.getPlayers(i).getPosition();
            moveTarget(Table.getPlayers(i));
            Table.getPlayers(i).setPosition(targetspace);
            while (!Table.getPlayers(i).isVisible(attacker)) {
                Table.getPlayers(i).setPosition(originposition);
                System.out.println("Do you want to retry to move the target? [yes, no]");
                answer = scanner.nextLine();
                if (answer.equals("no")) {
                    throw new UnreachablePlayerException(name);
                } else {
                    moveTarget(Table.getPlayers(i));
                    Table.getPlayers(i).setPosition(targetspace);
                }
            }
            target = Table.getPlayers(i);
        }
    }

    /**
     * This method acquire, step by step, the newer target's position
     * @param selectedtarget identify the selected target
     */
    private void moveTarget(Player selectedtarget){
        Space actualposition = selectedtarget.getPosition();
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String dir;
        String[] possibledir = validMove(actualposition);
        while (i < 3){
            System.out.println("Insert a direction in which to move the target or press Enter to confirm the actual position\navailable direction are: "+ Arrays.toString(possibledir)+" remaining moves: "+(3 - i));
            dir = scanner.nextLine();
            if (dir.isEmpty()){
                targetspace = actualposition;
                return;
            }
            if (Arrays.asList(possibledir).contains(dir)){
                switch (dir){
                    case "north":
                        actualposition = actualposition.getNord().getSpaceSecond();
                        break;
                    case "west":
                        actualposition = actualposition.getOvest().getSpaceSecond();
                        break;
                    case "south":
                        actualposition = actualposition.getSud().getSpaceSecond();
                        break;
                    case "east":
                        actualposition = actualposition.getEst().getSpaceSecond();
                        break;
                    default:
                        break;
                }
            }else {
                System.out.println(dir+" isn't a valid direction, please insert a valid direction\n");
                i--;
            }
            possibledir = validMove(actualposition);
            i++;
        }
        targetspace = actualposition;
    }

    /**
     * This method identify the available movement that can be done
     * @param position identify the actual target's position
     * @return the list of the possible direction in which the target can be moved
     */
    private String[] validMove(Space position){
        ArrayList<String> direction = new ArrayList<>();
        if (!position.getNord().isWall()){
            direction.add("north");
        }
        if (!position.getOvest().isWall()){
            direction.add("west");
        }
        if (!position.getSud().isWall()){
            direction.add("south");
        }
        if (!position.getEst().isWall()){
            direction.add("east");
        }
        return direction.toArray(new String[0]);
    }
}
