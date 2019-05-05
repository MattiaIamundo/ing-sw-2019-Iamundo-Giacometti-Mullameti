package it.polimi.sw2019.model;

import it.polimi.sw2019.exception.IllegalDirectionException;
import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.exception.InvalidInputException;


import java.util.Scanner;
/**Class MoveEnemy: the power up card described in this class gives you the possibility to move your enemy from one space to another
 * @author Merita Mullameti
 */

public class MoveEnemy implements EffectBehaviour {

    private Space moveto; //variable dedicated to the space the player wants his enemy to be
    private Scanner scanner = new Scanner(System.in);

    /**
     * @param target the player  who is going to be attacked using this power up card
     */
    @Override
    public  void useEffect (Player target){

        while ( true ) {

            System.out.println("In what direction do you want" + target.getNickname() + "to move ?");
            String direction = scanner.nextLine();
            try {

                findDirection(target, direction);
                break;

            } catch (InvalidDirectionException e) {

                System.out.println(e.getMessage() + "is an invalid direction! \n");

            } catch (IllegalDirectionException e) {

                System.out.println(e.getMessage() + "is an invalid direction, there is a wall! \n");

            } catch (InvalidInputException e) {

                System.out.println(e.getMessage() + "is an invalid answer! Enter yes or no!  \n");
            }
        }

    }

    /**
     * @param target the player  who is going to be attacked using this power up card
     * @param direction the direction the target needs to be moved
     * @throws InvalidInputException exception due to not correct input
     * @throws InvalidDirectionException exception due to wrong spelling of the direction
     * @throws IllegalDirectionException exception due to the presence of wall in the required direction
     */

    private void findDirection (Player target , String direction) throws InvalidInputException,InvalidDirectionException, IllegalDirectionException {

        String nrSpaces;

        switch(direction){
            
            case "north":
                if(target.getPosition().getNorth().isWall()){
                    throw new IllegalDirectionException(direction);
                }
                else{

                    System.out.println("Do you want"+target.getNickname()+"to move north again ? \n Yes or No ? ");
                    nrSpaces=scanner.nextLine();

                    if(nrSpaces == "yes"||nrSpaces=="Yes"){

                        if((target.getPosition().getNorth().getSpaceSecond()).getNorth().isWall()) {

                            throw new IllegalDirectionException(direction);
                        }else{

                            moveto=(target.getPosition().getNorth().getSpaceSecond()).getNorth().getSpaceSecond();
                            target.setPosition(moveto);
                        }

                    }else if (nrSpaces=="no" ||nrSpaces=="No"){

                        moveto=target.getPosition().getNorth().getSpaceSecond();
                        target.setPosition(moveto);

                    }else{
                        throw new InvalidInputException(nrSpaces);
                    }
                }
                break;

            case "south":
                if(target.getPosition().getSouth().isWall()){
                    throw new IllegalDirectionException(direction);
                }else{
                    System.out.println("Do you want"+target.getNickname()+"to move south again ? \n Yes or No ? ");
                    nrSpaces=scanner.nextLine();

                    if(nrSpaces == "yes"||nrSpaces=="Yes"){

                        if((target.getPosition().getSouth().getSpaceSecond()).getSouth().isWall()) {

                            throw new IllegalDirectionException(direction);
                        }else{

                            moveto=(target.getPosition().getSouth().getSpaceSecond()).getSouth().getSpaceSecond();
                            target.setPosition(moveto);
                        }

                    }else if (nrSpaces=="no" ||nrSpaces=="No"){

                        moveto=target.getPosition().getSouth().getSpaceSecond();
                        target.setPosition(moveto);

                    }else{
                        throw new InvalidInputException(nrSpaces);
                    }
                }
                break;
            case "west":
                if(target.getPosition().getWest().isWall()){
                    throw new IllegalDirectionException(direction);
                }else{
                    System.out.println("Do you want"+target.getNickname()+"to move west again ? \n Yes or No ? ");
                    nrSpaces=scanner.nextLine();

                    if(nrSpaces == "yes"||nrSpaces=="Yes"){

                        if((target.getPosition().getWest().getSpaceSecond()).getWest().isWall()) {

                            throw new IllegalDirectionException(direction);
                        }else{

                            moveto=(target.getPosition().getWest().getSpaceSecond()).getWest().getSpaceSecond();
                            target.setPosition(moveto);
                        }

                    }else if (nrSpaces=="no" ||nrSpaces=="No"){

                        moveto=target.getPosition().getWest().getSpaceSecond();
                        target.setPosition(moveto);

                    }else{
                        throw new InvalidInputException(nrSpaces);
                    }
                }
                break;
            case "east":
                if(target.getPosition().getEast().isWall()){
                    throw new IllegalDirectionException(direction);
                }else{
                    System.out.println("Do you want"+target.getNickname()+"to move east again ? \n Yes or No ? ");
                    nrSpaces=scanner.nextLine();

                    if(nrSpaces == "yes"||nrSpaces=="Yes"){

                        if((target.getPosition().getEast().getSpaceSecond()).getEast().isWall()) {

                            throw new IllegalDirectionException(direction);
                        }else{

                            moveto=(target.getPosition().getEast().getSpaceSecond()).getEast().getSpaceSecond();
                            target.setPosition(moveto);
                        }

                    }else if (nrSpaces=="no" ||nrSpaces=="No"){

                        moveto=target.getPosition().getEast().getSpaceSecond();
                        target.setPosition(moveto);

                    }else{
                        throw new InvalidInputException(nrSpaces);
                    }
                }
                break;
            default:
                throw new InvalidDirectionException(direction);
        }

    }
}
