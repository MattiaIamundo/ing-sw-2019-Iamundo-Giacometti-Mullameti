package it.polimi.sw2019.model;

import it.polimi.sw2019.exception.InvalidSpaceException;

import java.util.Scanner;
/**Class Teleport: the power up card described in this class gives you the possibility to move from one space to another
 * @author Merita Mullameti
 */
public class Teleport implements EffectBehaviour {
    private Space moveto = null;  //variable dedicated to the space the player wants to go

    /**
     * @param target the player  who is going to be attacked using this power up card
     */

    @Override
    public  void useEffect(Player target) {

        Scanner scanner = new Scanner(System.in);

        while (true){

            try {
                System.out.println("Where do you want to go ?");
                System.out.println("Enter the coordinate X : ");
                int i = scanner.nextInt(); //abscissa
                System.out.println("Enter the coordinate Y : ");
                int j = scanner.nextInt(); //ordinate
                moveto = Table.getMap().getSpace(i, j);
                break;

            }catch(InvalidSpaceException e){

                switch (e.getMessage()) {
                    case "xout":
                        System.out.println("The x-coordinate is out of the boundaries");
                        break;
                    case "yout":
                        System.out.println("The y-coordinate is out of the boundaries");
                        break;
                    case "xyout":
                        System.out.println("The x-coordinate and y-coordinate are out of the boundaries");
                        break;
                    case "neg":
                        System.out.println("One or both coordinate are negatives");
                        break;
                }
            }
        }

        target.setPosition(moveto);
    }
}
