package controller;

import view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.DashboardModel;
import model.LoginModel;
import model.ObserveStorage;
import model.Practitioner;
import view.DashboardView;

/**
 * Controller class for the login feature.
 * 
 * This class is instantiated with LoginView and LoginModel objects, from which 
 * it interprets and passes information, respectively.
 * 
 * Initiates the MVC modules for the dashboard feature once login is successful.
 * 
 * @author Bryan
 */
public class LoginController{
    private LoginView view;
    private LoginModel model;

    /**
     * Constructor for LoginController
     * @param view LoginView - View component
     * @param model LoginModel - Model component
     */
    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;
        initController();
    }
    
    /**
     * Attach an action listener to the login button from the LoginView of this 
     * instance which authenticates the input user ID.
     */
    private void initController() {
        // Initialise action listeners in view to interpret user input.
        getView().getIdSubmitBtn().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Integer id = getIdFromLogin();
                // Only make a GET request if the ID is of a valid format.
                if (id != -1){
                    getPractitionerAction(id);
                }
            }
        });
    }
    
    /**
     * Gets the user ID input into the login text field, and validates that the ID only contains numbers.
     * 
     * @return Integer - the input if it is an integer, or -1 otherwise
     */
    private Integer getIdFromLogin(){
        Integer id = -1;
        try {
            id = Integer.parseInt(getView().getIdTextfield().getText());
        }
        catch (NumberFormatException e) {
            getView().getErrorMsg().setText("ID must only contain numbers");
            getView().getIdTextfield().setText("");
        }
        return id;
    }
    
    /**
     * Initiate a program dashboard for a valid id; otherwise display an error.
     * 
     * This method passes the input into this instance's LoginModel in order to 
     * attempt to retrieve a Practitioner object. A non-null Practitioner 
     * initiates the program dashboard; a null Practitioner 
     * displays an error.
     * 
     * @param id an Integer that is a unique identifier for a practitioner in 
     * the database accessible through this instance's LoginModel.
     */
    private void getPractitionerAction(Integer id){
        Practitioner practitioner = getModel().getPractitioner(Integer.toString(id));
        if (practitioner == null) {
            // If the ID was invalid (i.e. practitioner does not exist), set error message on view and allow user to re-enter an ID.
            getView().getErrorMsg().setText("Invalid ID");
            getView().getIdTextfield().setText("");
        }
        else {
            // If the ID was valid, remove the current login view and initialise the dashboard view for the logged in user.
            loginSuccess(practitioner);
            getView().dispose();
        }
    }
    
    /**
     * Initiate the MVC modules for the dashboard feature using a Practitioner 
     * instance.
     * 
     * @param practitioner a non-null Practitioner object.
     */
    private void loginSuccess(Practitioner practitioner){
        // Create dashboard model
        DashboardModel dashboardModel = new DashboardModel(practitioner);
        // Create observe storage
        ObserveStorage storage = new ObserveStorage();
        // Create dashboard view
        DashboardView dashboardView = new DashboardView(dashboardModel, storage);
        // Create dashboard controller
        DashboardController dashboardController = new DashboardController(dashboardView, dashboardModel, storage); 
    }
    
    /**
     * Gets view attribute.
     * @return LoginView
     */
    public LoginView getView(){
        return this.view;
    }
    
    /**
     * Gets model attribute.
     * @return LoginModel
     */
    public LoginModel getModel(){
        return this.model;
    }
}
