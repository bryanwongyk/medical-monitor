package view;

import model.Biometric;

/**
 * BloodTextMonitor is a subclass of BiometricTextMonitor which monitors blood pressure.
 * @author Bryan
 */
public class BloodTextMonitor extends BiometricTextMonitor {
    
    /**
     * Constructor for BloodTextMonitor.
     * @param view - Dashboard view to be updated when observations are updated.
     */
    public BloodTextMonitor(DashboardView view) {
        super(view);
    }
    
    @Override
    public void update() {
        // Iterate over all observations and update them.
        for (Biometric observation : observations) {
            view.updateBloodTextMonitor(observation.getId(), observation.getQuantity(observationName.DIASTOLIC.name()), observation.getUnit(observationName.DIASTOLIC.name()), observation.getQuantity(observationName.SYSTOLIC.name()), observation.getUnit(observationName.SYSTOLIC.name()), observation.getTime());  
        }
    } 
}
