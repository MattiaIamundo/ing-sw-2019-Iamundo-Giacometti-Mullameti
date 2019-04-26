package it.polimi.sw2019.model;

import java.util.Scanner;
/**Class Teleport: the power up card described in this class gives you the possibility to move from one space to another
 * @author Merita Mullameti
 */
public class Teleport implements EffectBehaviour {
    private Space moveto;  //variable dedicated to the space the player wants to go
    /**Constructor of the class
     */
    public Teleport(){
        this.moveto= null;
    }


    @Override
    public  void useEffect(Player target) {

        Scanner scanner=new Scanner(System.in);

        System.out.println("Where do you want to go ?");
        System.out.println("Enter the coordinate X : ");
        Integer i = scanner.nextInt(); //abscissa
        System.out.println("Enter the coordinate Y : ");
        Integer j = scanner.nextInt(); //ordinate

        moveto = Map.getSpace(i, j);
        target.setPosition(moveto);
    }
}
