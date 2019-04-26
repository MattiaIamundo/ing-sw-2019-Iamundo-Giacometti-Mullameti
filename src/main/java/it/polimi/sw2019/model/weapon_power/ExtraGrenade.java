package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class implements the optional effect of Grenade Launcher
 * @author Mattia Iamundo
 */
public class ExtraGrenade implements Power{

    private Space targetarea = null;
    private String thisroom = null;
    private String otherroom = null;
    private DamageMove basicpower;

    @Override
    public void usePower(Player attacker){
        initialize(attacker);
        targetarea = attacker.getPosition();
        acquireSpace();
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition() == targetarea){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 1);
            }
        }
        if (!basicpower.ismoved){
            basicpower.moveTarget();
        }
    }

    /**
     * This method initialize the needed attributes
     * @param attacker identify the attacker
     */
    private void initialize(Player attacker){
        int i = 0;
        thisroom = attacker.getPosition().getRoom();
        if (!attacker.getPosition().getNorth().getSpaceSecond().getRoom().equals(thisroom)){
            otherroom = attacker.getPosition().getNorth().getSpaceSecond().getRoom();
        }else if (!attacker.getPosition().getWest().getSpaceSecond().getRoom().equals(thisroom)){
            otherroom = attacker.getPosition().getWest().getSpaceSecond().getRoom();
        }else if (!attacker.getPosition().getSouth().getSpaceSecond().getRoom().equals(thisroom)){
            otherroom = attacker.getPosition().getSouth().getSpaceSecond().getRoom();
        }else if (!attacker.getPosition().getEast().getSpaceSecond().getRoom().equals(thisroom)){
            otherroom = attacker.getPosition().getEast().getSpaceSecond().getRoom();
        }else {
            otherroom = null;
        }
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("GrenadeLauncher"))){
            i++;
        }
        basicpower = (DamageMove) attacker.listWeapon()[i].getPower();
    }

    /**
     * This method acquire step by step the target area for the power
     */
    private void acquireSpace(){
        Scanner scanner = new Scanner(System.in);
        String[] validedirection;
        String dir;

        System.out.println("Insert a direction to identify the target area, to confirm the position just press enter.\nThe available direction are: "+Arrays.toString(valideDirection(targetarea)));
        dir = scanner.nextLine();
        do {
            validedirection = valideDirection(targetarea);
            try {
                checkSpace(dir, validedirection);
            }catch (InvalidDirectionException e){
                System.out.println(e.getMessage()+" isn't a valid direction, retry\n");
            }
            System.out.println("Insert a direction to identify the target area, to confirm the position just press enter.\nThe available direction are: "+Arrays.toString(validedirection));
            dir = scanner.nextLine();
        }while (!dir.isEmpty());
    }

    /**
     * This method identify the valid direction that the user can insert
     * @param position identify the actual position of the target area
     * @return the list of the possible direction
     */
    private String[] valideDirection(Space position){
        ArrayList<String> availabledirection = new ArrayList<>();
        if (!(position.getNorth().isWall()) && (position.getNorth().getSpaceSecond().getRoom().equals(thisroom) || position.getNorth().getSpaceSecond().getRoom().equals(otherroom))){
            availabledirection.add("north");
        }
        if (!(position.getWest().isWall()) && (position.getWest().getSpaceSecond().getRoom().equals(thisroom) || position.getWest().getSpaceSecond().getRoom().equals(otherroom))){
            availabledirection.add("west");
        }
        if (!(position.getSouth().isWall()) && (position.getSouth().getSpaceSecond().getRoom().equals(thisroom) || position.getSouth().getSpaceSecond().getRoom().equals(otherroom))){
            availabledirection.add("south");
        }
        if (!(position.getEast().isWall()) && (position.getEast().getSpaceSecond().getRoom().equals(thisroom) || position.getEast().getSpaceSecond().getRoom().equals(otherroom))){
            availabledirection.add("east");
        }
        return availabledirection.toArray(new String[0]);
    }

    /**
     * This method verify if the direction inserted is a valid one
     * @param inserted identify the inserted direction
     * @param available is the list of valid direction that can be selected
     * @throws InvalidDirectionException caused by inserting a direction that isn't in the "available" list
     */
    private void checkSpace(String inserted, String[] available) throws InvalidDirectionException {
        boolean result = Arrays.asList(available).contains(inserted);
        if (result){
            switch (inserted){
                case "north":
                    targetarea = targetarea.getNorth().getSpaceSecond();
                    break;
                case "west":
                    targetarea = targetarea.getWest().getSpaceSecond();
                    break;
                case "south":
                    targetarea = targetarea.getSouth().getSpaceSecond();
                    break;
                case "east":
                    targetarea = targetarea.getEast().getSpaceSecond();
                    break;
                default:
                    break;
            }
        }else {
            throw new InvalidDirectionException(inserted);
        }
    }
}
