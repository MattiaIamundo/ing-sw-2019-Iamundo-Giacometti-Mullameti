package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class implements the first optional effect of Plasma Gun
 * @author Mattia Iamundo
 */
public class PhaseGlide implements Power{

    @Override
    public void usePower(Player attacker){
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String dir;
        String[] validdirection;

        while (i < 2){
            validdirection = validDirection(attacker.getPosition());
            System.out.println("Insert a direction, to confirm the actual position just press enter\navailable direction: "+ Arrays.toString(validdirection)+" remaining moves: "+(2 - i));
            dir = scanner.nextLine();
            if (dir.isEmpty()){
                break;
            }
            if (Arrays.asList(validdirection).contains(dir)){
                switch (dir){
                    case "north":
                        attacker.setPosition(attacker.getPosition().getNord().getSpaceSecond());
                        break;
                    case "west":
                        attacker.setPosition(attacker.getPosition().getOvest().getSpaceSecond());
                        break;
                    case "south":
                        attacker.setPosition(attacker.getPosition().getSud().getSpaceSecond());
                        break;
                    case "east":
                        attacker.setPosition(attacker.getPosition().getEst().getSpaceSecond());
                        break;
                    default:
                        break;
                }
                i++;
            }else {
                System.out.println(dir+" isn't a valid direction\n");
            }
        }
    }

    /**
     * This method verify which directions are selectable
     * @param position identify the actual player position
     * @return the list of the available direction
     */
    private String[] validDirection(Space position){
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
