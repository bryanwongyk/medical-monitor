package deserialisation;

import java.util.Map;

/**
 * Class storing the deserialised observation component deserialised using GSON.
 * @author Bryan
 */
public class ObservationComponent {
    private ComponentCode code;
    private Map<String, String> valueQuantity;
    
    /**
     * Gets observation name from code attribute.
     * @return String - text from ComponentCode.
     */
    public String getObservationName() {
        return this.code.getText();
    }
    
    /**
     * Gets quantity value from valueQuantity attribute.
     * @return Map<String, String> - value, unit, system, and code from valueQuantity part of JSON response.
     */
    public String getQuantity(){
        return this.valueQuantity.get("value");
    }
    
    /**
     * Gets unit from valueQuantity attribute.
     * @return Map<String, String> - value, unit, system, and code from valueQuantity part of JSON response.
     */
    public String getUnit(){
        return this.valueQuantity.get("unit");
    }
}
