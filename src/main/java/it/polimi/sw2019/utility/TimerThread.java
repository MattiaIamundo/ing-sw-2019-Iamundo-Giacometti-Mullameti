package it.polimi.sw2019.utility;

import it.polimi.sw2019.network.Socket.ServerSocket;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import static it.polimi.sw2019.network.Socket.ServerSocket.getPlayers;

public class TimerThread implements Runnable {

    //almost this is the time to attend the right players number
    private int time = 10000;
    //almost this is the time for the player to play his turn
    private int turnTime = 60000;
    //the game is started?
    private boolean game = false;
    //to set the tasks
    private Timer timer = new Timer();
    //the logger
    private static final Logger logger = Logger.getLogger(TimerThread.class.getName());

    //initializing a task for the timer
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            //set true the condition to go out the loop before the fifth player go inside the game
            System.out.println("inside the timer\n");

            if ( getPlayers().size() < 3 ) {
                //there are not enough players, restart the timer
                System.out.println("timer: restart timer\n");
                timer.schedule(this, time);
            }
            else {
                //go out the loop
                System.out.println("timer: out = true\n");
                ServerSocket.setOut();
            }

        }
    };

    //initializing the task for the turn timer
    private TimerTask turnTask = new TimerTask() {
        @Override
        public void run() {
            //here something to communicate to the server the player has finished the turn time
        }
    };

    /**
     * set the time to attend the other players
     *
     * @param seconds the time to wait
     */
    public void setSeconds(int seconds) {
        this.time = seconds;
    }

    /**
     * set the condition the game is started
     */
    public void setGame() {
        this.game = true;
    }

    /**
     * set the player's turn time
     *
     * @param secondsToTurn the turn time
     */
    public void setTurnTime(int secondsToTurn) {
        this.turnTime = secondsToTurn;
    }

    /**
     * start the timer thread
     */
    public void run() {

            //the server accept the clients
            if (!game) {
                System.out.println("timer schedule task\n");
                timer.schedule(task, time);
            }
            //the game is started
            else {
                //the time which every player has to play his turn
                System.out.println("timer schedule turnTime\n");
                timer.schedule(turnTask, turnTime);
            }
    }

    public void deleteTask () {
        task.cancel();
    }

    public void deleteTurnTask () {
        turnTask.cancel();
    }
}
