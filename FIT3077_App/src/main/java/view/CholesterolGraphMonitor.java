package view;

/**
 * CholesterolGraphMonitor is a subclass of BiometricGraphMonitor which monitors cholesterol i.e. updates the graph.
 * @author Bryan
 */
public class CholesterolGraphMonitor extends BiometricGraphMonitor{

    /**
     * Constructor for CholesterolGraphMonitor.
     * @param view - Dashboard view to be updated when observations are updated.
     */
    public CholesterolGraphMonitor(DashboardView view) {
        super(view);
    }
    
    @Override
    public void update() {
        view.updateCholesterolBarChart();
    } 
}
