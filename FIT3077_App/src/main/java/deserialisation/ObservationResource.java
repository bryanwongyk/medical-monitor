package deserialisation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A data class of containers with deserialised details pertaining to a 
 * biometric measurement using GSON.
 * 
 * This class provides methods for extracting relevant data from the details to 
 * be accessible by the system via the ObservationEntry class.
 * 
 * @author Bryan
 */
public class ObservationResource {
    private String effectiveDateTime;
    private Map<String, String> valueQuantity;
    private List<ObservationComponent> component;
    
    /**
     * Gets quantity from valueQuantity
     * @return String
     */
    public String getQuantity(){
        return valueQuantity.get("value");
    }
    
    /**
     * Gets unit from valueQuantity
     * @return String
     */
    public String getUnit(){
        return  valueQuantity.get("unit");
    }
    
    /**
     * Gets time from effectiveDateTime
     * @return String
     */
    public String getTime(){
        return effectiveDateTime;
    }
    
    /**
     * Gets list of component measurements.
     * @return List<Map<String, String>> - A list of a map of component measurements where the key is the component name (e.g. systolic), and the value is the quantity measured.
     */
    public List<Map<String, String>> getComponentMeasurements(){
        List<Map<String, String>> measurements = new ArrayList();
        for (Integer i = 0; i < component.size(); i++) {
            Map<String, String> map = new ConcurrentHashMap();
            map.put("name", component.get(i).getObservationName());
            map.put("qty", component.get(i).getQuantity());
            map.put("unit", component.get(i).getUnit());
            measurements.add(map);
        }
        return measurements;
    }
}
