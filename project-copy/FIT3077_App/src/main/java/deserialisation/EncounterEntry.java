package deserialisation;

/**
 * Data class storing deserialised details of an encounter; i.e. a consultation 
 * with a health practitioner using GSON.
 * 
 * @author Bryan
 */
public class EncounterEntry {
    private EncounterResource resource;
    
    /**
     * Return a patient's Id from this entry's associated EncounterResource.
     * 
     * @return String - The patient identifier associated with this encounter
     */
    public String getPatientId() {
        return this.resource.getReference().split("/")[1];
    }
}
