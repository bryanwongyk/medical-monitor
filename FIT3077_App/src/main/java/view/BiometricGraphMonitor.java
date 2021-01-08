package view;

import java.util.ArrayList;
import model.Biometric;
import model.ObservationName;
import observer.Observer;

/**
 * BiometricGraphMonitor is an Observer (related to graph monitors) that stores all observations for a single type of biometric. E.G. If it is observing Cholesterol
 * it will store all cholesterol observations. It updates the Dashboard view component any time a change is made to an
 * observation.
 * @author Bryan
 */
public abstract class BiometricGraphMonitor extends Observer{
    protected DashboardView view;
    protected ArrayList<Biometric> observations;
    protected ObservationName observationName;
    
    /**
     * Constructor for BiometricGraphMonitor.
     * @param view - Dashboard view to be updated when observations are updated.
     */
    public BiometricGraphMonitor(DashboardView view){
        this.view = view;
        this.observations = new ArrayList();
    }
    
    public abstract void update();
}
