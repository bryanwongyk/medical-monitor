package controller;

import java.util.Map;
import model.Biometric;
import model.DashboardModel;
import model.ObserveStorage;
import monitor_tasks.MonitorTasksManager;
import view.BiometricTextMonitor;
import view.DashboardView;

/**
 * BloodMonitorActions details the actions that users can take when directly interacting with blood pressure monitors in the Dashboard view component.
 * @author Bryan
 */
public class BloodMonitorActions extends BiometricMonitorActions {
    
    /**
     * Constructor for BloodMonitorActions
     * @param view The dashboard view component.
     * @param model The dashboard model component.
     * @param storage The storage holding biometric data.
     * @param monitorTasksManager The object handling execution of monitor tasks not directly input by users e.g. scheduling updates.
     */
    public BloodMonitorActions(DashboardView view, DashboardModel model, ObserveStorage storage, MonitorTasksManager monitorTasksManager) {
        super(view, model, storage, monitorTasksManager);
    }
    
    @Override
    public void showPatientDetailsAction() {
        // Check if a row is actually selected. If row == -1, then there is no row selected.
        Integer row = getView().getBloodTableSelectedRow();
        if (row != -1){
            // Get the ID of the selected patient
            String patientId = getView().getValueFromBloodTable(row, 0);
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
                
                // Check if a blood pressure observation exists for the patient                  
                Boolean hasBlood = getModel().patientHasBiometric(observationCode, selectedPatientId);
                if (hasBlood) {
                    // Check if the monitor already exists
                    if (!getStorage().hasBloodBiometric(key)){
                        // Create biometric subject and monitor only with patient details (no measurements yet).
                        Biometric biometric = new Biometric(observationCode, selectedPatientId, selectedGivenName, selectedFamilyName);
                        BiometricTextMonitor monitor = this.view.getBloodTextMonitor();
                        biometric.attach(monitor);
                        monitor.setObservation(biometric);
                        getStorage().storeBloodBiometric(observationCode + "," + selectedPatientId, biometric);
                        // Create blank monitor row in table in view with only patient name
                        getView().addMonitorTemplate(observationName, selectedGivenName + selectedFamilyName, selectedPatientId);
                        
                        // If this is the only monitor in the table, initialise the default scheduler which will fill the monitor with measurement data.
                        if (!getMonitorTasksManager().hasScheduledUpdate()){
                            getMonitorTasksManager().initScheduledUpdates(); 
                        }
                        // If there is already at least one other existing monitor, then do not reset the scheduler but simply add this monitor and fill in its data. 
                        else {
                            getMonitorTasksManager().addMonitor(key);
                        }
                        // If this is the first monitor, show the update frequency button in view
                        if (getStorage().numBlood() == 1){
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
        Integer row = getView().getBloodTableSelectedRow();
        if (row != -1){
            // Get the patient ID from the selected row.
            Integer patientIdRow = 0;
            String patientId = getView().getValueFromBloodTable(row, patientIdRow);
            // Delete row from table
            getView().removeBloodTableRow(row);
            // Remove observation and observer
            String code = getObservationCodes().getCode(observationName.BLOOD.name());
            getStorage().removeBloodBiometric(code + "," + patientId);

            if (!getStorage().hasMonitors()){
                if (getMonitorTasksManager().hasScheduledUpdate()) {
                    getMonitorTasksManager().cancelScheduledUpdates();
                }
                getView().hideUpdateOptions();
            }
        }
    }
    
    /**
     * Updates the thresholds at which blood pressure monitors are highlighted.
     */
    public void updateThresholdAction(){
        // Check if the frequency input by the user is a valid integer
        String systolicThresholdString = this.view.getSystolicThresholdString();
        String diastolicThresholdString = this.view.getDiastolicThresholdString();
        if (stringIsValidNumber(systolicThresholdString) && stringIsValidNumber(diastolicThresholdString)) {
            Integer systolicThresholdInt = Integer.parseInt(systolicThresholdString);  
            Integer diastolicThresholdInt = Integer.parseInt(diastolicThresholdString);  
            // Check if the threshold input is a valid range (>0)
            if (systolicThresholdInt > 0 && diastolicThresholdInt > 0) {
                // Update thresholds as part of schedule
                getStorage().setSystolicThreshold(systolicThresholdInt);
                getStorage().setDiastolicThreshold(diastolicThresholdInt);
                getView().updateBloodOverThreshold(systolicThresholdInt, diastolicThresholdInt);
            }
            else {
                // Reset both thresholds in the user input back to the default value.
                getView().setThresholdsToDefault();
            }
        }
        else {
            // Reset both thresholds in the user input back to the default value.
            getView().setThresholdsToDefault();
        }
    }
    
    /**
     * Checks if a given String is a valid number (i.e. all digits).
     * @param text The given string
     * @return Boolean. Returns true if the string is a valid number, and false if not.
     */
    private Boolean stringIsValidNumber(String text){
        // Loop through all characters in the string
        for (Integer i = 0; i < text.length(); i++) {
            // Check if the character is a digit
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
