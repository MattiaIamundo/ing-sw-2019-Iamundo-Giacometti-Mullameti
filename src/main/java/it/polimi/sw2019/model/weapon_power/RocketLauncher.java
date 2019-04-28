package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.*;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the basic effect of Rocket Launcher
 * @author Mattia Iamundo
 */
public class RocketLauncher implements Power{

    Player target;
    Space origin;

    @Override
    public void usePower(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String answer;
        while (true){
            try {
                acquireTarget(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+" isn't an existing player's nickname\n");
            }catch (IllegalPlayerException e){
                if (e.getCode() == ErrorCode.ATTACKERSELECTED){
                    System.out.println("You can't select yourself as the target\n");
                }else {
                    System.out.println(e.getMessage()+" is on your square, you can't select him as target\n");
                }
            }catch (UnreachablePlayerException e){
                System.out.println("You can't see "+e.getMessage()+", choose a player that you can see\n");
            }
        }
        target.getPlance().giveDamage(attacker, 2);
        System.out.println("Would you want to move the target? [yes, no]");
        answer = scanner.nextLine();
        if (answer.equals("yes")){
            while (true){
                try {
                    moveTarget();
                    break;
                }catch (InvalidDirectionException e){
                    System.out.println(e.getMessage()+" isn't a cardinal direction\n");
                }catch (IllegalDirectionException e){
                    System.out.println(e.getMessage()+" is in front of a wall, it isn't a valid direction\n");
                }
            }
        }
    }

    /**
     * This method acquire and check the validity of the target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException caused by a mistake in the insertion of player's nickname
     * @throws IllegalPlayerException caused by selecting as target the attacker or a player on the same square of the attacker
     * @throws UnreachablePlayerException caused by selecting a player that the attacker can't see
     */
    private void acquireTarget(Player attacker) throws InvalidPlayerException, IllegalPlayerException, UnreachablePlayerException {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String name;
        System.out.println("Insert the nickname of a player that you can see, but which isn't on your square");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }else if (attacker.getPosition() == Table.getPlayers(i).getPosition()){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }else if (!Table.getPlayers(i).isVisible(attacker)){
            throw new UnreachablePlayerException(name);
        }else {
            target = Table.getPlayers(i);
            origin = Table.getPlayers(i).getPosition();
        }
    }

    /**
     * This method acquire and check the validity of the direction in which the target will be moved
     * @throws InvalidDirectionException caused by a mistake in the insertion of direction's name
     * @throws IllegalDirectionException caused by selecting a direction that is in front of a wall
     */
    private void moveTarget() throws InvalidDirectionException, IllegalDirectionException{
        Scanner scanner = new Scanner(System.in);
        String direction;
        System.out.println("Insert the direction in which you intend to move the target");
        direction = scanner.nextLine();
        switch (direction){
            case "north":
                if (target.getPosition().getNorth().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    target.setPosition(target.getPosition().getNorth().getSpaceSecond());
                    break;
                }
            case "west":
                if (target.getPosition().getWest().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    target.setPosition(target.getPosition().getWest().getSpaceSecond());
                    break;
                }
            case "south":
                if (target.getPosition().getSouth().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    target.setPosition(target.getPosition().getSouth().getSpaceSecond());
                    break;
                }
            case "east":
                if (target.getPosition().getEast().isWall()){
                    throw new IllegalDirectionException(direction);
                }else {
                    target.setPosition(target.getPosition().getEast().getSpaceSecond());
                    break;
                }
            default:
                throw new InvalidDirectionException(direction);
        }
    }
}
