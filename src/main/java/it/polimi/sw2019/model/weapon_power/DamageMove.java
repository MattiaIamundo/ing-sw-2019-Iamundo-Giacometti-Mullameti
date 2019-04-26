package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.*;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the basic effect for GrenadeLauncher
 * @author Mattia Iamundo
 */
public class DamageMove implements Power{

    Player target = null;
    boolean ismoved = false;
    private Space moveto = null;

    @Override
    public void usePower(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String answer;
        while (true){
            try {
                acquireTarget(attacker);
                break;
            }catch (IllegalPlayerException e){
                System.out.println("You can't select yourself as a valid target\n");
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+"isn't a player\n");
            }catch (UnreachablePlayerException e){
                System.out.println(e.getMessage()+"you can't see this player, please select a player that you can see\n");
            }
        }
        target.getPlance().giveDamage(attacker, 1);
        System.out.println("you want to move "+target+", yes or no?\n");
        answer = scanner.nextLine();
        if (answer.equals("yes")){
            ismoved = true;
            moveTarget();
        }else {
            ismoved = false;
            moveto = null;
        }
    }

    /**
     * This method give the possibility to the attacker to move his target 1 square away
     */
    void moveTarget(){
        while (true){
            try {
                selectSpace(target);
                break;
            }catch (IllegalDirectionException e){
                System.out.println(e.getMessage()+"isn't a valid direction, please select a direction that isn't in front of a wall\n");
            }catch (InvalidDirectionException e){
                System.out.println(e.getMessage()+"isn't a direction\n");
            }
        }
        target.setPosition(moveto);
    }

    /**
     * This method implements the acquisition of the target and check his validity
     * @param attacker identify the attacker
     * @throws UnreachablePlayerException caused by selection a target that isn't in the field of view of the attacker
     * @throws InvalidPlayerException caused by a mistake un the name of the player
     * @throws IllegalPlayerException caused by selecting the attacker as the target
     */
    private void acquireTarget(Player attacker) throws UnreachablePlayerException, InvalidPlayerException, IllegalPlayerException {
        Scanner scanner = new Scanner(System.in);
        String name;
        int i = 0;
        System.out.println("Select the target\n");
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
            target = Table.getPlayers(i);
        }
    }

    /**
     * This method implement the acquisition and validation of the space where target will be moved
     * @param target identify the target of the power, previously selected
     * @throws InvalidDirectionException caused by inserting a non existing direction
     * @throws IllegalDirectionException caused by selecting a direction that is ahead of a wall
     */
    private void selectSpace(Player target) throws InvalidDirectionException, IllegalDirectionException{
        Scanner scanner = new Scanner(System.in);
        String dir;
        System.out.println("Insert the direction in which you intend to move your target, [north, west, south, east]\n");
        dir = scanner.nextLine();
        switch (dir){
            case "north":
                if (target.getPosition().getNord().isWall()){
                    throw new IllegalDirectionException(dir);
                }else {
                    moveto = target.getPosition().getNord().getSpaceSecond();
                    break;
                }
            case "west":
                if (target.getPosition().getOvest().isWall()){
                    throw new IllegalDirectionException(dir);
                }else {
                    moveto = target.getPosition().getOvest().getSpaceSecond();
                    break;
                }
            case "south":
                if (target.getPosition().getSud().isWall()){
                    throw new IllegalDirectionException(dir);
                }else {
                    moveto = target.getPosition().getSud().getSpaceSecond();
                    break;
                }
            case "east":
                if (target.getPosition().getEst().isWall()){
                    throw new IllegalDirectionException(dir);
                }else {
                    moveto = target.getPosition().getEst().getSpaceSecond();
                    break;
                }
            default:
                throw new InvalidDirectionException(dir);
        }
    }
}
