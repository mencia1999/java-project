/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LionKing.Authentification;

import LionKing.Authentification.Model.User;
import LionKing.Authentification.Util.BDDUser;
import LionKing.Authentification.View.RootLayoutController;
import LionKing.Authentification.View.SwichViewController;
import LionKing.Authentification.View.UserEditDialogController;
import LionKing.Authentification.View.UserTableViewController;
import LionKing.MainApp;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @author LionKing
 */
public class LoginMain extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private BDDUser bddUser=null;
    private User usr=null;
    private MainApp mainApp=null;
    
    private ObservableList<User> UserSData= FXCollections.observableArrayList();
    private ObservableList<User> UserAData= FXCollections.observableArrayList();
    private ObservableList<User> UserBData= FXCollections.observableArrayList();
    
    private ObservableList<User> roleData= FXCollections.observableArrayList();
    
    
    
    /*
    * constructeur de la classe mainApp
    */
    public LoginMain()
    {
        bddUser=new BDDUser();
        
        usr= new User(); 
        
    }
    
    /*
    * constructeur de la classe mainApp
    */
    public LoginMain(User user)
    {
        bddUser=new BDDUser();
        
        this.usr=user;
        
        UserSData.add(user);
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Authentification");
        
        bddUser=new BDDUser();
        
        mainApp=new MainApp();
        
        initRootLayout();
        if(usr.isConnected())
            showSwichView();
        
        Date actuelle = new Date();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(actuelle);
        System.out.print(dateFormat.format(actuelle).getClass());
        String test="2000-02-07 05:05:05";
        try {
            Date tes=dateFormat.parse(test);
            System.out.println(tes);
        } catch (ParseException ex) {
            Logger.getLogger(LoginMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
   
    }
    
    private void initRootLayout()
    {
        try 
        {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Authentification/View/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setLoginMain(this);
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    public void verifierConnection(User user)
    {
        user=bddUser.validationConnexion(user);
        if(user.isConnected())
        {
            bddUser.seConnecter(user);
            this.usr=user;
            if(user.isAdmin())
                showSwichView();
            else
                showApplication();
            System.out.println("user :" + usr.getNom());
        }
        else
        {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(primaryStage);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de connexion");
            alert.setContentText("Pseudo ou mot de passe invalide");

            alert.showAndWait();
            
            initRootLayout();
        }
        
    }
    
    private void showUserTableView()
    {
        try 
        {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Authentification/View/UserTableView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // récuperation des données
            UserSData=bddUser.getSimpleUser();
            UserAData=bddUser.getAdminUser();
            UserBData=bddUser.getBanquierUser();
            
            
            // Give the controller access to the main app.
            UserTableViewController controller = loader.getController();
            controller.setLoginMain(this);
            
            this.primaryStage.setTitle("Gerer Utilisateur");
            rootLayout.setCenter(page); 
            primaryStage.close();
            primaryStage.show();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    
    
    public void exit()
    {
        System.exit(0);
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void showApplication() {
            MainApp app= new MainApp(this.usr);
            app.start(new Stage());
            this.primaryStage.close();
    }

    public void showAdminPage() {
        showUserTableView();
    }

    public void showSwichView() {
        try 
        {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Authentification/View/SwichView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            SwichViewController controller = loader.getController();
            controller.setLoginMain(this);
            
            rootLayout.setCenter(page);
            primaryStage.setTitle("Choix d'interface");
            primaryStage.close();
            primaryStage.show();
            
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    public ObservableList<User> getUserSData()
    {
        return UserSData;
    }
    
    public ObservableList<User> getUserAData()
    {
        return UserAData;
    }
    
    public ObservableList<User> getUserBData()
    {
        return UserBData;
    }
    
    public boolean showUserEditDialog(User user) {
            try {
                // Load the fxml file and create a new stage for the popup dialog.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("Authentification/View/UserEditDialog.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Utilisateur");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                // Set the person into the controller.
                UserEditDialogController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setLoginMain(this);
                controller.setUser(user);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();

                return controller.isOkClicked();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        
    }
    
    public Stage getPrimaryStage()
    {
        return this.primaryStage;
    }
    
    public BDDUser getBddUser()
    {
        return this.bddUser;
    }
    
}
