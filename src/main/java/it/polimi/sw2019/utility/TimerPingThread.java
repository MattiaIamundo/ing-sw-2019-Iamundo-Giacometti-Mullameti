package it.polimi.sw2019.utility;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimerPingThread implements Runnable {

    private int timePing;
    private boolean isOn;
    private boolean timerPingDone;
    private Timer timer;
    private static final Logger logger = Logger.getLogger( TimerThread.class.getName() );

    public TimerPingThread() {
        this.timePing = 5000;
        this.isOn = false;
        this.timerPingDone = false;
    }

    public synchronized void deleteTimer() {
        timer.cancel();
        timer.purge();
    }

    public void setTimerPingDone(boolean status) {
        this.timerPingDone = status;
    }

    public boolean getTimePingDone() {
        return this.timerPingDone;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean status) {
        this.isOn = status;
    }

    @Override
    public void run() {
        timer = new Timer();

        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setTimerPingDone(true);
                logger.log(Level.INFO, "{TimerThread} timerDone: true ");
            }
        };

        setTimerPingDone(false);
        timer.schedule(task, timePing);
    }
}
