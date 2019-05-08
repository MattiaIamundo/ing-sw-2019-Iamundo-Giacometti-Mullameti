package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Table;

/**
 * @author Luca Giacometti
 */
public abstract class TableView extends ObservableByGame implements Observer <Table> {

    //private Table table;

    protected abstract void showTable();

    /**
     * this method show the update that one player did
     */
    public void update(Table message) {
        showTable();
    }
}
