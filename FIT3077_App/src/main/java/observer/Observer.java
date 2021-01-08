package observer;

/**
 * An interface part of the observer pattern used for updating monitored 
 * biometric measurements.
 * 
 * @author Bryan
 */
public abstract class Observer {
    /**
     * Update the view to which this Observer is attached when this method is 
     * called.
     */
    public abstract void update();
}