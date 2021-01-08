package model;

import deserialisation.Identifier;
import java.util.List;
import deserialisation.Name;


/**
 * A data class used as an attribute of a DashboardModel object that stores the user
 * health-practitioner-specific information as retrieved from the FHIR server and
 * deserialised using GSON.
 * 
 * @author Bryan
 */
public class Practitioner {
    private List<Name> name;
    private List<Identifier> identifier;
    
    /**
     * Gets practitioner's system ID
     * @return String
     */
    public String getSystemId(){
        return identifier.get(0).getSystemId();
    }
    
    /**
     * Gets practitioner's full name
     * @return String
     */
    public String getFullName() {
        return name.get(0).getFullNameWithPrefix();
    }
}
