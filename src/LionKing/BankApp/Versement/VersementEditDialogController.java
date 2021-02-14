/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LionKing.BankApp.Versement;


import LionKing.BankApp.Client.Client;
import LionKing.MainApp;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author DIAVOL'HARINOSY
 */
public class VersementEditDialogController
{
    
    @FXML
    private TextField NumBVField;
    @FXML
    private TextField NumComptField;
    @FXML
    private TextField MontantVersementField;
    
    private  int mode;
    
    public void setMode(int i)
    {
        this.mode =i;
    }

    
    private Stage dialogStage;
    private Versement versement;
    private boolean okClicked = false;
    
    @FXML
    private Label error;

    
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
    
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        //System.out.println(dialogStage);
        this.dialogStage = dialogStage;
    }
    
    /**
     * Sets the Versement to be edited in the dialog.
     *
     * @param client
     */
    public void setVersement(Versement versement) {
        this.versement = versement;
        
       // NumComptLabel.setText(client.getNumCompt());
        NumBVField.setText(versement.getNumBV());
        NumComptField.setText(versement.getNumCompt());
        MontantVersementField.setText(versement.getMontantVersement());
//        DateVersementField.setText(versement.getDateVersement());
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
    private void handleOk() 
    {
        if (isInputValid()) 
        {
            
            //client.setNumCompt(NumComptLabel.getText());
            versement.setNumBV(NumBVField.getText());
            versement.setNumCompt(NumComptField.getText());
            versement.setMontantVersement(MontantVersementField.getText());
            versement.setDate(new Date());
            

            okClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    private boolean isInputValid() {
        String errorMessage = "";

        if(NumComptExist(NumComptField.getText())==false)
        {
            error.setText("Numero de Compte Non existant");
            return false;
        }
        
        if(NumBVExist(NumBVField.getText())==true)
        {
            if(mode==1)
            {
                error.setText("Numero de Bordereau déjà existant");
                return false;
            }
            
        }
        
        if (NumBVField.getText() == null || NumBVField.getText().length() == 0) 
        {
            errorMessage += "No valid Nom!\n";
        }
        if (NumComptField.getText() == null || NumComptField.getText().length() == 0) 
        {
            errorMessage += "No valid Numero compte!\n";
        }
        if (MontantVersementField.getText() == null || MontantVersementField.getText().length() == 0) 
        {
            errorMessage += "No valid Numero compte!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    private Boolean NumComptExist(String num)
    {
        Boolean rp=false;
        
        ObservableList<String> NumComptData=FXCollections.observableArrayList();
        NumComptData=mainApp.getBDD().getAllNumCompte();
        for(int i=0;i<NumComptData.size();i++)
        {
            if(NumComptData.get(i).equals(num))
                rp=true;
        }
        
        return rp;
    }
    
    private Boolean NumBVExist(String num)
    {
        Boolean rp=false;
        
        ObservableList<String> NumBVData=FXCollections.observableArrayList();
        NumBVData=mainApp.getBDD().getAllNumBordereau();
        for(int i=0;i<NumBVData.size();i++)
        {
            if(NumBVData.get(i).equals(num))
                rp=true;
        }
        
        return rp;
    }
    
}
