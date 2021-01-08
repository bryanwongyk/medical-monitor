package view;

import model.Biometric;

/**
 * CholesterolTextMonitor is a subclass of BiometricTextMonitor which monitors cholesterol.
 * @author Bryan
 */
public class CholesterolTextMonitor extends BiometricTextMonitor{

    /**
     * Constructor for CholesterolTextMonitor.
     * @param view - Dashboard view to be updated when observations are updated.
     */
    public CholesterolTextMonitor(DashboardView view) {
        super(view);
    }
    
    @Override
    public void update() {
        for (Biometric observation : observations) {
            view.updateCholesterolTextualMonitor(observation.getId(), observation.getQuantity(observationName.CHOLESTEROL.name()), observation.getUnit(observationName.CHOLESTEROL.name()), observation.getTime());  
        }
    } 
}
