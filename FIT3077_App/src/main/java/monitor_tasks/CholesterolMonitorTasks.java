package monitor_tasks;

import deserialisation.ObservationResponse;
import iterator.BiometricIterator;
import java.util.Map;
import model.Biometric;
import model.ObserveStorage;
import view.DashboardView;

/**
 * CholesterolMonitorTasks represents the tasks cholesterol monitors execute such as updating monitors, adding monitors, or updating statistics.
 * @author Bryan
 */
public class CholesterolMonitorTasks extends MonitorTasks{

    /**
     * Constructor for CholesterolMonitorTasks.
     * @param storage ObserveStorage - contains storage of monitors.
     * @param view DashboardView - view component of dashboard.
     */
    public CholesterolMonitorTasks(ObserveStorage storage, DashboardView view) {
        super(storage, view);
    }

    @Override
    public void updateMonitors() {
        if (this.storage.hasCholesterolBiometrics()) {
            // Loop through all existing monitors
            BiometricIterator it = this.storage.getCholesterolBiometricsContainer().getIterator();
            while (it.hasNext()){
                // 1. Get requestHandler for new observation data
                Map.Entry<String, Biometric> biometric = it.next();
                String key = biometric.getKey();
                Biometric observation = biometric.getValue();

                String patientId = key.split(",")[1];
                String observationCode = observation.getCode();
                
                ObservationResponse observationResponse = requestHandler.getBiometricRequest(patientId, observationCode, "1");
                // 2. Update observation with current observation data
                observation.setQuantity(observationName.CHOLESTEROL.name(), observationResponse.getQuantity());
                observation.setUnit(observationName.CHOLESTEROL.name(), observationResponse.getUnit());
                observation.setTime(observationResponse.getTimes().get(0).split("\\+")[0]);
                // 3. Store new observation
                this.storage.storeCholesterolBiometric(key, observation);
                // 4. Notify all observers
                observation.notifyObservers();
            } 
            // 5. Calculate average if there is more than one monitor now
            if (this.storage.numCholesterol() > 1){
                updateStatistics();
            } 
            //6. Update cholesterol bar chart
            view.updateCholesterolGraphMonitor();
        }
    }

    @Override
    public void addMonitor(String key) {
            // 1. Get added observation
            Biometric observation = this.storage.getCholesterolBiometric(key);
            String observationCode = key.split(",")[0];
            String patientId = key.split(",")[1];

            // 2. Get requestHandler for observation data
            ObservationResponse observationResponse = requestHandler.getBiometricRequest(patientId, observationCode, "1");
            // 3. Update observation data
            observation.setQuantity(observationName.CHOLESTEROL.name(),observationResponse.getQuantity());
            observation.setUnit(observationName.CHOLESTEROL.name(), observationResponse.getUnit());
            observation.setTime(observationResponse.getTimes().get(0).split("\\+")[0]);
            this.storage.storeCholesterolBiometric(key, observation);
            // 4. Notify all observers
            observation.notifyObservers();
            // 5. Calculate average if there is more than one monitor now
            if (this.storage.numCholesterol() > 1){
                updateStatistics();
            } 
            //6. Update cholesterol bar chart
            view.updateCholesterolGraphMonitor();
    }

    @Override
    public void updateStatistics() {
        Integer sum = 0;
        Integer average = null;
        
        // 1. Loop through all cholesterol observations to calculate sum of quantities
        BiometricIterator it = this.storage.getCholesterolBiometricsContainer().getIterator();
        while (it.hasNext()){
            // Get the quantity value from the biometric and convert it into Integer
            sum += Integer.parseInt(it.next().getValue().getQuantity(observationName.CHOLESTEROL.name()));
        }
        // 2. Calculate average
        // If there are no cholesterol observations, return the average as null.
        if (this.storage.numCholesterol() > 0) {
            average = sum/this.storage.numCholesterol();
            this.storage.setCholesterolAvg(average);
        }
        view.updateCholesterolAverage(average);
    }
    
}
