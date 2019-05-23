package it.polimi.sw2019.controller;

import it.polimi.sw2019.exception.IllegalDirectionException;
import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.exception.InvalidNrOfMoves;
import it.polimi.sw2019.model.*;


import java.util.Scanner;

/**
 * this class stands for the game's turn
 * @author Luca Giacometti
 */
public class Turn {

    private static Player player = null;
    private int usedAction;
    private Action action;

    /**
     * this is the constructor
     * @param usedAction how many actions he used during this turn
     * @param action the action on air
     */
    public Turn ( int usedAction, Action action){

        this.action = action;
        this.usedAction = usedAction;
    }
    public static void setPlayer(Player player1) {
        player = player1;
    }

    /**
     *
     * @return the player who is playing this turn
     */
    public static Player getPlayer() {
        return player;
    }

    public void useAction(Player player) throws InvalidDirectionException, IllegalDirectionException , InvalidNrOfMoves{
        Scanner scanner = new Scanner(System.in);
        int nrSpaces;
        String direction;
        System.out.println("It`s your turn now ! What do you wanna do ?");
        String action =scanner.nextLine();

        switch (action){
            case "move":{
                while(true) {
                    try {
                        System.out.println("How many spaces do you want to move?\n1 , 2 or 3 : ");
                        nrSpaces = scanner.nextInt();
                        if ((nrSpaces == 1) || (nrSpaces == 2) || (nrSpaces == 3)) {
                            for (int i = 0; i < nrSpaces; i++) {

                                Move.findDirection(getPlayer());
                            }
                        }else{
                            throw new InvalidNrOfMoves(nrSpaces);
                        }
                        break;
                    } catch(InvalidNrOfMoves e) {

                        System.out.println(e.getMessage() + "is an invalid input! \n");
                    }
                }

            }
            case "move and grab" :{
                String input;
                System.out.println("Do you want to move or just grab the ammo");
                input = scanner.nextLine();

                if(input=="move"){
                    Move.findDirection(player);
                    if(PlayerPlance.getSecondAdrenaline()){
                        System.out.println("Do you want to move again or grab the ammo? ");
                        input=scanner.nextLine();
                        if (input=="move") {
                            Move.findDirection(player);
                        }else if (input=="grab"){
                            Grab.useAction(player);
                        }
                    }

                }else if (input=="grab"){
                    Grab.useAction(player);
                }

            }
            case "shoot":{

                Shoot.useAction();

            }

        }

    }

    public void reload(Weapon weapon){

        if (weapon.getIsLoad() == true){
            System.out.println("The weapon is already load\n");
        }
        else{
            //String[3]
            //weapon.getCost()
            //Integer[3]
            //getPlayer().getAmmo()
        }
    }
}
