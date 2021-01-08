package deserialisation;

import java.util.List;
import java.util.Map;

/**
 * Data class which stores deserialised data pertaining to a biometric 
 * measurement entry using GSON.
 * 
 * @author Bryan
 */
public class ObservationEntry {
    private ObservationResource resource;
    
    /**
     * Gets quantity
     * @return String
     */
    public String getQuantity(){
        return this.resource.getQuantity();
    }
    
    /**
     * Gets unit
     * @return String
     */
    public String getUnit(){
        return this.resource.getUnit();
    }
    
    /**
     * Gets time
     * @return String
     */
    public String getTime(){
        return this.resource.getTime();
    }
    
    /**
     * Gets component measurements from entry resource.
     * @return 
     */
    public List<Map<String, String>> getComponentMeasurements(){
        return this.resource.getComponentMeasurements();
    }
}
