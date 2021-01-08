package deserialisation;

import java.util.List;

/**
 * Data class to which a deserialised system id of a user (i.e. a health 
 * practitioner) is stored using GSON. The system id is used to retrieve the encounters 
 * associated with a practitioner. For example, a system id may be of the form
 * "http://hl7.org/fhir/sid/us-npi|...". This is different from the
 * resource id which is non-unique.
 * 
 * @author Bryan
 */
public class Identifier {
    private String system;
    private String value;
    
    /**
     * Returns system and value concatenated together in the form "system|value"
     * @return String
     */
    public String getSystemId(){
        return this.system + "|" + this.value;
    }
}
