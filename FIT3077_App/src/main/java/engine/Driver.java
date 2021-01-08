package engine;

import view.LoginView;
import controller.LoginController;
import model.LoginModel;

/**
 * Driver starts the program by first initiating MVC modules for the login page.
 * 
 * @author Bryan
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel();
        LoginController loginController = new LoginController(loginView, loginModel);
        
    }
    
}
