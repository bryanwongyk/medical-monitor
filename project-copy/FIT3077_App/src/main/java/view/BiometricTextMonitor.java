package view;

import model.Biometric;

/**
 * BiometricTextMonitor is an Observer (related to text monitors) that stores all observations for a single type of biometric. E.G. If it is observing Cholesterol
 * it will store all cholesterol observations. It updates the Dashboard view component any time a change is made to an
 * observation.
 * @author Bryan
 */
public abstract class BiometricTextMonitor extends BiometricGraphMonitor{
    
    /**
     * Constructor for BiometricTextMonitor.
     * @param view - Dashboard view to be updated when observations are updated.
     */
    public BiometricTextMonitor(DashboardView view) {
        super(view);
    }
    /**
     * Maintain a reference to a Biometric (subclass of Subject) when this method is called.
     * 
     * @param observation Biometric - The Biometric that this Observer will observe.
     */
    public void setObservation(Biometric observation) {
        this.observations.add(observation);
    }
}
