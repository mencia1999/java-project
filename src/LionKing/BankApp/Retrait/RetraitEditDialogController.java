/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LionKing.BankApp.Retrait;

import LionKing.BankApp.App.Util.DateUtil;
import LionKing.BankApp.Client.Client;
import LionKing.BankApp.Retrait.Retrait;
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
public class RetraitEditDialogController
{
    
    @FXML
    private TextField NumChequeField;
    @FXML
    private TextField NumComptField;
    @FXML
    private TextField MontantRetraitField;

    
    @FXML
    private Label error;
    
    private Stage dialogStage;
    private Retrait retrait;
    private boolean okClicked = false;
    
    private  int mode;
    
    public void setMode(int i)
    {
        this.mode =i;
    }

    
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
    public void setRetrait(Retrait retrait) {
        this.retrait = retrait;
        
       // NumComptLabel.setText(client.getNumCompt());
        NumChequeField.setText(retrait.getNumCheque());
        NumComptField.setText(retrait.getNumCompt());
        MontantRetraitField.setText(retrait.getMontantRetrait());
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
 
            retrait.setNumCheque(NumChequeField.getText());
            retrait.setNumCompt(NumComptField.getText());
            retrait.setMontantRetrait(MontantRetraitField.getText());
            retrait.setDate(new Date()); 
            

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
        if(NumChequeExist(NumChequeField.getText())==true)
        {
           if(mode==1)
            {
                error.setText("Numero de Bordereau déjà existant");
                return false;
            }
        }
        

        if (NumChequeField.getText() == null || NumChequeField.getText().length() == 0) 
        {
            errorMessage += "No valid Nom!\n";
        }
        if (NumComptField.getText() == null || NumComptField.getText().length() == 0) 
        {
            errorMessage += "No valid Numero compte!\n";
        }
        if (MontantRetraitField.getText() == null || MontantRetraitField.getText().length() == 0) 
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
    
    private Boolean NumChequeExist(String num)
    {
        Boolean rp=false;
        
        ObservableList<String> NumChequeData=FXCollections.observableArrayList();
        NumChequeData=mainApp.getBDD().getAllNumCheque();
        for(int i=0;i<NumChequeData.size();i++)
        {
            if(NumChequeData.get(i).equals(num))
                rp=true;
        }
        
        return rp;
    }
    
}
