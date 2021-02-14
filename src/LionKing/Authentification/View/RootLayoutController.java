/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LionKing.Authentification.View;

import LionKing.Authentification.LoginMain;
import LionKing.Authentification.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


/**
 * FXML Controller class
 *
 * @author USER
 */
public class RootLayoutController 
{

    @FXML
    private TextField pseudoField;
    @FXML
    private PasswordField pswdField;
    
    private LoginMain loginMain;
    
    public void setLoginMain(LoginMain logM)
    {
        this.loginMain=logM;
    }
    
    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
    /**
     * verifie l'authentification
     */
    @FXML
    private void handleConnection() 
    {
        User tempUser=new User();    
        tempUser.setPseudo((String)pseudoField.getText());  
        tempUser.setPswd((String)pswdField.getText()); 
        loginMain.verifierConnection(tempUser);
    }
    
    
}
