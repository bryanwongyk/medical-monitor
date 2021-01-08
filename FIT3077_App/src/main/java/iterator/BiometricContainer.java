package iterator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import model.Biometric;

/**
 * BiometricContainer is a concrete aggregate structure for Biometric objects based on the iterator design pattern.
 * @author Bryan
 */
public class BiometricContainer implements Container {
    private Map<String, Biometric> biometrics = new ConcurrentHashMap();   // <'observationCode,patientId' , Biometric>

    @Override
    public BiometricIterator getIterator() {
        return new BiometricIterator(this);
    }
    
    /**
     * Returns a copy of the biometrics map.
     * @return Map<String, Biometric> - <'observationCode,patientId' , Biometric>
     */
    public Map<String, Biometric> getBiometricsCopy(){
        return new ConcurrentHashMap<String, Biometric>(biometrics);
    }
    
    /**
     * Puts a biometric in the biometrics map.
     * @param key String - key.
     * @param value Biometric - value.
     */
    public void put(String key, Biometric value){
        getBiometrics().put(key, value);
    }
    
    /**
     * Removes a biometric from the biometrics map.
     * @param key String - key.
     */
    public void remove(String key){
        getBiometrics().remove(key);
    }
    
    /**
     * Gets the biometrics attribute.
     * @return Map<String, Biometric> - <'observationCode,patientId' , Biometric>
     */
    public Map<String, Biometric> getBiometrics(){
        return this.biometrics;
    }
}
