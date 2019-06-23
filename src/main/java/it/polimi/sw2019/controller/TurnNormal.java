package it.polimi.sw2019.controller;

import it.polimi.sw2019.exception.IllegalDirectionException;
import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.exception.InvalidNrOfMoves;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.PlayerPlance;
import it.polimi.sw2019.model.Weapon;

import java.util.Scanner;

public class TurnNormal extends Turn {

    /**
     * this is the constructor
     *
     * @param player     who is playing
     * @param usedAction how many actions he used during this turn
     * @param action     the action on air
     */
    public TurnNormal(Player player, Integer usedAction, Action action) {
        super(player, usedAction, action);
    }

    public void useAction(Player player) throws InvalidDirectionException, IllegalDirectionException, InvalidNrOfMoves {
        /*
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

                Shoot.useAction(player);

            }

        }
*/
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
