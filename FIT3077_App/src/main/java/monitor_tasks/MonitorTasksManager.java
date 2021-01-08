package monitor_tasks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import model.ObservationCodes;
import model.ObservationName;
import model.ObserveStorage;
import scheduler.MonitorUpdateScheduler;
import view.DashboardView;

/**
 * MonitorTasksManager holds the different monitor tasks available in the system, and the tasks to be executed are selected through this class at run-time.
 * @author Bryan
 */
public class MonitorTasksManager {
    private ObservationCodes observationCodes = new ObservationCodes();
    private ObservationName observationName;
    private ObserveStorage storage;
    private DashboardView view;
    private Map<String, MonitorTasks> monitorTasksMap = new ConcurrentHashMap();
    private MonitorUpdateScheduler scheduler;
    
    /**
     * Constructor for MonitorTaskManager.
     * @param storage ObserveStorage - stores all the monitors involved in the task execution.
     * @param view DashboardView - view component of the dashboard.
     */
    public MonitorTasksManager(ObserveStorage storage, DashboardView view) {
        this.storage = storage;
        this.view = view;
        this.scheduler = new MonitorUpdateScheduler(this);
        init();
    }
    
    /**
     * Initialises monitorTaskMap attribute with subclasses of MonitorTasks with their corresponding observation names as keys.
     */
    private void init(){
        getMonitorTasksMap().put(observationName.CHOLESTEROL.name(), new CholesterolMonitorTasks(getStorage(), this.view));
        getMonitorTasksMap().put(observationName.BLOOD.name(), new BloodMonitorTasks(getStorage(), this.view));
    }
    
    /**
     * Selects the tasks to execute based on the observation type at run-time.
     * @param observationCode String - Represents the observation code.
     * @return MonitorTasks - Represents the monitor tasks for the corresponding observation type.
     */
    private MonitorTasks selectMonitorType(String observationCode){
        if (observationCode.equals(getObservationCodes().getCode(observationName.CHOLESTEROL.name()))){
            return getMonitorTasksMap().get(observationName.CHOLESTEROL.name());
        }
        else if (observationCode.equals(getObservationCodes().getCode(observationName.BLOOD.name()))){
            return getMonitorTasksMap().get(observationName.BLOOD.name());
        }
        return null;
    }
    
    /**
     * Updates all monitors (i.e. executes the update monitor task for all observation types).
     */
    public void updateAllMonitors(){
        for (Map.Entry<String, MonitorTasks> o : getMonitorTasksMap().entrySet()) {
            o.getValue().updateMonitors();
        }
    }
    
    /**
     * Add a monitor for the corresponding observation.
     * @param key String - key to obtain the biometric.
     */
    public void addMonitor(String key){
        selectMonitorType(key.split(",")[0]).addMonitor(key);
    }
    
    /**
     * Update statistics for the corresponding observation.
     * @param observationCode String - observation code.
     */
    public void updateStatistics(String observationCode){
        selectMonitorType(observationCode).updateStatistics();
    }
    
    /**
     * Checks if the scheduler currently has a scheduled update.
     * @return Boolean - returns true if there is a scheduled update, returns false if not.
     */
    public Boolean hasScheduledUpdate(){
        return getScheduler().hasFutureTask();
    }
    
    /**
     * Initialises scheduler's scheduled updates.
     */
    public void initScheduledUpdates(){
        getScheduler().init();
    }
    
    /**
     * Cancels the scheduler's scheduled updates.
     */
    public void cancelScheduledUpdates(){
        getScheduler().cancelFutureTask();
    }
    
    /**
     * Updates the frequency of the scheduler's scheduled updates.
     * @param freq Integer - frequency to update the scheduler to.
     */
    public void updateFrequency(Integer freq){
        getScheduler().updateFrequency(freq);
    }
    
    /**
     * Get monitorTasksMap attribute.
     * @return Map<String, MonitorTasks>
     */
    public Map<String, MonitorTasks> getMonitorTasksMap(){
        return this.monitorTasksMap;
    }
    
    /**
     * Get observationCodes attribute.
     * @return ObservationCodes
     */
    public ObservationCodes getObservationCodes(){
        return this.observationCodes;
    }
    
    /**
     * Get storage attribute.
     * @return ObserveStorage
     */
    public ObserveStorage getStorage(){
        return this.storage;
    }
    
    /**
     * Get scheduler attribute.
     * @return MonitorUpdateScheduler
     */
    public MonitorUpdateScheduler getScheduler(){
        return this.scheduler;
    }
}
