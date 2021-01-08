package requests;

import com.google.gson.Gson;
import java.io.IOException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import deserialisation.EncounterResponse;
import deserialisation.ObservationResponse;
import deserialisation.PractitionerResponse;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.WebServiceException;
import java.net.URLEncoder;
import model.Patient;
import model.Practitioner;

/**
 * RequestHandler makes HTTP requests to the FHIR API server using an OkHTTP client (third-party library).
 * @author Bryan
 */
public class RequestHandler {
    private OkHttpClient client;
    private Gson gson;
    private String rootUrl = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir";
    
    /**
     * Constructor for RequestHandler.
     */
    public RequestHandler(){
        client = new OkHttpClient();
        client.setConnectTimeout(30, TimeUnit.SECONDS); // connect timeout length if there is no server response
        client.setReadTimeout(30, TimeUnit.SECONDS);    // socket timeout length if there is no server response
        gson = new Gson();
    }
    
    /**
     * Does a GET request to the FHIR server with a given URL.
     * @param url String - URL to make a request to.
     * @return String - Returns the response as a String if the GET request is successful, or returns null if it is unsuccessful.
     */
    public String getRequest(String url) {
        try {
            // Makes a get request based on the url given.
            url = getRootUrl() + url;
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            
            Response response = getClient().newCall(request).execute();
            
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Practitioner getPractitionerRequest (String practitionerId) {
        try {
            // Use RequestHandler to make a GET requestHandler to find the Practitioner resource based on their ID.
            String systemId = "http://hl7.org/fhir/sid/us-npi|" + practitionerId;
            String practitionerData = getRequest("/Practitioner?identifier=" + URLEncoder.encode(systemId, "UTF-8"));
            PractitionerResponse response = getGson().fromJson(practitionerData, PractitionerResponse.class);
            // If practitioner ID is invalid return null. Otherwise, de-serialise response.
            if (!response.hasPractitioner()) {
                return null;
            }
            return response.getPractitioner();
            
        } catch (UnsupportedEncodingException ex) {
            System.out.println("getPractitioner error.");
            return null;
        }
    }
    
    public Patient getPatientRequest (String patientId){
        // Deserialise patient data
        return getGson().fromJson(getRequest("/Patient/"+ patientId), Patient.class);
    }
    
    public EncounterResponse getEncounterFirstPageRequest (String practitionerId){
        try {
            String encounterData = getRequest("/Encounter?participant.identifier=" + URLEncoder.encode(practitionerId, "UTF-8") + "&_include=Encounter.participant.individual&_include=Encounter.patient&_count=50");
            // Deserialise encounter data
            return getGson().fromJson(encounterData, EncounterResponse.class);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("getEncounterFirstPageRequest error.");
            return null;
        }
    }
    
    /**
     * Takes a "next" page link for a given search page, and removes the prefixed root URL, so that only the final part of the URL is returned for a subsequent call to getRequest.
     * E.G. https://fhir.monash.edu/hapi-fhir-jpaserver/fhir?_getpages=ea15d8cf-b52f-44cd-a0dc-2ef0cf1f59fa&_getpagesoffset=1&_count=1&_pretty=true&_bundletype=searchset
     * is split into the following:
     * (1) https://fhir.monash.edu/hapi-fhir-jpaserver/fhir
     * (2) ?_getpages=ea15d8cf-b52f-44cd-a0dc-2ef0cf1f59fa&_getpagesoffset=1&_count=1&_pretty=true&_bundletype=searchset
     * @param url String - "next" link
     * @return String - final part of the URL after removing the root URL
     */
    public String formatNextPageUrl(String url) {
        String[] arrayStr = url.split(getRootUrl());
        return arrayStr[1];
    }
    
    public EncounterResponse getEncounterNextPageRequest (EncounterResponse prevEncounterResponse){
        String nextUrl = formatNextPageUrl(prevEncounterResponse.getNextPage());
        String encounterData = getRequest(nextUrl);
        // Deserialise encounter data
        return getGson().fromJson(encounterData, EncounterResponse.class);
    }
    
    /**
     * Uses a RequestHandler object to make a GET request for a patient's observation, and parses the JSON response.
     * @param patientId String - The ID of the patient to be searched.
     * @param observationCode String - The observation code for the biometric.
     * @return ObservationResponse - The observation response as deserialised by GSON.
     */
    public ObservationResponse getBiometricRequest (String patientId, String observationCode, String count) {
        String observationData = getRequest("/Observation?patient=" + patientId + "&code=" + observationCode + "&_sort=-date&_count=" + count);
        if (observationData == null) {
            throw new WebServiceException("Error in retrieving observation data.");
        }
       return getGson().fromJson(observationData, ObservationResponse.class);
    }
    
    /**
     * Get gson attribute.
     * @return Gson
     */
    public Gson getGson(){
        return this.gson;
    }
    
    /**
     * Get client attribute
     * @return OkHttpClient
     */
    public OkHttpClient getClient(){
        return this.client;
    }
    
    /**
     * Get rootUrl attribute
     * @return String
     */
    public String getRootUrl(){
        return this.rootUrl;
    }
}
