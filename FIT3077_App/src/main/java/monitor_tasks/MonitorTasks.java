package monitor_tasks;

import model.ObservationName;
import model.ObserveStorage;
import requests.RequestHandler;
import view.DashboardView;

/**
 * MonitorTasks is an abstract class representing the tasks monitors execute such as updating monitors, adding monitors, or updating statistics.
 * @author Bryan
 */
public abstract class MonitorTasks {
    protected ObserveStorage storage;
    protected DashboardView view;
    protected RequestHandler requestHandler = new RequestHandler();
    protected ObservationName observationName;
    
    /**
     * Constructor for MonitorTasks.
     * @param storage ObserveStorage - contains storage of monitors.
     * @param view DashboardView - view component of dashboard.
     */
    public MonitorTasks(ObserveStorage storage, DashboardView view){
        this.storage = storage;
        this.view = view;
    }
    
    /**
     * Updates monitors to get latest information from the FHIR server.
     */
    public abstract void updateMonitors();
    
    /**
     * Adds a monitor to be stored without interrupting any existing scheduled updates.
     * @param key String - key of the observation to be stored.
     */
    public abstract void addMonitor(String key);
    
    /**
     * Updates any statistics maintained in the monitors.
     */
    public abstract void updateStatistics();
}
