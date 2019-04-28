package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

public class TableRemoteView extends TableView{

    private class MessageReceiver implements Observer <String> {
        /**
         * this method implements the message that the server received
         * @param message the object which is updated
         */
        public void update(String message) {

            System.out.println("Ricevuto" + message);
            //here:     what the controller has to do with this command
        }

    }


    ///**
    // * this is the constructor
    // * @param player is the player who want to be interface with the table
    // */
    //public TableRemoteView (Player player) {
    //super(player);
        // also a connection with the client which to be interface with the server
    //}

    protected void showTable() {

        //i have to send to the connection the table
        //Table.clone();
    }
}
