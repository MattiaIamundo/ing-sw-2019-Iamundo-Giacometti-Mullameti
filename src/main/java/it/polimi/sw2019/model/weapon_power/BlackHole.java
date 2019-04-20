package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.ErrorCode;
import it.polimi.sw2019.exception.IllegalPlayerException;
import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.exception.UnreachablePlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.Scanner;

/**
 * This class implements the optional effect for the Vortex Cannon
 * @author Mattia Iamundo
 */
public class BlackHole implements Power{

    private Player target1;
    private Player target2;

    private Vortex basicpower;
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void usePower(Player attacker){
        initializer(attacker);
        while (true){
            try {
                acquireFirst(attacker);
                acquireSecond(attacker);
                break;
            }catch (InvalidPlayerException e){
                System.out.println(e.getMessage()+"isn't a Player\n");
            }catch (UnreachablePlayerException e){
                System.out.println(e.getMessage()+"isn't in a reachable position\n");
            }catch (IllegalPlayerException e){
                if (e.getCode() == ErrorCode.ATTACKERSELECTED){
                    System.out.println("you can't select yourself as a valid target\n");
                }else if (e.getCode() == ErrorCode.NOTSELECTABLE){
                    System.out.println(e.getMessage()+"is already selected as target\n");
                }
            }
        }
        target1.setPosition(basicpower.vortex);
        target2.setPosition(basicpower.vortex);
        target1.getPlance().giveDamage(attacker, 1);
        target2.getPlance().giveDamage(attacker, 1);
    }

    /**
     * this method resume the needed information from the basic power of the weapon
     * @param attacker identify the player the use the weapon
     */
    private void initializer(Player attacker){
         int i = 0;
         while ((i < 4) && (!attacker.listWeapon()[i].getName().equals("VortexCannon"))){
             i++;
         }
         basicpower = (Vortex) attacker.listWeapon()[i].getPower();
    }

    /**
     * this class acquire the first target and verify if it's a valid target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException identify a non existing Player
     * @throws UnreachablePlayerException identify a Player that isn't in the range of the power
     * @throws IllegalPlayerException identify a non valid target (e.g. the attacker or a Player already classified as a target)
     */
    private void acquireFirst(Player attacker) throws InvalidPlayerException, UnreachablePlayerException, IllegalPlayerException {
        String name;
        int i = 0;
        System.out.println("Indicates the name of the first target, it must be at most 1 square away from the Vortex:\n");
        name = scanner.nextLine();
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (basicpower.target.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        }else if (attacker.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }
        if (isValidPosition(i)){
            target1 = Table.getPlayers(i);
        }else {
            throw new UnreachablePlayerException(name);
        }
    }

    /**
     * this class acquire the second target and verify if it's a valid target
     * @param attacker identify the attacker
     * @throws InvalidPlayerException identify a non existing Player
     * @throws UnreachablePlayerException identify a Player that isn't in the range of the power
     * @throws IllegalPlayerException identify a non valid target (e.g. the attacker or a Player already classified as a target)
     */
    private void acquireSecond(Player attacker) throws InvalidPlayerException, UnreachablePlayerException, IllegalPlayerException {
        String name;
        int i = 0;
        System.out.println("Indicates the name of the second target, it must be at most 1 square away from the Vortex\n");
        name = scanner.nextLine();
        if (name.isEmpty()){
            target2 = null;
        }
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(name))){
            i++;
        }
        if (i == 5){
            throw new InvalidPlayerException(name);
        }else if (basicpower.target.getNickname().equals(name)){
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        } else if (target1.getNickname().equals(name)) {
            throw new IllegalPlayerException(name, ErrorCode.NOTSELECTABLE);
        } else if (attacker.getNickname().equals(name)) {
            throw new IllegalPlayerException(name, ErrorCode.ATTACKERSELECTED);
        }
        if (isValidPosition(i)){
            target2 = Table.getPlayers(i);
        }else {
            throw new UnreachablePlayerException(name);
        }
    }

    /**
     * this method check if the designated target is in the range of the power
     * @param target identify the designated target
     * @return true if the target is in the range, false otherwise
     */
    private boolean isValidPosition(int target){
        if (Table.getPlayers(target).getPosition() == basicpower.vortex){
            return true;
        }else if (Table.getPlayers(target).getPosition() == basicpower.vortex.getNord().getSpaceSecond()){
            return true;
        }else if (Table.getPlayers(target).getPosition() == basicpower.vortex.getNord().getSpaceSecond().getOvest().getSpaceSecond()){
            return true;
        }else if (Table.getPlayers(target).getPosition() == basicpower.vortex.getOvest().getSpaceSecond()){
            return true;
        }else if (Table.getPlayers(target).getPosition() == basicpower.vortex.getOvest().getSpaceSecond().getSud().getSpaceSecond()){
            return true;
        }else if (Table.getPlayers(target).getPosition() == basicpower.vortex.getSud().getSpaceSecond()){
            return true;
        }else if (Table.getPlayers(target).getPosition() == basicpower.vortex.getSud().getSpaceSecond().getEst().getSpaceSecond()){
            return true;
        }else if (Table.getPlayers(target).getPosition() == basicpower.vortex.getEst().getSpaceSecond()){
            return true;
        }else if (Table.getPlayers(target).getPosition() == basicpower.vortex.getEst().getSpaceSecond().getNord().getSpaceSecond()){
            return true;
        }else {
            return false;
        }
    }
}
