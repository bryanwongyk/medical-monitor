package scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The Scheduler class schedules a task to be run every N seconds at a given frequency.
 * Also allows certain actions to be executed immediately despite the update frequency.
 * @author Bryan
 */
public abstract class Scheduler {
    protected ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
    protected ScheduledFuture<?> futureTask;
    protected Runnable getObservation = null;
    protected Integer frequency = 60;// Default frequency set to 30 seconds
    
    /**
     * Initialises the Scheduler with a Runnable instance to be executed every initFrequency seconds.
     */
    public abstract void init();
    
    /**
     * Gets the frequency.
     * @return Integer - frequency in seconds
     */
    public Integer getFrequency(){
        return this.frequency;
    }
    
    /**
     * Changes the frequency of the getObservation task. If there is currently a scheduled task, this must be removed, and replaced with a new task with the new frequency.
     * @param updatedFrequency Integer - New frequency to update monitor data at.
     */
    public void updateFrequency(Integer updatedFrequency){      
        // 1. Cancel any currently running schedule
        if (hasFutureTask()){
            cancelFutureTask();
        }
        // 2. Set scheduler frequency to updated frequency
        this.frequency = updatedFrequency;
        // 3. Run new scheduled task with updated frequency
        this.futureTask = executor.scheduleAtFixedRate(this.getObservation, 0, this.frequency, TimeUnit.SECONDS);
    }
    
    /**
     * Cancel any currently scheduled task.
     */
    public void cancelFutureTask(){
        futureTask.cancel(true);
    }
    
    /**
     * Checks if there is currently a scheduled task (with a Runnable).
     * @return Boolean - Returns True if there is a scheduled task, returns False if there is no scheduled task.
     */
    public Boolean hasFutureTask(){
        return (futureTask != null);
    }
}
