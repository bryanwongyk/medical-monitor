package deserialisation;

import model.Practitioner;

/**
 * PractitionerEntry stores a practitioner resource as represented by
 * the JSON response from the FHIR server, and deserialised using GSON
 * @author Bryan
 */
public class PractitionerEntry {
    Practitioner resource;
    
    /**
     * Gets practitioner FHIR resource
     * @return Practitioner
     */
    public Practitioner getPractitioner(){
        return this.resource;
    }
}
