package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Ammo;

public class AmmoRemoteView extends AmmoView {


    private class MessageReceiver implements Observer <String> {

        /**
         * this method implements the message that the server received
         * @param message the object which is updated
         */
        public void update(String message) {

            //here:     what the controller has to do with this command
        }

    }


    public static void showAmmo (Ammo ammo) {
        //it has to pass to the NH the ammo by the network
        //ammo has to be serialised

    }

    public void notify (Ammo ammo) {
        //it has to pass to the NH the ammo by the network
    }
}
