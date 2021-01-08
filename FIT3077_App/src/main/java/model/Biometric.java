package model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import observer.Subject;

/**
 * An instance of Biometric represents a single observation of a patient e.g. a cholesterol observation of a patient.
 * A biometric may have more than one quantity if it has several components, and the key for each quantity would be the component name e.g. "systolic". If the biometric
 * has no components and only has one associated quantity, then the key should just be the observation name in lower-case e.g. "cholesterol".
 * @author Bryan
 */
public class Biometric extends Subject {
    protected String patientId;
    protected String code;
    protected String givenName;
    protected String familyName;
    protected String effectiveDateTime;
    protected Map<String, String> quantity = new ConcurrentHashMap(); // measurement quantity.
    protected Map<String, String> unit = new ConcurrentHashMap(); // the unit used for this measurement.
    
    public Biometric(String code, String patientId, String givenName, String familyName) {
        this.patientId = patientId;
        this.code = code;
        this.givenName = givenName;
        this.familyName = familyName;
    }

    /**
     * Gets observation code
     * @return String
     */
    public String getCode(){
        return code;
    }
    
    /**
     * Gets patient ID
     * @return String
     */
    public String getId(){
        return patientId;
    }
    
    /**
     * Concatenates and returns patient full name
     * @return String
     */
    public String getName(){
        return givenName + familyName;
    }
    
    /**
     * Gets time of observation
     * @return String
     */
    public String getTime(){
        return effectiveDateTime.replace("T", " ");
    }
    
    /**
     * Gets quantity of observation
     * @return String
     */
    public String getQuantity(String componentName){
        return quantity.get(componentName);
    }
    
    /**
     * Gets unit of observation
     * @return String
     */
    public String getUnit(String componentName){
        return unit.get(componentName);
    }
    
    /**
     * Sets time of observation
     * @param time String - Time given by FHIR server
     */
    public void setTime(String time){
        effectiveDateTime = time;
    }
    
    /**
     * Sets quantity of observation. Used when observation is updated by the scheduler.
     * @param quantity String - Quantity given by FHIR server
     */
    public void setQuantity(String componentName, String quantity) {
        // Round quantity received 
        // Convert String to float
        Float quantityFloat = Float.parseFloat(quantity);
        // Convert float to integer and round
        Integer quantityInt = Math.round(quantityFloat);
        // Convert back to string
        String quantityStr = Integer.toString(quantityInt);
        this.quantity.put(componentName, quantityStr);
    }
    
    /**
     * Sets unit of observation. Used when observation is updated by the scheduler.
     * @param unit String - Quantity given by FHIR server
     */
    public void setUnit(String componentName, String unit) {
        this.unit.put(componentName, unit);
    }
}
