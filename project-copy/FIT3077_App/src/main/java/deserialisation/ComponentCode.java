package deserialisation;

/**
 * Data class storing deserialised component text for an observation (e.g. diastolic and systolic component "text") using GSON.
 * @author Bryan
 */
public class ComponentCode {
    private String text;

    /**
     * Returns text attribute.
     * @return String text attribute.
     */
    public String getText(){
        return this.text;
    }
}
