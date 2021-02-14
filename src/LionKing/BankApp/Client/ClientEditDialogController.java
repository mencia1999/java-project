/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LionKing.BankApp.Client;

import LionKing.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author DIAVOL'HARINOSY
 */
public class ClientEditDialogController {
    
    @FXML
    private TextField NumComptField;
    @FXML
    private TextField NomField;
    @FXML
    private TextField PrenomField;
    @FXML
    private TextField AdressClientField;
    @FXML
    private TextField NumCINField;
    @FXML
    private TextField SoldeField;
    @FXML
    private TextField NumTelField;
    @FXML
    private TextField EmailField;


    private Stage dialogStage;
    private Client client;
    private boolean okClicked = false;
    
    MainApp mainApp;
    
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the client to be edited in the dialog.
     *
     * @param client
     */
    public void setClient(Client client) {
        this.client = client;

        NumComptField.setText(client.getNumCompt());
        NomField.setText(client.getNom());
        PrenomField.setText(client.getPrenom());
        AdressClientField.setText(client.getAdressClient());
        NumCINField.setText(client.getNumCIN());
        SoldeField.setText(client.getSolde());
        NumTelField.setText(client.getNumTel());
        EmailField.setText(client.getEmail());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            client.setNumCompt(NumComptField.getText());
            client.setNom(NomField.getText());
            client.setPrenom(PrenomField.getText());
            client.setAdressClient(AdressClientField.getText());
            client.setNumCIN(NumCINField.getText());
            client.setSolde(SoldeField.getText());
            client.setNumTel(NumTelField.getText());
            client.setEmail(EmailField.getText());
            
            
           // mainApp.getBDD().UpdateClient(client);

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (NumComptField.getText() == null || NumComptField.getText().length() == 0) {
            errorMessage += "Numero compte invalide!\n";
        }
        if (NomField.getText() == null || NomField.getText().length() == 0) {
            errorMessage += "No valid Nom!\n";
        }
        if (PrenomField.getText() == null || PrenomField.getText().length() == 0) {
            errorMessage += "No valid Numero compte!\n";
        }if (AdressClientField.getText() == null || AdressClientField.getText().length() == 0) {
            errorMessage += "No valid Numero compte!\n";
        }if (NumCINField.getText() == null || NumCINField.getText().length() == 0) {
            errorMessage += "No valid Numero compte!\n";
        }if (SoldeField.getText() == null || SoldeField.getText().length() == 0) {
            errorMessage += "No valid Numero compte!\n";
        }else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(SoldeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Solde (must be an integer)!\n";
            }
        }
        if (NumTelField.getText() == null || NumTelField.getText().length() == 0) {
            errorMessage += "No valid Numero compte!\n";
        }if (EmailField.getText() == null || EmailField.getText().length() == 0) {
            errorMessage += "No valid Numero compte!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
