package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.exception.IllegalDirectionException;
import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.model.*;
import java.util.Scanner;

public class MoveTakeFrenzy implements Action{

    private static Ammo ammo;
    private Space moveto;

    public static  void useAction(Player player) {
        Scanner scanner = new Scanner(System.in);
        int nrSpaces;
        String direction;
        while (true) {

            System.out.println("How many spaces do you want to move?\n1 or 2 ");
            nrSpaces = scanner.nextInt();
            if ((nrSpaces == 1) || (nrSpaces == 2)) {
                try{
                    for (int i = 0; i < nrSpaces; i++) {
                        System.out.println("Enter the direction you want to move to : ");
                        direction = scanner.nextLine();

                        Move.findDirection(player);
                    }

                    break;

                } catch (InvalidDirectionException e) {

                    System.out.println(e.getMessage() + "is an invalid direction! \n");

                } catch (IllegalDirectionException e) {

                    System.out.println(e.getMessage() + "is an invalid direction, there is a wall! \n");

                }

            }else{
                    //throw exception
            }
        }

        // Take the Ammo

        Ammo ammoCard = SpaceAmmo.takeAmmo();

        Player.addAmmo(ammoCard);

    }





    private boolean isBeforeFirstPlayer(){
        return true;
    }
}
