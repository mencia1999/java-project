/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LionKing.BankApp.Retrait;

import LionKing.BankApp.Versement.Versement;
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
 * FXML Controller class
 *
 * @author DIAVOL'HARINOSY
 */
public class RetraitTableViewController {
    @FXML
    private TableView<Retrait> RetraitTable;
    @FXML
    private TableColumn<Retrait, String> NumChequeColumn;
    @FXML
    private TableColumn<Retrait, String> NumComptColumn;
    @FXML
    private TableColumn<Retrait, String> MontantRetraitColumn;
    @FXML
    private TableColumn<Retrait, String> DateRetraitColumn;

    private MainApp mainApp;
    
    @FXML
    private Button btn_modifier;
    @FXML
    private Button btn_supprimer;
    
    @FXML
    private ComboBox combobox;
    @FXML
    private TextField recherche;

    
    //constructeur
    public RetraitTableViewController(){
        
    }
    
    @FXML
    private void initialize() 
    {
        // Initialize the Retrait table with the two columns.
        NumChequeColumn.setCellValueFactory(cellData -> cellData.getValue().NumChequeProperty());
        NumComptColumn.setCellValueFactory(cellData -> cellData.getValue().NumComptProperty());
        MontantRetraitColumn.setCellValueFactory(cellData -> cellData.getValue().MontantRetraitProperty());
        DateRetraitColumn.setCellValueFactory(cellData -> cellData.getValue().DateRetraitProperty());
        
        combobox.getItems().add("Par N°Bordereau");
        combobox.getItems().add("Par N°Compte");
        combobox.setValue("Par N°Compte");
        
        btn_modifier.setDisable(true);
        btn_supprimer.setDisable(true);

        
        RetraitTable.getSelectionModel().selectedItemProperty().addListener(
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
        RetraitTable.setItems(mainApp.getRetraitData());
    }
    
    @FXML
    private void handleDeleteRetrait() 
    {
        int selectedIndex =RetraitTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Retrait selectedIteme=RetraitTable.getSelectionModel().getSelectedItem();
            mainApp.getBDD().DeleteRetrait(selectedIteme);
            RetraitTable.getItems().remove(selectedIndex);
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
    private void handleNewRetrait() 
    {
        Retrait tempRetrait = new Retrait();
        boolean okClicked = mainApp.showRetraitEditDialog(tempRetrait,1);
            if (okClicked) 
            {
            mainApp.getRetraitData().add(tempRetrait);
            //appel de la fonction d'ajout de Retrait
            mainApp.getBDD().CreateRetrait(tempRetrait);
            }
    }
    
    @FXML
    private void handleEditRetrait() 
    {
        Retrait selectedRetrait = RetraitTable.getSelectionModel().getSelectedItem();
        if (selectedRetrait != null) 
        {
            Retrait r= new Retrait();
            r.setMontantRetrait(selectedRetrait.getMontantRetrait());
            boolean okClicked = mainApp.showRetraitEditDialog(selectedRetrait,2);
            if (okClicked) 
            {
                mainApp.getBDD().UpdateRetrait(selectedRetrait,r);
                mainApp.showRetraitTableView();
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
    private void handleRetourRetrait() {
        mainApp.showRetraitTableView();
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
        ObservableList<Retrait> RetraitData = FXCollections.observableArrayList();
        String value=(String) combobox.getValue();
        String Mot=recherche.getText();
        if(value.equals("Par N°Bordereau"))
        {
            RetraitData=mainApp.getBDD().searcheByNumCheque(Mot);
            
            RetraitTable.setItems(RetraitData);
            RetraitTable.refresh();
            
        }
        else if(value.equals("Par N°Compte"))
        {
            RetraitData=mainApp.getBDD().searcheByNumComptRetrait(Mot);
            RetraitTable.setItems(RetraitData);
            RetraitTable.refresh();
        }
            
    }
    
}
