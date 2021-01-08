package controller;

import java.util.Map;
import model.Biometric;
import model.DashboardModel;
import model.ObserveStorage;
import monitor_tasks.MonitorTasksManager;
import view.BiometricTextMonitor;
import view.DashboardView;

/**
 * CholesterolMonitorActions details the actions that users can take when directly interacting with cholesterol monitors in the Dashboard view component.
 * @author Bryan
 */
public class CholesterolMonitorActions extends BiometricMonitorActions {
    
    /**
     * Constructor for CholesterolMonitorActions
     * @param view The dashboard view component.
     * @param model The dashboard model component.
     * @param storage The storage holding biometric data.
     * @param monitorTasksManager The object handling execution of monitor tasks not directly input by users e.g. scheduling updates.
     */
    public CholesterolMonitorActions(DashboardView view, DashboardModel model, ObserveStorage storage, MonitorTasksManager monitorTasksManager) {
        super(view, model, storage, monitorTasksManager);
    }

    @Override
    public void showPatientDetailsAction() {
        // Check if a row is actually selected. If row == -1, then there is no row selected.
        Integer row = getView().getCholesterolTableSelectedRow();
        if (row != -1){
            // Get the ID of the selected patient
            String patientId = getView().getValueFromCholesterolTable(row, 0);
            // Get patient details from DashboardModel.
            Map<String,String> patientDetails = getModel().getPatientDetails(patientId);
            getView().setPatientDetailsText(
                    "Patient ID: " + patientId + "\n" 
                    + "Birthdate: " + patientDetails.get("birthDate") + "\n" 
                    + "Gender: " + patientDetails.get("gender") + "\n" 
                    + "Address: " + patientDetails.get("address")
            );
        }
    }

    @Override
    public void trackAction(String observationCode, String observationName) {
        try {
            // Reset error message text-area (if there is any text).
            getView().setPatientErrorText("");
            
            // Check if a row is actually selected. If row == -1, then there is no row selected.
            Integer row = getView().getPatientTableSelectedRow();
            if (row != -1){
                String selectedPatientId = getView().getValueFromPatientTable(row, 0);
                String selectedGivenName = getView().getValueFromPatientTable(row, 1);
                String selectedFamilyName = getView().getValueFromPatientTable(row, 2);
                String key = observationCode + "," + selectedPatientId; 
                
                // Check if a cholesterol observation exists for the patient                  
                Boolean hasCholesterol = getModel().patientHasBiometric(observationCode, selectedPatientId);
                if (hasCholesterol) {
                    // Check if the monitor already exists
                    if (!getStorage().hasCholesterolBiometric(key)){
                        // Create biometric subject and monitor only with patient details (no measurements yet).
                        Biometric biometric = new Biometric(observationCode, selectedPatientId, selectedGivenName, selectedFamilyName);
                        BiometricTextMonitor textMonitor = this.view.getCholesterolTextMonitor();
                        biometric.attach(textMonitor);
                        textMonitor.setObservation(biometric);
                        
                        getStorage().storeCholesterolBiometric(observationCode + "," + selectedPatientId, biometric);
                        // Create blank monitor row in table in view with only patient name
                        getView().addMonitorTemplate(observationName, selectedGivenName + selectedFamilyName, selectedPatientId);
                        
                        // If this is the only monitor in the table, initialise the default scheduler which will fill the monitor with measurement data.
                        // This also updates any associated graphs.
                        if (!getMonitorTasksManager().hasScheduledUpdate()){
                            getMonitorTasksManager().initScheduledUpdates();
                        }
                        // If there is already at least one other existing monitor, then do not reset the scheduler but simply add this monitor and fill in its data. 
                        else {
                            getMonitorTasksManager().addMonitor(key);
                        }
                        
                        // If there is now more than one cholesterol observation, update the cholesterol average value
                        if (getStorage().numCholesterol() > 1){
                            getMonitorTasksManager().updateStatistics(observationCode);
                        };
                        // If this is the first monitor, show the update frequency button in view
                        if (getStorage().numCholesterol() == 1){
                            getView().showUpdateOptions();
                        }
                    }
                }
                // Set error message for user
                else {
                    getView().setPatientErrorText("ERROR: Selected patient does not have that measurement.");
                }
            }
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void untrackAction() {
        // Check if a row is actually selected. If row == -1, then there is no row selected.
        Integer row = getView().getCholesterolTableSelectedRow();
        if (row != -1){
            // Get the patient ID from the selected row.
            Integer patientIdRow = 0;
            String patientId = getView().getValueFromCholesterolTable(row, patientIdRow);
            // Delete row from table
            getView().removeCholesterolTableRow(row);
            // Remove observation and observer
            String code = getObservationCodes().getCode(observationName.CHOLESTEROL.name());
            getStorage().removeCholesterolBiometric(code + "," + patientId);
            if (!getStorage().hasMonitors()){
                if (getMonitorTasksManager().hasScheduledUpdate()) {
                    getMonitorTasksManager().cancelScheduledUpdates();
                }
                getView().hideUpdateOptions();
            }
            // Else, update the average calculation for all remaining monitors.
            else {
                getMonitorTasksManager().updateStatistics(code);
            }
        }
        view.updateCholesterolGraphMonitor();
    }
    
}
