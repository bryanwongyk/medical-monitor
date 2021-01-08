package iterator;

import java.util.Iterator;
import java.util.Map;
import model.Biometric;

/**
 * BiometricIterator is a concrete iterator implementation for the BiometricContainer aggregate structure based on the iterator design pattern.
 * @author Bryan
 */
public class BiometricIterator implements ContainerIterator {
    private Iterator<Map.Entry<String, Biometric>> it;
    
    /**
     * Constructor for BiometricIterator
     * @param biometricContainer BiometricContainer - the aggregate structure to iterate over.
     */
    public BiometricIterator(BiometricContainer biometricContainer){
        this.it = biometricContainer.getBiometricsCopy().entrySet().iterator();
    }
    
    @Override
    public Boolean hasNext() {
        return this.it.hasNext();
    }

    @Override
    public Map.Entry<String, Biometric> next() {
        if (this.it.hasNext()){
            return this.it.next();
        }
        return null;
    }
}
