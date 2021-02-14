/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LionKing.BankApp.Client;

import LionKing.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ClientDetailViewController  {
    @FXML
    private Label NumComptLabel;
    @FXML
    private Label NomLabel;
    @FXML
    private Label PrenomLabel;
    @FXML
    private Label AdressClientLabel;
    @FXML
    private Label NumCINLabel;
    @FXML
    private Label SoldeLabel;
    @FXML
    private Label NumTelLabel;
    @FXML
    private Label EmailLabel;
    
    MainApp mainApp;
    Client client;
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void showClientDetails(Client client) {
        if (client != null) {
            // Fill the labels with info from the person object.
            NumComptLabel.setText(client.getNumCompt());
            NomLabel.setText(client.getNom());
            PrenomLabel.setText(client.getPrenom());
            AdressClientLabel.setText(client.getAdressClient());
            NumCINLabel.setText(client.getNumCIN());
            SoldeLabel.setText(client.getSolde());
            NumTelLabel.setText(client.getNumTel());
            EmailLabel.setText(client.getEmail());

            this.client=client;
            // TODO: We need a way to convert the birthday into a String! 
            // birthdayLabel.setText(...);
        } else {
            // Person is null, remove all the text.
            NumComptLabel.setText("");
            NomLabel.setText("");
            PrenomLabel.setText("");
            AdressClientLabel.setText("");
            NumCINLabel.setText("");
            SoldeLabel.setText("");
            NumTelLabel.setText("");
            EmailLabel.setText("");
            client=new Client();
        }
    }
    
    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditClient() 
    {
        
        
            boolean okClicked = mainApp.showClientEditDialog(this.client);
            if (okClicked) 
            {
                mainApp.showClientTableView();
                mainApp.getBDD().UpdateClient(this.client);
            }
    }
    
    @FXML
    private void handleRetour() {
        mainApp.showClientTableView();
    }
    
    @FXML
    private void handleMouvement() {
        mainApp.showMouvement(this.client);
    }
    
}
