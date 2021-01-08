package model;

import requests.RequestHandler;

/**
 * The LoginModel handles the business logic for LoginController. 
 * 
 * @author Bryan
 */
public class LoginModel {
    private RequestHandler requestHandler = new RequestHandler();
    
    /**
     * Initiate a RequestHandler and return result of query for a practitioner ID 
     * as a Practitioner object.
     * 
     * @param String - The practitioner's ID as stored in the database.
     * @return Practitioner - Object representing the logged in Practitioner.
     */
    public Practitioner getPractitioner(String practitionerId) {;
        return getRequestHandler().getPractitionerRequest(practitionerId);
    }
    
    /**
     * Get requestHandler attribute.
     * @return RequestHandler
     */
    public RequestHandler getRequestHandler(){
        return this.requestHandler;
    }
}
