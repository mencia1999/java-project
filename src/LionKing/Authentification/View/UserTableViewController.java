/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LionKing.Authentification.View;

import LionKing.Authentification.LoginMain;
import LionKing.Authentification.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 * FXML Controller class
 *
 * @author USER
 */
public class UserTableViewController {

    @FXML
    private Button btn_desactiverS;
    @FXML
    private Button btn_desactiverB;
    @FXML
    private Button btn_desactiverA;
    
    @FXML
    private Button btn_modifierS;
    @FXML
    private Button btn_modifierB;
    @FXML
    private Button btn_modifierA;
    
    @FXML
    private Button btn_supprimerS;
    @FXML
    private Button btn_supprimerB;
    @FXML
    private Button btn_supprimerA;
    
    @FXML
    private Button btn_detailS;
    @FXML
    private Button btn_detailB;
    @FXML
    private Button btn_detailA;
    
    
    
    
    @FXML
    private TableView<User> userSTable;
    @FXML
    private TableColumn<User, String> pseudoSColumn;
    @FXML
    private TableColumn<User, String> nomSColumn;
    @FXML
    private TableColumn<User, String> prenomSColumn;
    @FXML
    private TableColumn<User, Boolean> etatSColumn;
    @FXML
    private TableColumn<User, Boolean> connexionSColumn;
    
    @FXML
    private TableView<User> userATable;
    @FXML
    private TableColumn<User, String> pseudoAColumn;
    @FXML
    private TableColumn<User, String> nomAColumn;
    @FXML
    private TableColumn<User, String> prenomAColumn;
    @FXML
    private TableColumn<User, Boolean> etatAColumn;
    @FXML
    private TableColumn<User, Boolean> connexionAColumn;
    
    @FXML
    private TableView<User> userBTable;
    @FXML
    private TableColumn<User, String> pseudoBColumn;
    @FXML
    private TableColumn<User, String> nomBColumn;
    @FXML
    private TableColumn<User, String> prenomBColumn;
    @FXML
    private TableColumn<User, Boolean> etatBColumn;
    @FXML
    private TableColumn<User, Boolean> connexionBColumn;
    
    
    
    private LoginMain loginMain;
    
    public void setLoginMain(LoginMain loginMain)
    {
        this.loginMain=loginMain;
        
        // Add observable list data to the table
        
        userSTable.setItems(loginMain.getUserSData());
        userATable.setItems(loginMain.getUserAData());
        userBTable.setItems(loginMain.getUserBData());

    }
    
    /**
     * Initializes the controller class.
     */
    public void initialize() 
    {
        btn_desactiverA.setDisable(true);
        btn_detailA.setDisable(true);
        btn_modifierA.setDisable(true);
        btn_supprimerA.setDisable(true);
        
        btn_desactiverB.setDisable(true);
        btn_detailB.setDisable(true);
        btn_modifierB.setDisable(true);
        btn_supprimerB.setDisable(true);
        
        btn_desactiverS.setDisable(true);
        btn_detailS.setDisable(true);
        btn_modifierS.setDisable(true);
        btn_supprimerS.setDisable(true);
        
        // Initialize the person table with the two columns.
        pseudoSColumn.setCellValueFactory(cellData -> cellData.getValue().PseudoProperty());
        nomSColumn.setCellValueFactory(cellData -> cellData.getValue().NomProperty());
        prenomSColumn.setCellValueFactory(cellData -> cellData.getValue().PrenomProperty());
        etatSColumn.setCellValueFactory(cellData -> cellData.getValue().EnabledProperty());
        connexionSColumn.setCellValueFactory(cellData -> cellData.getValue().IsConnectedProperty());
        
        // Initialize the person table with the two columns.
        pseudoAColumn.setCellValueFactory(cellData -> cellData.getValue().PseudoProperty());
        nomAColumn.setCellValueFactory(cellData -> cellData.getValue().NomProperty());
        prenomAColumn.setCellValueFactory(cellData -> cellData.getValue().PrenomProperty());
        etatAColumn.setCellValueFactory(cellData -> cellData.getValue().EnabledProperty());
        connexionAColumn.setCellValueFactory(cellData -> cellData.getValue().IsConnectedProperty());
        
        // Initialize the person table with the two columns.
        pseudoBColumn.setCellValueFactory(cellData -> cellData.getValue().PseudoProperty());
        nomBColumn.setCellValueFactory(cellData -> cellData.getValue().NomProperty());
        prenomBColumn.setCellValueFactory(cellData -> cellData.getValue().PrenomProperty());
        etatBColumn.setCellValueFactory(cellData -> cellData.getValue().EnabledProperty());
        connexionBColumn.setCellValueFactory(cellData -> cellData.getValue().IsConnectedProperty());
        
        userSTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> activerButtonS());
        
        userATable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> activerButtonA());
        
        userBTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> activerButtonB());
    }    
    
    /**
     * retour vers l'interface de choix
     */
    @FXML
    private void handleSwichView() {
        loginMain.showSwichView();
    }
    
    /**
     * retour vers l'interface de choix
     */
    @FXML
    private void handleEditS() {
        int i=userSTable.getSelectionModel().getSelectedIndex();
        if(i>=0)
        {
            User user= userSTable.getSelectionModel().getSelectedItem();
            String pseudo=user.getPseudo();
            Boolean isOkClicked=loginMain.showUserEditDialog(user);
            if(isOkClicked)
            {
                int rows=loginMain.getBddUser().updateUser(user,pseudo);
                System.out.println(rows);
            }
        }
        else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(loginMain.getPrimaryStage());
            alert.setTitle("Pas de séléction");
            alert.setHeaderText("Aucun utilisateur séléctioné ");
            alert.setContentText("Veuillez séléctioner un utilisateur dans le tableau.");

            alert.showAndWait();
        }
    }
    /**
     * retour vers l'interface de choix
     */
    @FXML
    private void handleEditA() {
        int i=userATable.getSelectionModel().getSelectedIndex();
        if(i>=0)
        {
            User user= userATable.getSelectionModel().getSelectedItem();
            String pseudo=user.getPseudo();
            Boolean isOkClicked=loginMain.showUserEditDialog(user);
            if(isOkClicked)
            {
                int rows=loginMain.getBddUser().updateUser(user,pseudo);
                System.out.println(rows);
            }
        }
        else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(loginMain.getPrimaryStage());
            alert.setTitle("Pas de séléction");
            alert.setHeaderText("Aucun utilisateur séléctioné ");
            alert.setContentText("Veuillez séléctioner un utilisateur dans le tableau.");

            alert.showAndWait();
        }
    }
    /**
     * retour vers l'interface de choix
     */
    @FXML
    private void handleEditB() {
        int i=userBTable.getSelectionModel().getSelectedIndex();
        if(i>=0)
        {
            User user= userSTable.getSelectionModel().getSelectedItem();
            String pseudo=user.getPseudo();
            Boolean isOkClicked=loginMain.showUserEditDialog(user);
            if(isOkClicked)
            {
                int rows=loginMain.getBddUser().updateUser(user,pseudo);
                System.out.println(rows);
            }
        }
        else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(loginMain.getPrimaryStage());
            alert.setTitle("Pas de séléction");
            alert.setHeaderText("Aucun utilisateur séléctioné ");
            alert.setContentText("Veuillez séléctioner un utilisateur dans le tableau.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleNewS()
    {
        User user= new User();
        Boolean okClicked=loginMain.showUserEditDialog(user);
        if(okClicked)
        {
            loginMain.getUserSData().add(user);
            
            loginMain.getBddUser().createUser(user);
        }
    }
    
    @FXML
    private void handleNewB()
    {
        User user= new User();
        Boolean okClicked=loginMain.showUserEditDialog(user);
        if(okClicked)
        {
            loginMain.getUserBData().add(user);
            
            loginMain.getBddUser().createUser(user);
            loginMain.getBddUser().setBanquier(user.getPseudo());
        }
    }
    
    @FXML
    private void handleNewA()
    {
        User user= new User();
        Boolean okClicked=loginMain.showUserEditDialog(user);
        if(okClicked)
        {
            loginMain.getUserAData().add(user);
            
            loginMain.getBddUser().createUser(user);
            loginMain.getBddUser().setAdmin(user.getPseudo());
        }
    }
    
    @FXML
    private void handleSupprimerS()
    {
        int i=userSTable.getSelectionModel().getSelectedIndex();
        if (i>=0)
        {
            String pseudo=userSTable.getSelectionModel().getSelectedItem().getPseudo();
            loginMain.getBddUser().deleteUser(pseudo);
            userSTable.getItems().remove(i);
        }
        else
        {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(loginMain.getPrimaryStage());
            alert.setTitle("Pas de séléction");
            alert.setHeaderText("Aucun utilisateur séléctioné ");
            alert.setContentText("Veuillez séléctioner un utilisateur dans le tableau.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleSupprimerA()
    {
        int i=userATable.getSelectionModel().getSelectedIndex();
        if (i>=0)
        {
            String pseudo=userATable.getSelectionModel().getSelectedItem().getPseudo();
            loginMain.getBddUser().deleteUser(pseudo);
            userATable.getItems().remove(i);
        }
        else
        {
            
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(loginMain.getPrimaryStage());
            alert.setTitle("Pas de séléction");
            alert.setHeaderText("Aucun utilisateur séléctioné ");
            alert.setContentText("Veuillez séléctioner un utilisateur dans le tableau.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleSupprimerB()
    {
        int i=userBTable.getSelectionModel().getSelectedIndex();
        if (i>=0)
        {
            String pseudo=userBTable.getSelectionModel().getSelectedItem().getPseudo();
            loginMain.getBddUser().deleteUser(pseudo);
            userBTable.getItems().remove(i);
        }
        else
        {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(loginMain.getPrimaryStage());
            alert.setTitle("Pas de séléction");
            alert.setHeaderText("Aucun utilisateur séléctioné ");
            alert.setContentText("Veuillez séléctioner un utilisateur dans le tableau.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleDesactiverS()
    {
        int i=userSTable.getSelectionModel().getSelectedIndex();
        if(i>=0)
        {
            User user=userSTable.getSelectionModel().getSelectedItem();
            user.setIsEnabled(Boolean.FALSE);

            loginMain.getBddUser().setNotEnabled(user.getPseudo());
        }
        else
        {
            btn_desactiverS.setDisable(true);
        }
              
    }
    
    private void activerButtonS()
    {
        btn_desactiverS.setDisable(false);
        btn_detailS.setDisable(false);
        btn_modifierS.setDisable(false);
        btn_supprimerS.setDisable(false);
    }
    
    private void activerButtonB()
    {
        btn_desactiverB.setDisable(false);
        btn_detailB.setDisable(false);
        btn_modifierB.setDisable(false);
        btn_supprimerB.setDisable(false);
    }
    
    private void activerButtonA()
    {
        btn_desactiverA.setDisable(false);
        btn_detailA.setDisable(false);
        btn_modifierA.setDisable(false);
        btn_supprimerA.setDisable(false);
    }
    
}
