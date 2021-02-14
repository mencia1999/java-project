/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LionKing.BankApp.App.View;

import LionKing.Authentification.Model.User;
import LionKing.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;



/**
 * FXML Controller class
 *
 * @author USER
 */
public class HomeLayoutController {

   private MainApp mainApp;
   private User  user;
   
   @FXML
   private Button btn_gerer;
    
   
   public void setMainApp(MainApp main)
   {
       this.mainApp=main;
   }
   
   public void setUser(User user)
   {
       this.user=user;
   }
   
   /**
     * Initializes the controller class.
     */
    public void initialize() 
    {
        
    }
   
   /**
     * interface Client
     */
    @FXML
    private void handleClient() {
        mainApp.showClientTableView();
    }
    
    /**
     * interface Versement
     */
    @FXML
    private void handleVersement() {
        mainApp.showVersementTableView();
    }
    
    /**
     * interface retrait
     */
    @FXML
    private void handleRetrait() {
        mainApp.showRetraitTableView();
    }
    
    /**
     * passer dans l'interface de login
     */
    @FXML
    private void handleAdmin() {
        mainApp.showAdminPage();
    }
}
