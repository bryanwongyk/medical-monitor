package deserialisation;

import java.util.List;
import model.Practitioner;

/**
 * Data class storing deserialised details of a practitioner response using GSON.
 * 
 * @author Bryan
 */
public class PractitionerResponse {
    private List<PractitionerEntry> entry;
    private Integer total;
    
    /**
     * Gets practitioner data from entry.
     * @return Practitioner
     */
    public Practitioner getPractitioner(){
        return entry.get(0).getPractitioner();
    }
    
    /**
     * Checks if a Practitioner resource exists in the FHIR search.
     * @return Boolean - True if it exists, False if not
     */
    public Boolean hasPractitioner(){
        if (total <= 0) {
            return false;
        }
        else {
            return true;
        }
    }
}

