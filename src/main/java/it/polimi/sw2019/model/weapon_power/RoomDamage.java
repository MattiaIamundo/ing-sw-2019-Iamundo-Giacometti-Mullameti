package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class implement the basic effect of Furnace
 * @author Mattia Iamundo
 */
public class RoomDamage implements Power{

    private String room = null; //save the target room

    @Override
    public void usePower(Player attacker){
        acquireRoom(attacker);
        if (room == null){
            return;
        }
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition().getRoom().equals(room)){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 1);
            }
        }
    }

    /**
     * This method acquire the target room, if it's possible
     * @param attacker identify the attacker
     */
    private void acquireRoom(Player attacker){
        Scanner scanner = new Scanner(System.in);
        String roomname;
        String[] availablerooms;

        availablerooms = identifyRoom(attacker);
        if (availablerooms.length == 0){
            System.out.println("Operation not valid, there isn't an available room\n");
        }else {
            while (true) {
                System.out.println("Select one of this room as the target:\n" + Arrays.toString(availablerooms));
                roomname = scanner.nextLine();
                if (Arrays.asList(availablerooms).contains(roomname)) {
                    room = roomname;
                    break;
                }else {
                    System.out.println(roomname+" isn't an available room\n");
                }
            }
        }
    }

    /**
     * This method identify the rooms that can be set as target
     * @param attacker identify the attacker
     * @return the list of the valid room that can be set as target
     */
    private String[] identifyRoom(Player attacker){
        ArrayList<String> rooms = new ArrayList<>();
        String attackerroom = attacker.getPosition().getRoom();
        if (!attacker.getPosition().getNorth().getSpaceSecond().getRoom().equals(attackerroom)){
            rooms.add(attacker.getPosition().getNorth().getSpaceSecond().getRoom());
        }
        if (!attacker.getPosition().getWest().getSpaceSecond().getRoom().equals(attackerroom)){
            rooms.add(attacker.getPosition().getWest().getSpaceSecond().getRoom());
        }
        if (!attacker.getPosition().getSouth().getSpaceSecond().getRoom().equals(attackerroom)){
            rooms.add(attacker.getPosition().getSouth().getSpaceSecond().getRoom());
        }
        if (!attacker.getPosition().getEast().getSpaceSecond().getRoom().equals(attackerroom)){
            rooms.add(attacker.getPosition().getEast().getSpaceSecond().getRoom());
        }
        return rooms.toArray(new String[0]);
    }
}
