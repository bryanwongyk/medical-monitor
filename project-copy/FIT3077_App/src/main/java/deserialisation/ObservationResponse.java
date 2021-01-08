package deserialisation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A data class storing deserialised measurement JSON response data in the form of an 
 * ObservationEntry using GSON.
 * 
 * @author Bryan
 */
public class ObservationResponse {
    private Integer total;
    private List<ObservationEntry> entry;
    
    /**
     * Gets quantity of observation from entry
     * @return String
     */
    public String getQuantity(){
        return this.entry.get(0).getQuantity();
    }
    
    /**
     * Gets unit of observation from entry
     * @return String
     */
    public String getUnit(){
        return  this.entry.get(0).getUnit();
    }
    
    /**
     * Gets time of observation from entry
     * @return String
     */
    public List<String> getTimes(){
        List<String> times = new ArrayList();
        for (ObservationEntry entryItem: this.entry) {
            times.add(entryItem.getTime());
        }
        return times;
    }
    
    /**
     * Gets total number of observations to check if observations for the patient exist
     * @return String
     */
    public Integer getTotal(){
        return this.total;
    }
    
    /**
     * Gets a list of all component measurements from the response body, if there are any component measurements. e.g. systolic and diastolic components of a blood measurement.
     * @return List<List<Map<String, String>>> e.g. [[{name: diastolic, qty: value, unit: value}, {name: systolic, qty: value, unit: value}],...]
     */
    public List<List<Map<String, String>>> getAllComponentMeasurements(){
        List<List<Map<String, String>>> measurements = new ArrayList();
        for (ObservationEntry entryItem: this.entry) {
            measurements.add(entryItem.getComponentMeasurements());
        }
        return measurements;
    }
}
