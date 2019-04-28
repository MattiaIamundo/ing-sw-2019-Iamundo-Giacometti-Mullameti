package it.polimi.sw2019.view;

public class PlayerRemoteView extends PlayerView {

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

    protected void showPlayer() {

    }
}
