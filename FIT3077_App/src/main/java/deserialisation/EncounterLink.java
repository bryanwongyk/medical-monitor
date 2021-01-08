package deserialisation;

/**
 * A class that stores a deserialised container of details pertaining to an 
 * encounter link using GSON.
 * 
 * This class provides a method for retrieving the links of the next page
 * of encounters, and a way of checking whether the next page exists.
 * 
 * @author Bryan
 */
public class EncounterLink {
    public String relation;
    public String url;
    
    /**
     * Gets the relation given in the link list of a FHIR resource. 
     * @return String - Relation can be "self", "next", or "previous".
     */
    public String getRelation(){
        return this.relation;
    }
    
    /**
     * Gets the URL given to access the respective relation. For example, to go to the next page of search results.
     * @return String - URL
     */
    public String getUrl(){
        return this.url;
    }
}
