package model;

import iterator.BiometricContainer;
import java.util.Map;

/**
 * ObserveStorage stores all biometric observations as well as any relevant statistics e.g. cholesterol average.
 * @author Bryan
 */
public class ObserveStorage {
    private BiometricContainer cholesterolBiometrics = new BiometricContainer();
    private BiometricContainer bloodBiometrics = new BiometricContainer();
    private Integer cholesterolAvg;
    private Integer systolicThreshold = null;
    private Integer diastolicThreshold = null;
    
    /**
     * Returns cholesterolBiometrics.
     * @return BiometricContainer - Aggregate structure containing Biometrics.
     */
    public BiometricContainer getCholesterolBiometricsContainer(){
        return this.cholesterolBiometrics;
    }
    
    /**
     * Returns bloodBiometrics.
     * @return BiometricContainer - Aggregate structure containing Biometrics.
     */
    public BiometricContainer getBloodBiometricsContainer(){
        return this.bloodBiometrics;
    }

    /**
     * Sets systolic threshold.
     * @param threshold Integer - represents the new systolic threshold.
     */
    public void setSystolicThreshold(Integer threshold){
        this.systolicThreshold = threshold;
    }
    
    /**
     * Sets diastolic threshold.
     * @param threshold Integer - represents the new diastolic threshold.
     */
    public void setDiastolicThreshold(Integer threshold){
        this.diastolicThreshold = threshold;
    }
    
    public Boolean hasBloodThresholds(){
        return (getSystolicThreshold() != null && getDiastolicThreshold() != null);
    }
    
    /**
     * Gets systolic threshold.
     * @return Integer - represents the systolic threshold.
     */
    public Integer getSystolicThreshold(){
        return this.systolicThreshold;
    }
    
    /**
     * Gets diastolic threshold.
     * @return Integer - represents the diastolic threshold.
     */
    public Integer getDiastolicThreshold(){
        return this.diastolicThreshold;
    }
    
    /**
     * Sets the cholesterol average.
     * @param avg Integer
     */
    public void setCholesterolAvg(Integer avg) {
        this.cholesterolAvg = avg;
    }
    
    /**
     * Stores a cholesterol biometric into storage.
     * @param key String - Key in the form of "observationCode,patientId".
     * @param value Biometric - Biometric observation to store.
     */
    public void storeCholesterolBiometric(String key, Biometric value) {
        getCholesterolBiometricsContainer().put(key, value);
    }
    
    /**
     * Stores a blood pressure biometric into storage.
     * @param key String - Key in the form of "observationCode,patientId"
     * @param value Biometric
     */
    public void storeBloodBiometric(String key, Biometric value) {
        getBloodBiometricsContainer().put(key, value);
    }
    
    /**
     * Returns all cholesterol biometrics
     * @return Map<String, Biometric> 
     */
    public Map<String, Biometric> getCholesterolBiometricsCopy(){
        return cholesterolBiometrics.getBiometricsCopy();
    }
    
    /**
     * Returns all blood pressure biometrics
     * @return Map<String, Biometric> 
     */
    public Map<String, Biometric> getBloodBiometricsCopy(){
        return bloodBiometrics.getBiometricsCopy();
    }
    
    /**
     * Gets a cholesterol biometric.
     * @param key String - Key in the form of "observationCode,patientId".
     * @return Biometric - Cholesterol biometric.
     */
    public Biometric getCholesterolBiometric(String key) {
        return cholesterolBiometrics.getBiometricsCopy().get(key);
    }
    
     /**
     * Gets a blood pressure biometric.
     * @param key String - Key in the form of "observationCode,patientId"
     * @return Biometric
     */
    public Biometric getBloodBiometric(String key) {
        return bloodBiometrics.getBiometricsCopy().get(key);
    }
    
    /**
     * Checks if a cholesterol biometric exists.
     * @param key String - Key in the form of "observationCode,patientId"
     * @return Boolean - True if it exists, False if not
     */
    public Boolean hasCholesterolBiometric(String key) {
        return cholesterolBiometrics.getBiometricsCopy().containsKey(key);
    }

    /**
     * Checks if a blood pressure biometric exists.
     * @param key String - Key in the form of "observationCode,patientId"
     * @return Boolean - True if it exists, False if not
     */
    public Boolean hasBloodBiometric(String key) {
        return bloodBiometrics.getBiometricsCopy().containsKey(key);
    }
    
    /**
     * Removes a cholesterol biometric from storage.
     * @param key String - Key in the form of "observationCode,patientId"
     */
    public void removeCholesterolBiometric(String key) {
        getCholesterolBiometricsContainer().remove(key);
    }
    
    /**
     * Removes a blood pressure biometric from storage.
     * @param key String - Key in the form of "patientId"
     */
    public void removeBloodBiometric(String key) {
        getBloodBiometricsContainer().remove(key);
    }
    
    /**
     * Returns number of cholesterol biometrics currently stored.
     * @return Integer
     */
    public Integer numCholesterol(){
        return getCholesterolBiometricsContainer().getBiometricsCopy().size();
    }
    
    /**
     * Gets the number of blood biometrics currently stored.
     * @return Integer - Number of blood biometrics.
     */
    public Integer numBlood(){
        return getBloodBiometricsContainer().getBiometricsCopy().size();
    }
    
    /**
     * Checks if there are currently any monitors/observations.
     * @return Boolean - True if a monitor exists, false if not.
     */
    public Boolean hasMonitors(){
        if (hasBloodBiometrics() || hasCholesterolBiometrics()){
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Checks if there are currently any cholesterol monitors/observations.
     * @return Boolean - True if a cholesterol monitor exists, false if not.
     */
    public Boolean hasCholesterolBiometrics(){
        return !getCholesterolBiometricsContainer().getBiometricsCopy().isEmpty();
    }
    
    /**
     * Checks if there are currently any blood pressure monitors/observations.
     * @return Boolean - True if a blood pressure.
     */
    public Boolean hasBloodBiometrics(){
        return !getBloodBiometricsContainer().getBiometricsCopy().isEmpty();
    }
}
