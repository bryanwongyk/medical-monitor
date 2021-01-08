package model;

import deserialisation.EncounterResponse;
import deserialisation.ObservationResponse;
import requests.RequestHandler;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The Model component of the Dashboard that handles all business logic such as calling the RequestHandler to make GET requests, storing patient details,
 * calling the data storage for observations.
 * @author Bryan
 */
public class DashboardModel {
    private Practitioner user;
    private Map<String, Patient> patients = new HashMap();
    private RequestHandler requestHandler = new RequestHandler();
    
    /**
     * Constructor for DashboardModel.
     * @param practitioner Practitioner - Represents the logged in practitioner.
     */
    public DashboardModel(Practitioner practitioner) {
        this.user = practitioner;
        initPatientList();
    }
    
    /**
     * Retrieves all of the logged in practitioner's patients by making requests using the RequestHandler.
     */
    private void initPatientList(){
        // Limit to 5 page GET requests maximum
        Integer nRequests = 0;
        Integer requestLimit = 5;
        // For loop for every patient on a page
        // Get all encounters that the Practitioner is a part of
        // Deserialise encounter data
        EncounterResponse encounterResponse = getRequestHandler().getEncounterFirstPageRequest(user.getSystemId());
        nRequests ++;
        // Get a unique set of the patient IDs from this page
        Set<String> patientIds = new HashSet<String>();
        patientIds = encounterResponse.getUniquePatientIds(patientIds);

        // Loop through pages (if any)
        Boolean nextPage = encounterResponse.hasNextPage();
        while (nextPage && nRequests < requestLimit) {
            encounterResponse = getRequestHandler().getEncounterNextPageRequest(encounterResponse);
            // Get a unique set of the patient IDs from this page which are not already in the set yet
            patientIds = encounterResponse.getUniquePatientIds(patientIds);
            // Check if there is another page
            nextPage = encounterResponse.hasNextPage();
            // Increment requestHandler count
            nRequests ++;
        }

        // Get all patient names based on patient IDs and store data
        for (String patientId : patientIds) {
            Patient patient = getRequestHandler().getPatientRequest(patientId);
            // Add to patients
            getPatients().put(patient.getId(), patient);
        }
    }
    
    /**
     * Gets the practitioner's full name.
     * @return String
     */
    public String getPractitionerName(){
        return this.user.getFullName();
    }
    
    /**
     * Gets the stored patients.
     * @return Map<String, Patient> 
     */
    public Map<String, Patient> getPatients() {
        return this.patients;
    }
    
    /**
     * Checks if a patient has the given biometric (e.g. not all patients have a Cholesterol measurement).
     * @param observationCode String - observation code for the biometric type
     * @param patientId String - patient ID
     * @return Boolean - True if biometric exists, False if not
     */
    public Boolean patientHasBiometric(String observationCode, String patientId) {
        ObservationResponse observationResponse = requestHandler.getBiometricRequest(patientId, observationCode, "1");
        // If total observations found is greater than 0 then it exists, otherwise it does not.
        if (observationResponse.getTotal() > 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Returns details of a patient given their ID.
     * @param patientId String - ID of the patient.
     * @return Map<String, String> - Map of their details.
     */
    public Map<String, String> getPatientDetails(String patientId){
        Map<String, String> info = new HashMap();
        String birthDate = getPatients().get(patientId).getBirthDate();
        info.put("birthDate", getPatients().get(patientId).getBirthDate());
        info.put("gender", getPatients().get(patientId).getGender());
        info.put("address", getPatients().get(patientId).getAddress());
        return info;
    }
    
    /**
     * Get requestHandler attribute.
     * @return RequestHandler
     */
    public RequestHandler getRequestHandler(){
        return this.requestHandler;
    }
}
    
