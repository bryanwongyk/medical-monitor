package deserialisation;

import java.util.Map;

/**
 * A class that stores a deserialised container of details pertaining to an 
 * encounter using GSON.
 * 
 * This class provides a method for retrieving the identifier (the "reference") 
 * of a patient, represented as a private Map attribute of details for a given 
 * medical consultation.
 * 
 * @author Bryan
 */
public class EncounterResource {
    private Map<String, String> subject;
    
    /**
     * Return the attribute representing a patient identifier.
     * @return the patient identifier associated with this encounter
     */
    public String getReference(){
        return this.subject.get("reference");
    }
}
