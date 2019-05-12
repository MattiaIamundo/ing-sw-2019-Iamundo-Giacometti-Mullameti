package it.polimi.sw2019.view;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.model.Table;

/**
 * this is the class view for the client
 * @author Luca Giacometti
 */
public class TableView extends ObservableByGame implements Observer <Table> {

    private Table table;

    /**
     * this method send to the NH the query to show the game's table
     * @param message
     */
    protected void showTable( Table message) {

        //it calls a NH methods to show to the client the Table
    }

    /**
     * this method show the update that one player did
     */
    public void update(Table message) {
        showTable( message);
    }
}
