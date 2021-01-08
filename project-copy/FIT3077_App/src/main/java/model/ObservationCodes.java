package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores codes for all observations
 * 
 * @author Bryan
 */
public class ObservationCodes {
    private Map<String, String> codeMap;
    private ObservationName observationName;
    
    /**
     * Constructor for ObservationCodes.
     */
    public ObservationCodes() {
        this.codeMap = new HashMap();
        initCodes();
    }
    
    /**
     * Initialise the code map with known observation codes from the FHIR server.
     */
    private void initCodes(){
        getCodeMap().put(observationName.CHOLESTEROL.name(), "2093-3");
        getCodeMap().put(observationName.BLOOD.name(), "55284-4");
    }
    
    
    /**
     * Get a code for a given biometric/observation type.
     * @param String - Represents the name of the biometric. e.g. Cholesterol.
     * @return String - Represents the biometric code as stored in the codeMap attribute e.g. 2093-3.
     */
    public String getCode(String biometricName){
        return getCodeMap().get(biometricName);
    }
    
    /**
     * Get codeMap attribute.
     * @return Map<String, String>
     */
    public Map<String, String> getCodeMap(){
        return this.codeMap;
    }
    
}
