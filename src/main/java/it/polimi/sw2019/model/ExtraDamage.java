package it.polimi.sw2019.model;



/**Class ExtraDamage: the power up card described in this class gives you the possibility to extra damage one of your enemies
 * @author Merita Mullameti
 */
public class ExtraDamage implements EffectBehaviour {

    @Override
    public void useEffect(Player target) {

        Player currentPlayer; //variable to memorise the player who´s currently playing
        currentPlayer=Table.getCurrentPlayer();

        target.getPlance().giveDamage(currentPlayer,1);

    }
}
