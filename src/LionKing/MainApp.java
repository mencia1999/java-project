/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LionKing;

import LionKing.Authentification.LoginMain;
import LionKing.Authentification.Model.User;
import LionKing.Authentification.Util.BDDUser;
import LionKing.BankApp.App.Util.BDConnexion;
import LionKing.BankApp.App.View.HomeLayoutController;
import LionKing.BankApp.App.View.RootLayoutController;
import LionKing.BankApp.Client.Client;
import LionKing.BankApp.Client.ClientController;
import LionKing.BankApp.Client.ClientDetailViewController;
import LionKing.BankApp.Client.ClientEditDialogController;
import LionKing.BankApp.Mouvement.MouvementController;
import LionKing.BankApp.Retrait.Retrait;
import LionKing.BankApp.Retrait.RetraitEditDialogController;
import LionKing.BankApp.Retrait.RetraitTableViewController;
import LionKing.BankApp.Versement.Versement;
import LionKing.BankApp.Versement.VersementEditDialogController;
import LionKing.BankApp.Versement.VersementTableViewController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author LionKING
 */
public class MainApp extends Application 
{
    private BDConnexion BDD=null;
    private BDDUser bddUser=null;
    private User usr;
    
    private ObservableList<Client> ClientData = FXCollections.observableArrayList();
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    
    /*
    * constructeur de la classe mainApp
    */
    public MainApp()
    {
        BDD=new BDConnexion();
        bddUser=new BDDUser();
        
        usr= new User();
    }
    
    /*
    * constructeur de la classe mainApp
    */
    public MainApp(User user)
    {
        BDD=new BDConnexion();
        bddUser=new BDDUser();
        
        this.usr=user;
    }

    
    @Override
    public void start(Stage primaryStage) 
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestion Bancaire");
        
        // Set the application icon.
        //this.primaryStage.getIcons().add(new Image("file:resources/images/jonathan.png"));
       
        if(usr.isConnected())
        {
            initRootLayout();
            showHomeLayout();
        }
        else
        {
            LoginMain loginMain= new LoginMain();
            loginMain.start(primaryStage);
        }
            
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    /**
     * Returns the main user.
     * @return
     */
    public User getUser() {
        return this.usr;
    }
    
    /**
     * Returns the BddUser object.
     * @return
     */
    public BDDUser getBddUser() {
        return bddUser;
    }
    
    /**
     * Returns the main user.
     * @return
     */
    public BDConnexion getBDD() {
        return this.BDD;
    }
    
    
    /**
     * Setter de User
     */
    public void setUser(User user)
    {
        this.usr=user;
    }
    
    /**
     * verifie si le client est connectée  
     * @return true si connecté else false 
     */
    public Boolean isConnected()
    {
        Boolean rp;
        rp=false;
        
        if(this.usr.isConnected() && this.usr.isEnabled() && (this.usr.isAdmin() || this.usr.isBanquier()) )
            rp=true;
        
        return rp;
    }
    
    public void seDeconnecter()
    {
        bddUser.seDeconnecter(this.usr);
        this.usr=new User();
        LoginMain main= new LoginMain();
        main.start(new Stage());
        primaryStage.close();
    }
    
    public  void showNotConnectedError()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(primaryStage);
            alert.setTitle("Connexion");
            alert.setHeaderText("Erreur de connexion");
            alert.setContentText("Vous êtes déconnecter ou votre compte à été désactivé ");

            alert.showAndWait();
            
            bddUser.seDeconnecter(this.usr);
            
            LoginMain main=new LoginMain();
            main.start(new Stage());
            primaryStage.close();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() 
    {
        try 
        {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("BankApp/App/View/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            controller.setInformation();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    /**
     * affiche l'interface d'accueill.
     */
    public void showHomeLayout()
    {
        if(isConnected())
        {    
            try {
                // Load person HomeLayout.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("BankApp/App/View/HomeLayout.fxml"));
                AnchorPane homeLayout=null;
                homeLayout = (AnchorPane) loader.load();

                // Give the controller access to the main app.
                HomeLayoutController controller = loader.getController();
                controller.setMainApp(this);
                controller.setUser(this.usr);

                rootLayout.setCenter(homeLayout); 


            } catch (IOException ex) {
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else
            showNotConnectedError();
    }

    
    // Debut client
    
    
    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Client> getClientData() {
        return ClientData;
    }
    
    public void showClientTableView() {
        if(isConnected())
        {
            try {
                ClientData=BDD.getAllClient();
                // Load person overview.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("BankApp/Client/Client.fxml"));
                AnchorPane ClientView = (AnchorPane) loader.load();

                // Set person overview into the center of root layout.
                rootLayout.setCenter(ClientView);

                 // Give the controller access to the main app.
                ClientController controller = loader.getController();
                controller.setMainApp(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            showNotConnectedError();
    }
    
    public void showClientDetailView(Client client) 
    {
        if(isConnected())
        {
            try {
                // Load client overview.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("BankApp/Client/ClientDetailView.fxml"));
                AnchorPane ClientTableView = (AnchorPane) loader.load();

                // Set client overview into the center of root layout.
                rootLayout.setCenter(ClientTableView);

                 //Give the controller access to the main app.
                ClientDetailViewController controller = loader.getController();
                controller.setMainApp(this);
                controller.showClientDetails(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            showNotConnectedError();
    }
    
    public boolean showClientEditDialog(Client client) {
        
        if(isConnected())
        {
            try {
                // Load the fxml file and create a new stage for the popup dialog.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("BankApp/Client/ClientEditDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("modification client");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Set the person into the controller.
                ClientEditDialogController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setClient(client);
                controller.setMainApp(this);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();

                return controller.isOkClicked();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void showAdminPage() {
        LoginMain log=new LoginMain(usr);
        log.start(new Stage());
        primaryStage.close();
    }
    
    
    //Debut Versement
    
    private ObservableList<Versement> VersementData = FXCollections.observableArrayList();
    
    public ObservableList<Versement> getVersementData() {
        return VersementData;
    }
    
    public void showVersementTableView() 
    {
        if(isConnected())
        {
            try {
                // Load versement overview.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("BankApp/Versement/VersementTableView.fxml"));
                AnchorPane VersementTableView = (AnchorPane) loader.load();
                
                VersementData=this.BDD.getAllVersement();
                // Set client overview into the center of root layout.
                rootLayout.setCenter(VersementTableView);

                 //Give the controller access to the main app.
                VersementTableViewController controller = loader.getController();
                controller.setMainApp(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            showNotConnectedError();
    }
    
    public boolean showVersementEditDialog(Versement versement ,int i) {
        
        if(isConnected())
        {
            try {
                // Load the fxml file and create a new stage for the popup dialog.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("BankApp/Versement/VersementEditDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("modification Versement");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Set the versemet into the controller.
                VersementEditDialogController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setVersement(versement);
                controller.setMainApp(this);
                controller.setMode(i);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();

                return controller.isOkClicked();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    
    //Debut Retrait
    
    private ObservableList<Retrait> RetraitData = FXCollections.observableArrayList();
    
    public ObservableList<Retrait> getRetraitData() {
        return RetraitData;
    }
    
    public void showRetraitTableView() 
    {
        if(isConnected())
        {
            try {
                // Load versement overview.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("BankApp/Retrait/RetraitTableView.fxml"));
                AnchorPane RetraitTableView = (AnchorPane) loader.load();
                RetraitData=this.BDD.getAllRetrait();
                // Set client overview into the center of root layout.
                rootLayout.setCenter(RetraitTableView);

                 //Give the controller access to the main app.
                RetraitTableViewController controller = loader.getController();
                controller.setMainApp(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            showNotConnectedError();
    }
    
    public boolean showRetraitEditDialog(Retrait retrait, int i) {
        
        if(isConnected())
        {
            try {
                // Load the fxml file and create a new stage for the popup dialog.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("BankApp/Retrait/RetraitEditDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("modification Retrait");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Set the versemet into the controller.
                RetraitEditDialogController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setRetrait(retrait);
                controller.setMainApp(this);
                controller.setMode(i);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();

                return controller.isOkClicked();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    
    //Debut Mouvement Bancaire
    
    public void showMouvement(Client client) 
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("BankApp/Mouvement/MouvementView.fxml"));
            AnchorPane MouvementView = (AnchorPane) loader.load();
            
            // Set client overview into the center of root layout.
                rootLayout.setCenter(MouvementView);

                 //Give the controller access to the main app.
                MouvementController controller = loader.getController();
                controller.setMainApp(this);
                controller.showClientMouvement(client);
                controller.setClient(client);
                
                
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
      

