package monitor_tasks;

import deserialisation.ObservationResponse;
import iterator.BiometricIterator;
import java.util.List;
import java.util.Map;
import model.Biometric;
import model.ObserveStorage;
import view.DashboardView;

/**
 * BloodMonitorTasks represents the tasks blood monitors execute such as updating monitors, adding monitors, or updating statistics.
 * @author Bryan
 */
public class BloodMonitorTasks extends MonitorTasks{
    
    /**
     * Constructor for BloodMonitorTasks.
     * @param storage ObserveStorage - contains storage of monitors.
     * @param view DashboardView - view component of dashboard.
     */
    public BloodMonitorTasks(ObserveStorage storage, DashboardView view) {
        super(storage, view);
    }

    @Override
    public void updateMonitors() {
        if (this.storage.hasBloodBiometrics()) {
            // Loop through all existing monitors
            BiometricIterator it = this.storage.getBloodBiometricsContainer().getIterator();
            while (it.hasNext()){
                // Get requestHandler for new observation data
                Map.Entry<String, Biometric> biometric = it.next();
                String key = biometric.getKey();
                Biometric observation = biometric.getValue();
                
                // Get requestHandler for new observation data
                String patientId = key.split(",")[1];
                String observationCode = observation.getCode();
                
                ObservationResponse observationResponse = this.requestHandler.getBiometricRequest(patientId, observationCode, "1");
                // Update observation with current observation data
                List<List<Map<String, String>>> componentMeasurements = observationResponse.getAllComponentMeasurements();
                for (Integer entry = 0; entry < componentMeasurements.size(); entry++) {
                    for (Integer component = 0; component < componentMeasurements.get(entry).size(); component++) {
                        if (componentMeasurements.get(entry).get(component).get("name").equals("Systolic Blood Pressure")){
                            observation.setQuantity(this.observationName.SYSTOLIC.name(), componentMeasurements.get(entry).get(component).get("qty"));
                            observation.setUnit(this.observationName.SYSTOLIC.name(), componentMeasurements.get(entry).get(component).get("unit"));
                        }
                        if (componentMeasurements.get(entry).get(component).get("name").equals("Diastolic Blood Pressure")){
                            observation.setQuantity(this.observationName.DIASTOLIC.name(), componentMeasurements.get(entry).get(component).get("qty"));
                            observation.setUnit(this.observationName.DIASTOLIC.name(), componentMeasurements.get(entry).get(component).get("unit"));
                        }
                    }
                }
                
                observation.setTime(observationResponse.getTimes().get(0).split("\\+")[0]);
                //  Notify all observers
                observation.notifyObservers();
            }
            // Check for values above blood thresholds
            updateStatistics();
        }
    }

    @Override
    public void addMonitor(String key) {
            // 1. Get added observation
            Biometric observation = this.storage.getBloodBiometric(key);
            String observationCode = key.split(",")[0];
            String patientId = key.split(",")[1];

            // 2. Get requestHandler for observation data
            ObservationResponse observationResponse = requestHandler.getBiometricRequest(patientId, observationCode, "1");
            // 3. Update observation data
            List<List<Map<String, String>>> componentMeasurements = observationResponse.getAllComponentMeasurements();
            for (Integer entry = 0; entry < componentMeasurements.size(); entry++) {
                for (Integer component = 0; component < componentMeasurements.get(entry).size(); component++) {
                    if (componentMeasurements.get(entry).get(component).get("name").equals("Systolic Blood Pressure")){
                        observation.setQuantity(observationName.SYSTOLIC.name(), componentMeasurements.get(entry).get(component).get("qty"));
                        observation.setUnit(observationName.SYSTOLIC.name(), componentMeasurements.get(entry).get(component).get("unit"));
                    }
                    if (componentMeasurements.get(entry).get(component).get("name").equals("Diastolic Blood Pressure")){
                        observation.setQuantity(observationName.DIASTOLIC.name(), componentMeasurements.get(entry).get(component).get("qty"));
                        observation.setUnit(observationName.DIASTOLIC.name(), componentMeasurements.get(entry).get(component).get("unit"));
                    }
                }
            }
            observation.setTime(observationResponse.getTimes().get(0).split("\\+")[0]);
            this.storage.storeBloodBiometric(key, observation);
            // 4. Notify all observers
            observation.notifyObservers();
            // 5. Check for values above blood thresholds
            updateStatistics();
    }

    @Override
    public void updateStatistics() {
        if (storage.hasBloodThresholds()) {
            // Update view
            view.updateBloodOverThreshold(storage.getSystolicThreshold(), storage.getDiastolicThreshold());
        }
    }
}
