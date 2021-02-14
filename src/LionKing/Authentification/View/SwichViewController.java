/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LionKing.Authentification.View;

import LionKing.Authentification.LoginMain;
import javafx.fxml.FXML;


/**
 * FXML Controller class
 *
 * @author USER
 */
public class SwichViewController  {

    private LoginMain loginMain;
    
    public void setLoginMain(LoginMain logM)
    {
        this.loginMain=logM;
    }
    
    /**
     * Choisi l'interface administrateur
     */
    @FXML
    private void handleAdmin() {
        loginMain.showAdminPage();
    }
    
    /**
     * Choisi l'interface administrateur
     */
    @FXML
    private void handleBanquier() {
        loginMain.showApplication();
    }
    
}
