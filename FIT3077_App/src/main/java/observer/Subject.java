package observer;

import java.util.HashSet;

/**
 * Implements the Subject component of an Observer pattern.
 * 
 * @author Bryan
 */
public class Subject {
    protected HashSet<Observer> observers = new HashSet<Observer>();

    /**
     * Set an Observer to observe this Subject for state changes
     * @param o Observer - Observer to attach to this Subject
     */
    public void attach(Observer o) {
        this.observers.add(o);
    }

    /**
     * Inform all attached Observers to update their state based on this 
     * Subject's current state.
     */
    public void notifyObservers() {
        for (Observer o : this.observers) {
                o.update();
        }
    }
}
