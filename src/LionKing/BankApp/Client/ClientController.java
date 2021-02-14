/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LionKing.BankApp.Client;

import LionKing.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


/**
 *
 * @author DIAVOL'HARINOSY
 */
public class ClientController {
    
    @FXML
    private TableView<Client> clientTable;
    @FXML
    private TableColumn<Client, String> NumComptColumn;
    @FXML
    private TableColumn<Client, String> NomColumn;
    @FXML
    private TableColumn<Client, String> PrenomColumn;
    @FXML
    private TableColumn<Client, String> SoldeColumn;
    
    @FXML
    private ComboBox combobox;
    @FXML
    private TextField recherche;
    
    @FXML
    private Button btn_modifier;
    @FXML
    private Button btn_supprimer;
    @FXML
    private Button btn_details;
    
    
    
    private MainApp mainApp;
    
    
    @FXML
    private void initialize() {
        // Initialize the client table with the two columns.
        NumComptColumn.setCellValueFactory(cellData -> cellData.getValue().NumComptProperty());
        NomColumn.setCellValueFactory(cellData -> cellData.getValue().NomProperty());
        PrenomColumn.setCellValueFactory(cellData -> cellData.getValue().PrenomProperty());
        SoldeColumn.setCellValueFactory(cellData -> cellData.getValue().SoldeProperty());
        
        combobox.getItems().add("Par Nom");
        combobox.getItems().add("Par N°Compte");
        combobox.setValue("Par Nom");
        
        btn_modifier.setDisable(true);
        btn_supprimer.setDisable(true);
        btn_details.setDisable(true);
        
        clientTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> activerButton());
    }
    
    private void activerButton()
    {
        btn_modifier.setDisable(false);
        btn_supprimer.setDisable(false);
        btn_details.setDisable(false);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        clientTable.setItems(mainApp.getClientData());
    }
    

    
    @FXML
    private void handleDeleteClient() {
        int selectedIndex = clientTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Client selectedIteme=clientTable.getSelectionModel().getSelectedItem();
            mainApp.getBDD().DeleteClient(selectedIteme);
            clientTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleNewClient() 
    {
        Client tempPerson = new Client();
        boolean okClicked = mainApp.showClientEditDialog(tempPerson);
            if (okClicked) 
            {
                mainApp.getClientData().add(tempPerson);
                //appel de la fonction d'ajout de client
                mainApp.getBDD().CreateClient(tempPerson);
            }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditClient() 
    {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) 
        {
            boolean okClicked = mainApp.showClientEditDialog(selectedClient);
            if (okClicked) 
            {
                mainApp.showClientTableView();
                System.out.println("view2 : "+selectedClient.getNumCompt());
            System.out.println("view2 : "+selectedClient.getSolde());
                mainApp.getBDD().UpdateClient(selectedClient);
            }

        } 
        else 
        {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    
    /**
     * voir detail client
     */
    @FXML
    private void VoirDetail() 
    {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null)
        {
            
                mainApp.showClientDetailView(selectedClient);
            

        } 
        else 
        {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    
    /**
     * retour vers l'interface de choix
     */
    @FXML
    private void handleRetour() {
        mainApp.showHomeLayout();
    }
    
    /**
     * fonction recherche
     */
    @FXML
    private void handleRecherche() {
        ObservableList<Client> ClientData = FXCollections.observableArrayList();
        String value=(String) combobox.getValue();
        String Mot=recherche.getText();
        if(value.equals("Par Nom"))
        {
            System.out.println(combobox.getValue());
            System.out.println(Mot);
            ClientData=mainApp.getBDD().searcheByNom(Mot);
            
            clientTable.setItems(ClientData);
            clientTable.refresh();
            
        }
        else if(value.equals("Par N°Compte"))
        {
            System.out.println(combobox.getValue());
            System.out.println(Mot);
            ClientData=mainApp.getBDD().searcheByNumCompt(Mot);
            clientTable.setItems(ClientData);
            clientTable.refresh();
        }
            
    }
}
