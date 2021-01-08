package deserialisation;

/**
 * Data class storing deserialised city, state, and country information for an 
 * address using GSON.
 * @author Bryan
 */
public class Address {
    private String city;
    private String state;
    private String country;
    
    /**
     * Returns city
     * @return String
     */
    public String getCity(){
        return city;
    }
    
    /**
     * Returns state
     * @return String
     */
    public String getState(){
        return state;
    }
    
    /**
     * Returns country
     * @return String
     */
    public String getCountry(){
        return country;
    }
}
