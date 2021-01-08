package model;

import deserialisation.Address;
import deserialisation.Name;
import java.util.List;

/**
 * Patient represents patient data as retrieved from a GET request to the FHIR server deserialised using GSON.
 * @author Bryan
 */
public class Patient  {
    private String id;
    private List<Name> name;
    private String gender;
    private String birthDate;
    private List<Address> address;
    
    /**
     * Gets patient ID.
     * @return String
     */
    public String getId() {
        return id;
    }
    
    /**
     * Gets patient given name.
     * @return String
     */
    public String getGivenName() {
        return name.get(0).getGivenName();
    }
    
    /**
     * Gets patient family name.
     * @return String
     */
    public String getFamilyName() {
        return name.get(0).getFamilyName();
    }
    
    /**
     * Gets patient gender.
     * @return String
     */
    public String getGender(){
        return gender;
    }
    
    /**
     * Gets patient birth date.
     * @return String
     */
    public String getBirthDate(){
        return birthDate;
    }
    
    /**
     * Gets patient address.
     * @return String
     */
    public String getAddress(){
        return address.get(0).getCity() + ", " + address.get(0).getState() + ", " + address.get(0).getCountry();
    }
}
