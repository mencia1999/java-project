/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LionKing.Authentification.Util;

import LionKing.Authentification.Model.User;
import LionKing.BankApp.App.Util.BDConnexion;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author LionKing
 */
public class BDDUser {
    //les classe necessaire pour la connexion 
    private Class driver_connexion= null;
    private Connection conn= null;
    private Statement stat=null;
    private ResultSet res=null;
    
    //les paramètre de connexion
    private String url = "jdbc:mysql://localhost/BankApp";
    private String user = "BankApp";
    private String passwd = "lionking";
    
    /*
    *Constructeur de la classe 
    */
    public BDDUser () 
    {
        try {
            // récupération du driver
            driver_connexion = Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // création de la connexion
            conn = (Connection) DriverManager.getConnection(url, user, passwd);
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            //creation d'un objet statement
            stat=conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    /**
     *creation d'un nouveau utilisateur
     */
    public int createUser(User usr)
    {
        int rows=0;
        String requete="insert into user (pseudo,pswd,nom,prenom,isConnected,enabled) values (\""+usr.getPseudo()+"\",\""
                                                                                                                +usr.getPswd()+"\",\""
                                                                                                                +usr.getNom()+"\",\""
                                                                                                                +usr.getPrenom()+"\","
                                                                                                                +usr.isConnected()+","
                                                                                                                +usr.isEnabled()
                                                                                                                +");";
        
        try {
            rows = stat.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rows;
    }
    
    /**
     *verifie si le pseudo d'un utilisateur existe déja;
     * 
     * return true si exist sinon false
     */
    public boolean UserPseudoExist(String pseudo)
    {
        boolean rp=false;
        String requete="select pseudo from user where pseudo=\""+pseudo+"\";";
        
        try {
            res=stat.executeQuery(requete);
            
            if(res.next())
            {
                if(pseudo.equals((String)res.getString("pseudo")))
                    rp=true;
            }
                    
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rp;
    }
    
    /**
     *verifie si le pseudo d'un utilisateur existe déja et verifie le mot de passe;
     * 
     * return true si exist sinon false
     */
    public User validationConnexion(User usr)
    {
        String requete="select * from user where pseudo=\""+usr.getPseudo()+"\";";
        
        try {
            res=stat.executeQuery(requete);
            while(res.next())
            {
                if(usr.getPseudo().equals((String)res.getString("pseudo")) && usr.getPswd().equals((String)res.getString("pswd")))
                {
                    // affectation des données dans l'objet user 
                    usr.setPseudo(res.getString("pseudo"));
                    usr.setPswd(res.getString("pswd"));
                    usr.setNom(res.getString("nom"));
                    usr.setPrenom(res.getString("prenom"));
                    usr.setIsConnected(); 
                    usr.setIsEnabled((Boolean)res.getBoolean("enabled"));
                    usr.setRole(res.getInt("role"));
                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return usr;
    }
    
    
    public Boolean seDeconnecter(User usr)
    {
        String requete = " update user set isConnected=0 where pseudo=\""+usr.getPseudo()+"\";";
        
        try {
            int rows=stat.executeUpdate(requete);
            if(rows>0)
                return true;
            else
                return false;
            
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public Boolean seConnecter(User usr)
    {
        String requete = " update user set isConnected=1 where pseudo=\""+usr.getPseudo()+"\";";
        
        try {
            int rows=stat.executeUpdate(requete);
            if(rows>0)
                return true;
            else
                return false;
            
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ObservableList<User> getSimpleUser()
    {
        ObservableList<User> UserData= FXCollections.observableArrayList();

        String requete="select * from user where role!=2 and role!=1 ;";
        
        try {
            res=stat.executeQuery(requete);
            while(res.next())
            {
                User usr=new User();
                
                // affectation des données dans l'objet user 
                usr.setPseudo(res.getString("pseudo"));
                usr.setPswd(res.getString("pswd"));
                usr.setNom(res.getString("nom"));
                usr.setPrenom(res.getString("prenom"));
                usr.setEmail(res.getString("email"));
                usr.setCin(res.getString("cin"));
                usr.setIsConnectedValue(res.getBoolean("isconnected")); 
                usr.setIsEnabled((Boolean)res.getBoolean("enabled"));
                
                UserData.add(usr);
           
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return UserData; 
    }
    
    public ObservableList<User> getAdminUser()
    {
        ObservableList<User> UserData= FXCollections.observableArrayList();

        String requete="select * from user where role=1 ;";
        
        try {
            res=stat.executeQuery(requete);
            while(res.next())
            {
                User usr=new User();
                
                // affectation des données dans l'objet user 
                usr.setPseudo(res.getString("pseudo"));
                usr.setPswd(res.getString("pswd"));
                usr.setNom(res.getString("nom"));
                usr.setPrenom(res.getString("prenom"));
                usr.setEmail(res.getString("email"));
                usr.setCin(res.getString("cin"));
                usr.setIsConnectedValue(res.getBoolean("isconnected")); 
                usr.setIsEnabled((Boolean)res.getBoolean("enabled"));
                
                UserData.add(usr);
           
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return UserData; 
    }
    
    public ObservableList<User> getBanquierUser()
    {
        ObservableList<User> UserData= FXCollections.observableArrayList();

        String requete="select * from user where role=2 ;";
        
        try {
            res=stat.executeQuery(requete);
            while(res.next())
            {
                User usr=new User();
                
                // affectation des données dans l'objet user 
                usr.setPseudo(res.getString("pseudo"));
                usr.setPswd(res.getString("pswd"));
                usr.setNom(res.getString("nom"));
                usr.setPrenom(res.getString("prenom"));
                usr.setEmail(res.getString("email"));
                usr.setCin(res.getString("cin"));
                usr.setIsConnectedValue(res.getBoolean("isconnected")); 
                usr.setIsEnabled((Boolean)res.getBoolean("enabled"));
                
                UserData.add(usr);
           
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return UserData; 
    }
    
    public int updateUser(User usr,String pseudo)
    {
        int rows=0;
        String requete="update user set pseudo=\""+usr.getPseudo()+"\", pswd = \""+usr.getPswd()+"\", nom =\""  +usr.getNom()+"\", prenom=\""+usr.getPrenom()+"\", email=\""+usr.getEmail()+"\", cin =\"" + usr.getCin() +"\" where pseudo = \"" + pseudo +  " \";";
        
        try {
            rows = stat.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rows;
        
    }
    
    public int deleteUser(String pseudo)
    {
        int rows=0;
        String requete="delete from user where pseudo=\""+pseudo+"\" ;";
        
        try {
            rows = stat.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rows;
    }
    
    public int setNotEnabled(String pseudo)
    {
        int rows=0;
        String requete="update user set enabled=false where pseudo=\""+pseudo+"\" ;";
        
        try {
            rows = stat.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rows;
    }
    
    public int setEnabled(String pseudo)
    {
        int rows=0;
        String requete="update user set enabled=true where pseudo=\""+pseudo+"\" ;";
        
        try {
            rows = stat.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rows;
    }
    
    public int setAdmin(String pseudo)
    {
        int rows=0;
        String requete="update user set role=1 where pseudo=\""+pseudo+"\" ;";
        
        try {
            rows = stat.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rows;
    }
    
    public int setBanquier(String pseudo)
    {
        int rows=0;
        String requete="update user set role=2 where pseudo=\""+pseudo+"\" ;";
        
        try {
            rows = stat.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(BDDUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rows;
    }
    
}
