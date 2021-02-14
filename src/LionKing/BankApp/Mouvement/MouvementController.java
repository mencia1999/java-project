/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LionKing.BankApp.Mouvement;

import LionKing.BankApp.App.Util.BDConnexion;
import LionKing.BankApp.Client.Client;
import LionKing.BankApp.Retrait.Retrait;
import LionKing.BankApp.Versement.Versement;
import LionKing.MainApp;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author DIAVOL'HARINOSY
 */
public class MouvementController 
{
    @FXML
    private Label NumComptLabel;
    @FXML
    private Label NomLabel;
    @FXML
    private Label PrenomLabel;
    @FXML
    private Label SoldeLabel;
    @FXML
    private Label TotalVersementLabel;
    @FXML
    private Label TotalRetraitLabel;
    @FXML
    private TableView<LigneMouvement> MouvementTableColumn;  
    @FXML
    private TableColumn<LigneMouvement, String> NumChequeColumn;
    @FXML
    private TableColumn<LigneMouvement, String> VersementColumn;
    @FXML
    private TableColumn<LigneMouvement, String> RetraitColumn;
    @FXML
    private TableColumn<LigneMouvement, String> DateColumn;
    @FXML
    private ComboBox mensuel;
    @FXML
    private TextField annuel;
    @FXML
    private Label error;
    
    MainApp mainApp;
    private Client client;
    ObservableList<LigneMouvement> ligneMouvements = FXCollections.observableArrayList();
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }
    
    @FXML
    private void initialize() {
        
        
        
        // Initialize the client table with the two columns.
        NumChequeColumn.setCellValueFactory(cellData -> cellData.getValue().NumChequeProperty);
        VersementColumn.setCellValueFactory(cellData -> cellData.getValue().MontantVersementProperty);
        RetraitColumn.setCellValueFactory(cellData -> cellData.getValue().RetraitProperty);
        DateColumn.setCellValueFactory(cellData -> cellData.getValue().DateProperty); 
        
        mensuel.getItems().addAll("Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre");
        mensuel.setValue("Janvier");
        mensuel.setVisibleRowCount(12);
    }
    
    public void showClientMouvement(Client client){
        double SumVersement = 0,SumRetrait=0;
        
        NumComptLabel.setText(client.getNumCompt());
        NomLabel.setText(client.getNom());
        PrenomLabel.setText(client.getPrenom());
        SoldeLabel.setText(client.getSolde());
        
        BDConnexion db = new BDConnexion();
        ObservableList<Versement> listVersement = db.getClientVersement(client);
        ObservableList<Retrait> listRetrait = db.getClientRetrait(client);
        
        for(Versement v:listVersement){
            ligneMouvements.add(new LigneMouvement(v.getNumBV(),v.getMontantVersement(),null,v.getDateVersement()));
            SumVersement= SumVersement + parseDouble(v.getMontantVersement());
            TotalVersementLabel.setText(String.valueOf(SumVersement));
            
        }
        for(Retrait r:listRetrait){
            ligneMouvements.add(new LigneMouvement(r.getNumCheque(),null,r.getMontantRetrait(),r.getDateRetrait()));
            SumRetrait= SumRetrait + parseDouble(r.getMontantRetrait());
            TotalRetraitLabel.setText(String.valueOf(SumRetrait));
        }
        
        MouvementTableColumn.setItems(ligneMouvements);
    }
    
    public void showClientMouvementByMonth(Client client,int mois){
        double SumVersement = 0,SumRetrait=0;
        TotalVersementLabel.setText(String.valueOf(SumVersement));
        TotalRetraitLabel.setText(String.valueOf(SumRetrait));
        
        BDConnexion db = new BDConnexion();
        ObservableList<Versement> listVersement = db.getClientVersementByMonth(client,mois);
        ObservableList<Retrait> listRetrait = db.getClientRetraitByMonth(client,mois);
        
        ligneMouvements=null;
        ligneMouvements=FXCollections.observableArrayList();
        
        for(Versement v:listVersement){
            ligneMouvements.add(new LigneMouvement(v.getNumBV(),v.getMontantVersement(),null,v.getDateVersement()));
            SumVersement= SumVersement + parseDouble(v.getMontantVersement());
            TotalVersementLabel.setText(String.valueOf(SumVersement));
            
        }
        for(Retrait r:listRetrait){
            ligneMouvements.add(new LigneMouvement(r.getNumCheque(),null,r.getMontantRetrait(),r.getDateRetrait()));
            SumRetrait= SumRetrait + parseDouble(r.getMontantRetrait());
            TotalRetraitLabel.setText(String.valueOf(SumRetrait));
        }
        
        MouvementTableColumn.setItems(ligneMouvements);
        MouvementTableColumn.refresh();
    }
    
    public void showClientMouvementByYear(Client client,int annee){
        double SumVersement = 0,SumRetrait=0;
        TotalVersementLabel.setText(String.valueOf(SumVersement));
        TotalRetraitLabel.setText(String.valueOf(SumRetrait));
        
        BDConnexion db = new BDConnexion();
        ObservableList<Versement> listVersement = db.getClientVersementByYear(client,annee);
        ObservableList<Retrait> listRetrait = db.getClientRetraitByYear(client,annee);
        ligneMouvements=null;
        ligneMouvements=FXCollections.observableArrayList();
        
        for(Versement v:listVersement){
            ligneMouvements.add(new LigneMouvement(v.getNumBV(),v.getMontantVersement(),null,v.getDateVersement()));
            SumVersement= SumVersement + parseDouble(v.getMontantVersement());
            TotalVersementLabel.setText(String.valueOf(SumVersement));
            
        }
        for(Retrait r:listRetrait){
            ligneMouvements.add(new LigneMouvement(r.getNumCheque(),null,r.getMontantRetrait(),r.getDateRetrait()));
            SumRetrait= SumRetrait + parseDouble(r.getMontantRetrait());
            TotalRetraitLabel.setText(String.valueOf(SumRetrait));
        }
        
        MouvementTableColumn.setItems(ligneMouvements);
        MouvementTableColumn.refresh();
    }
    
    public class LigneMouvement{
        StringProperty NumChequeProperty;
        StringProperty MontantVersementProperty;
        StringProperty RetraitProperty;
        StringProperty DateProperty;
        
        public LigneMouvement(String numCheque, String montantVersement,String retrait,String date){
            NumChequeProperty = new SimpleStringProperty(numCheque);
            MontantVersementProperty = (montantVersement == null) ? new SimpleStringProperty(" - ") : new SimpleStringProperty(montantVersement);
            RetraitProperty = (retrait == null) ? new SimpleStringProperty(" - ") : new SimpleStringProperty(retrait);
            DateProperty = new SimpleStringProperty(date);
            
        }
        
    }
    
    @FXML
    private void handleRetour() {
        mainApp.showClientTableView();
    }
    
    private int getMonth(String mois)
    {
        switch (mois){
            case "Janvier" :
                return 0;
            case "Février" :
                return 1;
            case "Mars" :
                return 2;
            case "Avril" :
                return 3;
            case "Mai" :
                return 4;
            case "Juin" :
                return 5;
            case "Juillet" :
                return 6;
            case "Août" :
                return 7;
            case "Septembre" :
                return 8;
            case "Octobre" :
                return 9;
            case "Novembre" :
                return 10;
            case "Décembre" :
                return 11;
        }
        return -1;
    }
    
    
    @FXML
    private void handleByMonth()
    {
        String m=(String)mensuel.getValue();
        int mois=getMonth(m);
        showClientMouvementByMonth(this.client,mois);
    }
    
    @FXML
    private void handleByYear()
    {
        if(isInputValid())
        {
            int annee;
            annee = parseInt(annuel.getText());
            showClientMouvementByYear(this.client,annee);
        }
        
    }
    
    private Boolean isInputValid()
    {
        if (annuel.getText() == null || annuel.getText().length() == 0) {
            error.setText("Année invalide");
            return false;
        }else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(annuel.getText());
            } catch (NumberFormatException e) {
                error.setText("Année invalide");
                return false;
            }
        }
        error.setText("");
        return true;
    }
    
}
