package deserialisation;

import java.util.List;
import java.util.Set;

/**
 * A class providing a method to obtain all unique patient identifiers by 
 * iterating through a list attribute of EncounterEntry instances using GSON.
 * 
 * @author Bryan
 */
public class EncounterResponse {
    private List<EncounterLink> link;
    private List<EncounterEntry> entry;
    
    /**
     * Retrieve all unique patient identifiers associated with this instance's 
     * private List of EncounterEntry instances.
     * 
     * @return a String Set of unique patient identifiers.
     */
    public Set<String> getUniquePatientIds(Set<String> patientIds) {
        // A set is used to ensure that all patients displayed are unique. One 
        // encounter is one visit to the practitioner, but a patient may visit 
        // the same practitioner multiple times. Therefore, encounters may have 
        // duplicate patients.
        // Get the IDs of all of the practitioner's patients
        for (EncounterEntry entryElement : entry) {
            String patientId = entryElement.getPatientId();
            patientIds.add(patientId);
        }
        return patientIds;
    }
    
    /**
     * Checks if there is a next page of search results by iterating through the "link" list in the FHIR resource.
     * @return Boolean - True if there is a next page, False if there is not
     */
    public Boolean hasNextPage() {
        for (EncounterLink item : link) {
            if (item.getRelation().equals("next")) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the URL of the next page of search results by iterating through the "link" list in the FHIR resource.
     * @return String - URL
     */
    public String getNextPage() {
        for (EncounterLink item : link) {
            if (item.getRelation().equals("next")) {
                return item.getUrl();
            }
        }
        return null;
    }
}
