package controller;

import model.DashboardModel;
import model.ObservationCodes;
import model.ObservationName;
import model.ObserveStorage;
import monitor_tasks.MonitorTasksManager;
import view.DashboardView;

/**
 * BiometricMonitorActions is an abstract class detailing the actions that users can take when directly interacting with monitors in the Dashboard view component.
 * @author Bryan
 */
public abstract class BiometricMonitorActions {
    protected DashboardView view;
    protected DashboardModel model;
    protected ObserveStorage storage;
    protected ObservationCodes observationCodes = new ObservationCodes();
    protected ObservationName observationName;
    protected MonitorTasksManager monitorTasksManager;
    
    /**
     * Constructor for BiometricMonitorActions
     * @param view The dashboard view component.
     * @param model The dashboard model component.
     * @param storage The storage holding biometric data.
     * @param monitorTasksManager The object handling execution of monitor tasks not directly input by users e.g. scheduling updates.
     */
    public BiometricMonitorActions(DashboardView view, DashboardModel model, ObserveStorage storage, MonitorTasksManager monitorTasksManager){
        this.view = view;
        this.model = model;
        this.storage = storage;
        this.monitorTasksManager = monitorTasksManager;
    }
    
    /**
     * Displays the selected patient's details such as their ID, birth-date, gender, and address in the dashboard view.
     */
    public abstract void showPatientDetailsAction();
    
    /**
     * Tracks the observations of the selected patient.
     * @param observationCode The observation code stored in the FHIR server.
     * @param observationName The name e.g. CHOLESTEROL
     */
    public abstract void trackAction (String observationCode, String observationName);
    
    /**
     * Removes a selected monitor.
     */
    public abstract void untrackAction ();
    
    /**
     * Gets view attribute.
     * @return DashboardView
     */
    public DashboardView getView(){
        return this.view;
    }
    
    /**
     * Gets model attribute.
     * @return DashboardModel
     */
    public DashboardModel getModel(){
        return this.model;
    }
    
    /**
     * Gets storage attribute.
     * @return ObserveStorage
     */
    public ObserveStorage getStorage(){
        return this.storage;
    }
    
    /**
     * Get monitorTasksManager attribute.
     * @return MonitorTasksManager
     */
    public MonitorTasksManager getMonitorTasksManager(){
        return this.monitorTasksManager;
    }
    
    /**
     * Gets observationCodes attribute.
     * @return ObservationCodes
     */
    public ObservationCodes getObservationCodes(){
        return this.observationCodes;
    }
}