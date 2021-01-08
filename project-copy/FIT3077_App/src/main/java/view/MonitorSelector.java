package view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MonitorSelector acts as the intermediary between DashboardView and the different monitors displayed on the DashboardView. It instantiates these monitors
 * and allows specific monitors to be selected and used in DashboardView.
 * @author Bryan
 */
public class MonitorSelector {
    private Map<String, BiometricTextMonitor> textMonitors = new ConcurrentHashMap<String, BiometricTextMonitor>();
    private Map<String, BiometricGraphMonitor> graphMonitors = new ConcurrentHashMap<String, BiometricGraphMonitor>();
    private DashboardView view;
    
    /**
     * Constructor for MonitorSelector.
     * @param view DashboardView - view component of the dashboard.
     */
    public MonitorSelector(DashboardView view){
        this.view = view;
        init();
    }
    
    /**
     * Initialising monitors implemented in the view.
     */
    private void init(){
        this.textMonitors.put("cholesterolTextMonitor", new CholesterolTextMonitor(this.view));
        this.graphMonitors.put("cholesterolGraphMonitor", new CholesterolGraphMonitor(this.view));
        this.textMonitors.put("bloodTextMonitor", new BloodTextMonitor(this.view));
    }
    
    /**
     * Selects a text monitor in the view.
     * @param key String - key value of the monitor stored in the textMonitors attribute.
     * @return BiometricTextMonitor
     */
    public BiometricTextMonitor selectTextMonitor(String key){
        return getTextMonitors().get(key);
    }
    
    /**
     * Selects a graph monitor in the view.
     * @param key String - key value of the monitor stored in the graphMonitors attribute.
     * @return BiometricGraphMonitor
     */
    public BiometricGraphMonitor selectGraphMonitor(String key){
        return getGraphMonitors().get(key);
    }
    
    /**
     * Returns the graphMonitors attribute.
     * @return Map<String, BiometricGraphMonitor> 
     */
    private Map<String, BiometricGraphMonitor> getGraphMonitors(){
        return this.graphMonitors;
    }
    
    /**
     * Returns the texthMonitors attribute.
     * @return Map<String, BiometricTextMonitor> 
     */
    private Map<String, BiometricTextMonitor> getTextMonitors(){
        return this.textMonitors;
    }
}
