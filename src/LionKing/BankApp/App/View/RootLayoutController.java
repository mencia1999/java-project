/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LionKing.BankApp.App.View;

import LionKing.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 * FXML Controller class
 *
 * @author LionKing
 */
public class RootLayoutController 
{
    @FXML
    private Label nomLabel;
    
    @FXML
    private Label prenomLabel;
    
    // Reference to the main application
    private MainApp mainApp;
    
    public void setMainApp(MainApp main) 
    {
        this.mainApp=main;
    }
    
    @FXML
    private void initialize() {
     
    }
    
    public void setInformation()
    {
        nomLabel.setText(mainApp.getUser().getNom());
        prenomLabel.setText(mainApp.getUser().getPrenom());
    }
    
    /**
     * Closes the application.
     */
    @FXML
    private void handleDeconnexion() {
        mainApp.seDeconnecter();
    }
    
}
