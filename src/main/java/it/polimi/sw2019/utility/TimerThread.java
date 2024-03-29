package it.polimi.sw2019.utility;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimerThread implements Runnable {

    //almost this is the time to attend the right players number
    private int time;
    //almost this is the time for the player to play his turn
    private int turnTime;
    //the game is started?
    private boolean game;
    //to set the timer
    private Timer timer;
    //to set that the timer has completed his task
    private boolean timerDone;
    //to set that the timer is on
    private boolean on;
    //the logger
    private static final Logger logger = Logger.getLogger( TimerThread.class.getName() );

    // ........ TimerThread's Methods ...........

    /**
     * this is the constructor
     */
    public TimerThread() {
        time = 0;
        turnTime = 30000;
        game = false;
        timerDone = false;
        on = false;
    }

    /**
     * set the time to attend the other players
     * @param seconds the time to wait
     */
    public synchronized void setTime(int seconds) {

        this.time = seconds;
        System.out.println(time);
    }

    /**
     * set the condition the game is started
     */
    public synchronized void setGame() {
        this.game = true;
    }

    /**
     * set the player's turn time
     * @param secondsToTurn the turn time
     */
    public synchronized void setTurnTime(int secondsToTurn) {
        this.turnTime = secondsToTurn;
    }

    /**
     * @return true if the timer is done
     */
    public synchronized boolean getTimerDone() {
        return this.timerDone;
    }

    /**
     * @param status set the status of the timer
     */
    public synchronized void setTimerDone(boolean status) {
        this.timerDone = status;
    }

    /**
     * this cancel the timer
     */
    public synchronized void deleteTimer() {
        timer.cancel();
        timer.purge();
    }

    /**
     * set is the timer is on
     * @param status the status of the timer
     */
    public synchronized void setOn(boolean status) {
        on = status;
    }

    /**
     * @return the status of the timer
     */
    public synchronized boolean getOn() {
        return on;
    }

    /**
     *
     * @return the milliseconds for play a turn in the game
     */
    public synchronized int getTurnTime() {
        return turnTime;
    }


    /**
     * start the timer and schedule a task to set true the attribute timerDone
     */
    public void run() {

        timer = new Timer();
        //initializing the task for the timer
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //set true the condition to go out the loop before the fifth player go inside the game
                //inside this class
                setTimerDone(true);
                //go out the loop
                logger.log(Level.INFO, "{TimerThread} timerDone: true ");
            }
        };
            //the server is accepting the clients
            setTimerDone(false);
            setOn(true);
            if (!game) {
                //the time to accept other two players
                logger.log(Level.INFO, "timer schedule task with time");
                timer.schedule(task, time);
            }
            //the game is started
            else {
                //the time which every player has to play his turn
                logger.log(Level.INFO, "timer schedule task with timeTurn");
                timer.schedule(task, turnTime);
            }
    }
}
