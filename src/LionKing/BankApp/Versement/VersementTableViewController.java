/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LionKing.BankApp.Versement;

import LionKing.MainApp;
import java.util.Date;
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
 * FXML Controller class
 *
 * @author DIAVOL'HARINOSY
 */
public class VersementTableViewController {
    @FXML
    private TableView<Versement> VersementTable;
    @FXML
    private TableColumn<Versement, String> NumBVColumn;
    @FXML
    private TableColumn<Versement, String> NumComptColumn;
    @FXML
    private TableColumn<Versement, String> MontantVersementColumn;
    @FXML
    private TableColumn<Versement, String> DateVersementColumn;
    
    @FXML
    private Button btn_modifier;
    @FXML
    private Button btn_supprimer;

    private MainApp mainApp;
    
    @FXML
    private ComboBox combobox;
    @FXML
    private TextField recherche;

    
    //constructeur
    public VersementTableViewController(){
        
    }
    
    @FXML
    private void initialize() 
    {
        // Initialize the Versement table with the two columns.
        NumBVColumn.setCellValueFactory(cellData -> cellData.getValue().NumBVProperty());
        NumComptColumn.setCellValueFactory(cellData -> cellData.getValue().NumComptProperty());
        MontantVersementColumn.setCellValueFactory(cellData -> cellData.getValue().MontantVersementProperty());
        DateVersementColumn.setCellValueFactory(cellData -> cellData.getValue().DateVersementProperty());
        
        combobox.getItems().add("Par N°Bordereau");
        combobox.getItems().add("Par N°Compte");
        combobox.setValue("Par N°Compte");
        
        btn_modifier.setDisable(true);
        btn_supprimer.setDisable(true);

        
        VersementTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> activerButton());
    }
    
    private void activerButton()
    {
        btn_modifier.setDisable(false);
        btn_supprimer.setDisable(false);
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        VersementTable.setItems(mainApp.getVersementData());
    }
    
    @FXML
    private void handleDeleteVersement() 
    {
        int selectedIndex = VersementTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Versement selectedIteme=VersementTable.getSelectionModel().getSelectedItem();
            mainApp.getBDD().DeleteVersement(selectedIteme);
            VersementTable.getItems().remove(selectedIndex);
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
    private void handleNewVersement() 
    {
        Versement tempVersement = new Versement();
        boolean okClicked = mainApp.showVersementEditDialog(tempVersement,1);
            if (okClicked) 
            {
            mainApp.getVersementData().add(tempVersement);
            //appel de la fonction d'ajout de client
            mainApp.getBDD().CreateVersement(tempVersement);
            }
    }
    
    @FXML
    private void handleEditVersement() 
    {
        Versement selectedVersement = VersementTable.getSelectionModel().getSelectedItem();
        if (selectedVersement != null) 
        {
            Versement v= new Versement();
            v.setMontantVersement(selectedVersement.getMontantVersement());
            boolean okClicked = mainApp.showVersementEditDialog(selectedVersement,2);
            if (okClicked) 
            {
                mainApp.getBDD().UpdateVersement(selectedVersement,v);
                mainApp.showVersementTableView();  
            }

        } 
        else 
        {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleRetourClient() {
        mainApp.showClientTableView();
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
        ObservableList<Versement> VersementData = FXCollections.observableArrayList();
        String value=(String) combobox.getValue();
        String Mot=recherche.getText();
        if(value.equals("Par N°Bordereau"))
        {
            VersementData=mainApp.getBDD().searcheByNumBordereau(Mot);
            
            VersementTable.setItems(VersementData);
            VersementTable.refresh();
            
        }
        else if(value.equals("Par N°Compte"))
        {
            VersementData=mainApp.getBDD().searcheByNumComptVersement(Mot);
            VersementTable.setItems(VersementData);
            VersementTable.refresh();
        }
            
    }
    
}
