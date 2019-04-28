package it.polimi.sw2019.model;



/**Class Marked: the power up card described in this class gives you the possibility to give 1 mark to one of the players you see
 * @author Merita Mullameti
 */

public class Marked implements EffectBehaviour {

    /**
     * @param target the player  who is going to be attacked using this power up card
     */
    @Override
    public void useEffect(Player target) {

        Player currentPlayer; //variable to memorise the player who´s currently playing
        currentPlayer=Table.getCurrentPlayer();

        target.getPlance().setMark(currentPlayer);

    }
}