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
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author USER
 */
public class UserEditDialogController  {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField cinField;
    @FXML
    private TextField pseudoField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmationField;
    


    private Stage dialogStage;
    private User user;
    private boolean okClicked = false;
    
    private LoginMain loginMain;
    
    public void setLoginMain(LoginMain loginMain)
    {
        this.loginMain=loginMain;
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
     * Sets the user to be edited in the dialog.
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;

        nomField.setText(user.getNom());
        prenomField.setText(user.getPrenom());
        emailField.setText(user.getEmail());
        cinField.setText(user.getCin());
        pseudoField.setText(user.getPseudo());
        passwordField.setText(user.getPswd());
        confirmationField.setText(user.getPswd());
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
            user.setNom(nomField.getText());
            user.setPrenom(prenomField.getText());
            user.setEmail(emailField.getText());
            user.setCin(cinField.getText());
            user.setPseudo(pseudoField.getText());
            user.setPswd(passwordField.getText());

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
     * Called when the user clicks cancel.
     */
    @FXML
    private Boolean isInputValid() {
        
        return true;
    }


    
}
