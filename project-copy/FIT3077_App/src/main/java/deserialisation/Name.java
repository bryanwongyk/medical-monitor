package deserialisation;

import java.util.List;

/**
 * Class storing the deserialised name information of a patient deserialised using GSON.
 * 
 * @author Bryan
 */
public class Name {
    private String family;
    private List<String> given;
    private List<String> prefix;
    
    /**
     * Concatenates full name into a single string.
     * @return String - full name
     */
    public String getFullNameWithPrefix() {
        // Process prefix
        String prefixString = getPrefix();
        // Process given name
        String givenString = getGivenName();
        // Process family name
        String familyString = getFamilyName();
        return prefixString + " " + givenString + familyString;
    }
    
    /**
     * Gets prefix
     * @return String
     */
    public String getPrefix() {
        // There should only be one prefix.
        return this.prefix.get(0);
    }
    
    /**
     * Gets given name
     * @return String
     */
    public String getGivenName() {
        // Process given name
        String givenString = "";
        // If there is more than one given name for the person, ensure that there is a space between them in the given name. 
        // e.g. if only initials are recorded, then it is possible that they are separated into multiple given names.
        for (String temp : this.given) {
            // Remove all digits from given name response e.g. "Gonzalo160" -> "Gonzalo"
            temp = temp.replaceAll("\\d","");
            givenString += temp + " ";
        }
        return givenString;
    }
    
    /**
     * Gets family name
     * @return String
     */
    public String getFamilyName() {
        // Remove all digits
        return this.family.replaceAll("\\d","");
    }
}
