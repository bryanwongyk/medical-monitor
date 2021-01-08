package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import model.DashboardModel;
import model.ObservationCodes;
import model.ObservationName;
import model.ObserveStorage;
import monitor_tasks.MonitorTasksManager;
import view.DashboardView;

/**
 * The DashboardController class interprets user inputs when the associated DashboardView object is interacted with,
 * and uses DashboardModel to handle the business logic.
 * @author Bryan
 */
public class DashboardController {
    private DashboardView view;
    private DashboardModel model;
    private ObserveStorage storage;
    private ObservationCodes observationCodes = new ObservationCodes();
    private ObservationName observationName;
    private CholesterolMonitorActions cholesterolActions;
    private BloodMonitorActions bloodActions;
    private MonitorTasksManager monitorTasksManager;

    /**
     * Constructor for DashboardController.
     * @param view DashboardView - View component
     * @param model DashboardModel - Model component
     */
    public DashboardController(DashboardView view, DashboardModel model, ObserveStorage storage) {
        this.view = view;
        this.model = model;
        this.storage = storage;
        this.monitorTasksManager = new MonitorTasksManager(this.storage, this.view);
        this.cholesterolActions = new CholesterolMonitorActions(this.view, this.model, this.storage, this.monitorTasksManager);
        this.bloodActions = new BloodMonitorActions(this.view, this.model, this.storage, this.monitorTasksManager);
        initController();
    }
    
    /**
     * Adds action listeners to view components.
     */
    private void initController() {
        // Update frequency button action listener
        JButton updateBtn = getView().getFreqUpdateBtn();
        updateBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                updateFreqAction();
            }
        });
        
        // Show selected cholesterol patient details button action listener
        JButton cholesterolDetailsBtn = getView().getCholesterolDetailsBtn();
        cholesterolDetailsBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                getCholesterolActions().showPatientDetailsAction();
            }
        });

        // Add cholesterol monitor action listener
        JButton cholesterolMonitorBtn = getView().getCholesterolMonitorBtn();
        cholesterolMonitorBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // Get the text of the button which triggered the click event, and find corresponding code.
                getCholesterolActions().trackAction(observationCodes.getCode(observationName.CHOLESTEROL.name()), observationName.CHOLESTEROL.name());
            }
        });

        // Remove cholesterol monitor button action listener
        JButton removeCholesterolBtn = getView().getRemoveCholesterolBtn();
        removeCholesterolBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                getCholesterolActions().untrackAction();
            }
        });

        // Add blood pressure monitor action listener
        JButton bloodMonitorBtn = getView().getBloodMonitorBtn();
        bloodMonitorBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // Get the text of the button which triggered the click event, and find corresponding code.
                getBloodActions().trackAction(getObservationCodes().getCode(observationName.BLOOD.name()), observationName.BLOOD.name());
            }
        });

        // Remove blood pressure monitor button action listener
        JButton removeBloodBtn = getView().getRemoveBloodBtn();
        removeBloodBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                getBloodActions().untrackAction();
            }
        });
        
        // Show selected blood pressure patient details button action listener
        JButton bloodDetailsBtn = getView().getBloodDetailsBtn();
        bloodDetailsBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                getBloodActions().showPatientDetailsAction();
            }
        });

        // Update blood pressure thresholds button action listener
        JButton thresholdUpdateBtn = getView().getThresholdUpdateBtn();
        thresholdUpdateBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                getBloodActions().updateThresholdAction();
            }
        }); 
    }
    
    /**
     * Updates the frequency at which monitors are updated.
     */
    private void updateFreqAction(){
        // Check if the frequency input by the user is a valid integer
        String frequencyString = getView().getFrequencyString();
        if (stringIsValidNumber(frequencyString)) {
            Integer frequencyInt = Integer.parseInt(frequencyString);  
            // Check if the frequency input is a valid range (>= 5)
            if (frequencyInt >= 5) {
                // Update frequency of scheduler
                getMonitorTasksManager().updateFrequency(frequencyInt);
            }
            else {
                // Reset the frequency in the user input back to the default value.
                getView().setFrequencyToDefault();
            }
        }
        else {
            // Reset the frequency in the user input back to the default value.
            getView().setFrequencyToDefault();
        }
    }
    
    /**
     * Checks if a given String is a valid number (i.e. all digits).
     * @param text The given string
     * @return Boolean. Returns true if the string is a valid number, and false if not.
     */
    private Boolean stringIsValidNumber(String text){
        // Loop through all characters in the string
        for (Integer i = 0; i < text.length(); i++) {
            // Check if the character is a digit
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Gets view attribute.
     * @return DashboardView
     */
    public DashboardView getView(){
        return this.view;
    }
    
    /**
     * Get monitorTasksManager attribute.
     * @return MonitorTasksManager
     */
    public MonitorTasksManager getMonitorTasksManager(){
        return this.monitorTasksManager;
    }
    
    /**
     * Get cholesterolActions attribute.
     * @return CholesterolMonitorActions
     */
    public CholesterolMonitorActions getCholesterolActions(){
        return this.cholesterolActions;
    }
    
    /**
     * Get bloodActions attribute.
     * @return BloodMonitorActions
     */
    public BloodMonitorActions getBloodActions(){
        return this.bloodActions;
    }
    
    /**
     * Gets observationCodes attribute.
     * @return ObservationCodes
     */
    public ObservationCodes getObservationCodes(){
        return this.observationCodes;
    }
}
