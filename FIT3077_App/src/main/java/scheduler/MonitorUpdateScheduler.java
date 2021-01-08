package scheduler;

import monitor_tasks.MonitorTasksManager;
import java.util.concurrent.TimeUnit;

/**
 * MonitorUpdateScheduler schedules an update for monitors to run every N seconds at a given frequency.
 * @author Bryan
 */

public class MonitorUpdateScheduler extends Scheduler {
    private MonitorTasksManager monitorTasksManager;
    
    /**
     * Constructor for MonitorUpdateScheduler.
     * @param monitorTasksManager MonitorTasksManager - Monitor task manager containing task to schedule to run every N seconds.
     */
    public MonitorUpdateScheduler(MonitorTasksManager monitorTasksManager) {
        this.monitorTasksManager = monitorTasksManager;
    }
    
    /**
     * Schedules a Runnable instance containing a function to run every N seconds at a given frequency.
     */
    public void init(){
        // Create Runnable instance to be executed
        this.getObservation = new Runnable() {
            @Override
            public void run() {
                try {
                    getMonitorTasksManager().updateAllMonitors();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // Schedule execution of Runnable instance 
        this.futureTask = executor.scheduleAtFixedRate(this.getObservation, 0, this.frequency, TimeUnit.SECONDS);
    }
    
    /**
     * Get monitorTasksManager attribute
     * @return MonitorTasksManager
     */
    public MonitorTasksManager getMonitorTasksManager(){
        return this.monitorTasksManager;
    }
}
