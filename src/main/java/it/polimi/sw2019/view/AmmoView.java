package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.model.AmmoDoublePowerUp;
import it.polimi.sw2019.model.AmmoTriple;

public class AmmoView extends ObservableByGame implements Observer <Ammo>{

    private Ammo ammo;
    private UIinterface ui;

    public AmmoView() {}
    public AmmoView(UIinterface userImp) {
        ui = userImp;
    }

    /**
     * this method show to the player the ammo on one particular space
     * @param ammo the ammo to be show
     */
    public void showAmmo( Ammo ammo ) {

        if (ammo instanceof AmmoTriple) {

            System.out.println( "This is the ammo:\n");
            System.out.println( "First color: " + ammo.getColorFirst() + "\n");
            System.out.println( "Second color: " + ammo.getColorSecond() + "\n");
            System.out.println( "Third color: " + ((AmmoTriple) ammo).getColorThird() + "\n");
        }
        else if (ammo instanceof AmmoDoublePowerUp) {

            System.out.println( "This is the ammo:\n");
            System.out.println( "First color: " + ammo.getColorFirst() + "\n");
            System.out.println( "Second color: " + ammo.getColorSecond() + "\n");
            System.out.println( "Power up name: " + ((AmmoDoublePowerUp) ammo).getPowerUp().getName() + "\n");
            System.out.println( "Power up color: " + ((AmmoDoublePowerUp) ammo).getPowerUp().getColor() + "\n");
        }
    }

    /**
     * when there is an update of an ammo, this method show the change
     * to the player
     * @param message the object which have to be updated
     */
    public void update(Ammo message) {

        System.out.println( "There is an update!\n");
        showAmmo( message );
    }

}
