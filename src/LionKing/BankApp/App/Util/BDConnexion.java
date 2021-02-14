/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LionKing.BankApp.App.Util;

import LionKing.BankApp.Client.Client;
import LionKing.BankApp.Retrait.Retrait;
import LionKing.BankApp.Versement.Versement;
import com.mysql.jdbc.Connection;
import static java.lang.Double.parseDouble;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author lionKing
 */
public class BDConnexion {
    
    //les classe necessaire pour la connexion 
    private Class driver_connexion= null;
    private Connection conn= null;
    private Statement stat=null; 
    private ResultSet res=null;
    
    //les paramètre de connexion
    private String url = "jdbc:mysql://localhost/BankApp";
    private String user = "BankApp";
    private String passwd = "lionking";
    
    public BDConnexion () 
    {
        
           
        try {
            driver_connexion = Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            conn = (Connection) DriverManager.getConnection(url, user, passwd);
             stat=conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
    /**
    *fonction de création de client
    */
    public boolean CreateClient(Client client)
    {
        
        
        try {
            String requete="insert into Client (NumCompt,Nom,Prenom,AdressClient,NumCIN,Solde,NumTel,Email) values (\""+client.getNumCompt()+"\",\""
                                                                                                                    +client.getNom()+"\",\""
                                                                                                                    +client.getPrenom()+"\",\""
                                                                                                                    +client.getAdressClient()+"\",\""
                                                                                                                    +client.getNumCIN()+"\","
                                                                                                                    +client.getSolde()+",\""
                                                                                                                    +client.getNumTel()+"\",\""
                                                                                                                    +client.getEmail()+"\""
                                                                                                                    +");";    

            int rows=stat.executeUpdate(requete);
            if(rows>0)
                return true;
            else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        
    }
    
    public ObservableList<Client> getAllClient()
    {
        ObservableList<Client> ClientData = FXCollections.observableArrayList();
        
        try {
            
            res=stat.executeQuery("select * from Client ;");
            
            while(res.next())
            {   
                Client client=new Client();
                    
                // affectation des données dans l'objet user 
                client.setNumCompt(res.getString("NumCompt"));
                client.setNom(res.getString("Nom"));
                client.setPrenom(res.getString("prenom"));
                client.setAdressClient(res.getString("AdressClient"));
                client.setNumCIN(res.getString("NumCIN"));
                client.setSolde(res.getString("Solde"));
                client.setNumTel(res.getString("NumTel"));
                client.setEmail(res.getString("Email"));
                
                ClientData.add(client);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ClientData;
    }
    
    public boolean DeleteClient(Client client)
    {
        try {
            
            int rows=stat.executeUpdate("delete from Client where NumCompt=\""+client.getNumCompt()+"\";");
            
            if(rows>0)
                return true;
            else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public boolean UpdateClient(Client client)
    {
       try {
            
            int rows=stat.executeUpdate("update client set Nom=\""+client.getNom()+"\" ,Prenom =\"" +client.getPrenom()+ "\" ,AdressClient = \"" +client.getAdressClient()+ "\" ,NumCIN = \""+client.getNumCIN()+ "\" ,NumTel = \"" +client.getNumTel()+ "\" ,Email = \"" +client.getEmail()+ "\" where NumCompt=\""+client.getNumCompt()+"\";");
            System.out.println("bdd : "+client.getNumCompt());
            System.out.println("bdd : "+client.getSolde());
            System.out.println("coucouc je suis là"+rows);
            if(rows>0)
                return true;
            else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
    }
    
    /**
    *fonction de création, modification et suppression de Versement
    */
    public boolean CreateVersement(Versement versement)
    {
        double NewSolde;
        NewSolde= getSolde(versement.getNumCompt());
        NewSolde= NewSolde + parseDouble(versement.getMontantVersement());
        UpdateSoldeClient(versement.getNumCompt(), NewSolde);
        
        try {
            String requete="insert into versement (NumBV,MontantVersement,DateVersement,NumCompt) values (\""+versement.getNumBV()+"\","
                                                                                                                    +versement.getMontantVersement()+",\""
                                                                                                                    +versement.getDateVersement()+"\",\""                                                                                                                    
                                                                                                                    +versement.getNumCompt()+"\""
                                                                                                                    +");";     

            int rows=stat.executeUpdate(requete);
            if(rows>0)
                return true;
            else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ObservableList<Versement> getAllVersement()
    {
        ObservableList<Versement> VersementData = FXCollections.observableArrayList();
        
        try {
            
            res=stat.executeQuery("select * from Versement ;");
            
            while(res.next())
            {   
                Versement versement=new Versement();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                // affectation des données dans l'objet user 
                versement.setNumBV(res.getString("NumBV"));
                versement.setNumCompt(res.getString("NumCompt"));
                versement.setDate(dateFormat.parse(res.getString("DateVersement"))); 
                versement.setMontantVersement(res.getString("MontantVersement"));
                
                VersementData.add(versement);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return VersementData;
    }
    
    public boolean DeleteVersement(Versement versement)
    {
        double NewSolde;
        NewSolde= getSolde(versement.getNumCompt());
        NewSolde= NewSolde - parseDouble(versement.getMontantVersement());
        UpdateSoldeClient(versement.getNumCompt(), NewSolde);
        
        try {
            
            int rows=stat.executeUpdate("delete from Versement where NumBV=\""+versement.getNumBV()+"\";");
            
            if(rows>0)
                return true;
            else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public boolean UpdateVersement(Versement versement,Versement v)
    {
       try {
           double NewSolde;
           NewSolde= getSolde(versement.getNumCompt());
           NewSolde= NewSolde - parseDouble(v.getMontantVersement()) + parseDouble(versement.getMontantVersement());
           UpdateSoldeClient(versement.getNumCompt(), NewSolde);
           
            int rows=stat.executeUpdate("update Versement set MontantVersement=\""+versement.getMontantVersement()+"\"  where NumBV=\""+versement.getNumBV()+"\";");
            if(rows>0)
                return true;
            else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
    }
    
    private double getSolde(String NumCompt)
    {
        double rp=0d;
        
        try {
            res=stat.executeQuery("select Solde from Client where NumCompt=\""+ NumCompt +"\" ;");
            while(res.next())
            {
                rp=res.getDouble("Solde");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rp;
    }
    
    private boolean UpdateSoldeClient(String NumCompt, Double NewSolde)
    {
        try {
            
            int rows=stat.executeUpdate("update client set Solde=\""+NewSolde+"\" where NumCompt=\""+NumCompt+"\";");

            if(rows>0)
                return true;
            else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
    }
    
    /**
    *fonction de création, modification et suppression de Retrait
    */
    public boolean CreateRetrait(Retrait retrait)
    {
        double NewSolde;
        NewSolde= getSolde(retrait.getNumCompt());
        NewSolde= NewSolde - parseDouble(retrait.getMontantRetrait());
        UpdateSoldeClient(retrait.getNumCompt(), NewSolde);
        
        try {
            String requete="insert into Retrais (NumCheque,MontantRetrait,DateRetrait,NumCompt) values (\""+retrait.getNumCheque()+"\","
                                                                                                                    +retrait.getMontantRetrait()+",\""
                                                                                                                    +retrait.getDateRetrait()+"\",\""                                                                                                                    
                                                                                                                    +retrait.getNumCompt()+"\""
                                                                                                                    +");";     

            int rows=stat.executeUpdate(requete);
            if(rows>0)
                return true;
            else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ObservableList<Retrait> getAllRetrait()
    {
        ObservableList<Retrait> RetraitData = FXCollections.observableArrayList();
        
        try {
            
            res=stat.executeQuery("select * from Retrais ;");
            
            while(res.next())
            {   
                Retrait retrait=new Retrait();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                // affectation des données dans l'objet user 
                retrait.setNumCheque(res.getString("NumCheque"));
                retrait.setNumCompt(res.getString("NumCompt"));
                retrait.setDate(dateFormat.parse(res.getString("DateRetrait"))); 
                retrait.setMontantRetrait(res.getString("MontantRetrait"));
                
                RetraitData.add(retrait);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return RetraitData;
    }
    
    public boolean DeleteRetrait(Retrait retrait)
    {
        double NewSolde;
        NewSolde= getSolde(retrait.getNumCompt());
        NewSolde= NewSolde + parseDouble(retrait.getMontantRetrait());
        UpdateSoldeClient(retrait.getNumCompt(), NewSolde);
        
        try {
            
            int rows=stat.executeUpdate("delete from Retrais where NumCheque=\""+retrait.getNumCheque()+"\";");
            
            if(rows>0)
                return true;
            else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public boolean UpdateRetrait(Retrait retrait,Retrait v)
    {
       try {
           double NewSolde;
           NewSolde= getSolde(retrait.getNumCompt());
           NewSolde= NewSolde + parseDouble(v.getMontantRetrait()) - parseDouble(retrait.getMontantRetrait());
           UpdateSoldeClient(retrait.getNumCompt(), NewSolde);
           
            int rows=stat.executeUpdate("update Retrais set MontantRetrait=\""+retrait.getMontantRetrait()+"\" where NumCheque=\""+retrait.getNumCheque()+"\";");
            if(rows>0)
                return true;
            else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
    }
    
        public ObservableList<Versement> getClientVersement(Client client) {
        ObservableList<Versement> VersementData = FXCollections.observableArrayList();
        
        try {
            
            res=stat.executeQuery("select * from Versement where NumCompt='"+client.getNumCompt() +"' ;");
            
            while(res.next())
            {   
                Versement versement=new Versement();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                // affectation des données dans l'objet user 
                versement.setNumBV(res.getString("NumBV"));
                versement.setNumCompt(res.getString("NumCompt"));
                versement.setDate(dateFormat.parse(res.getString("DateVersement"))); 
                versement.setMontantVersement(res.getString("MontantVersement"));
                
                VersementData.add(versement);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return VersementData;
    }
        
    public ObservableList<Versement> getClientVersementByMonth(Client client, int mois) {
        ObservableList<Versement> VersementData = FXCollections.observableArrayList();
        
        try {
            
            res=stat.executeQuery("select * from Versement where NumCompt='"+client.getNumCompt() +"' ;");
            
            while(res.next())
            {   
                Versement versement=new Versement();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                // affectation des données dans l'objet user 
                versement.setNumBV(res.getString("NumBV"));
                versement.setNumCompt(res.getString("NumCompt"));
                versement.setDate(dateFormat.parse(res.getString("DateVersement"))); 
                versement.setMontantVersement(res.getString("MontantVersement"));
                
                if(versement.getDate().getMonth()==mois)
                    VersementData.add(versement);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return VersementData;
    }
    
    public ObservableList<Versement> getClientVersementByYear(Client client, int annee) {
        ObservableList<Versement> VersementData = FXCollections.observableArrayList();
        
        try {
            
            res=stat.executeQuery("select * from Versement where NumCompt='"+client.getNumCompt() +"' ;");
            
            while(res.next())
            {   
                Versement versement=new Versement();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                // affectation des données dans l'objet user 
                versement.setNumBV(res.getString("NumBV"));
                versement.setNumCompt(res.getString("NumCompt"));
                versement.setDate(dateFormat.parse(res.getString("DateVersement"))); 
                versement.setMontantVersement(res.getString("MontantVersement"));
                
                if((versement.getDate().getYear()+1900)==annee)
                    VersementData.add(versement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return VersementData;
    }

    public ObservableList<Retrait> getClientRetrait(Client client) {
        ObservableList<Retrait> RetraitData = FXCollections.observableArrayList();
        
        try {
            
            res=stat.executeQuery("select * from Retrais where NumCompt='"+ client.getNumCompt() +"' ;");
            
            while(res.next())
            {   
                Retrait retrait=new Retrait();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                // affectation des données dans l'objet user 
                retrait.setNumCheque(res.getString("NumCheque"));
                retrait.setNumCompt(res.getString("NumCompt"));
                retrait.setDate(dateFormat.parse(res.getString("DateRetrait"))); 
                retrait.setMontantRetrait(res.getString("MontantRetrait"));
                
                RetraitData.add(retrait);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return RetraitData;
    }
    
    public ObservableList<Retrait> getClientRetraitByMonth(Client client,int mois) {
        ObservableList<Retrait> RetraitData = FXCollections.observableArrayList();
        
        try {
            
            res=stat.executeQuery("select * from Retrais where NumCompt='"+ client.getNumCompt() +"' ;");
            
            while(res.next())
            {   
                Retrait retrait=new Retrait();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                // affectation des données dans l'objet user 
                retrait.setNumCheque(res.getString("NumCheque"));
                retrait.setNumCompt(res.getString("NumCompt"));
                retrait.setDate(dateFormat.parse(res.getString("DateRetrait"))); 
                retrait.setMontantRetrait(res.getString("MontantRetrait"));
                
                if(retrait.getDate().getMonth()==mois)
                    RetraitData.add(retrait);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return RetraitData;
    }
    
    public ObservableList<Retrait> getClientRetraitByYear(Client client,int annee) {
        ObservableList<Retrait> RetraitData = FXCollections.observableArrayList();
        
        try {
            
            res=stat.executeQuery("select * from Retrais where NumCompt='"+ client.getNumCompt() +"' ;");
            
            while(res.next())
            {   
                Retrait retrait=new Retrait();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                // affectation des données dans l'objet user 
                retrait.setNumCheque(res.getString("NumCheque"));
                retrait.setNumCompt(res.getString("NumCompt"));
                retrait.setDate(dateFormat.parse(res.getString("DateRetrait"))); 
                retrait.setMontantRetrait(res.getString("MontantRetrait"));
                
                if((retrait.getDate().getYear()+1900)==annee)
                    RetraitData.add(retrait);                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return RetraitData;
    }
    
    public ObservableList<Client> searcheByNom(String mot)
    {
        ObservableList<Client> ClientData = FXCollections.observableArrayList();
        String requete="SELECT * FROM `client` WHERE nom like \"%"+mot+"%\" or prenom like \"%"+mot+"%\";";
        
       
        try {
            res= stat.executeQuery(requete);
            
            while(res.next())
            {   
                Client client=new Client();
                    
                // affectation des données dans l'objet user 
                client.setNumCompt(res.getString("NumCompt"));
                client.setNom(res.getString("Nom"));
                client.setPrenom(res.getString("prenom"));
                client.setAdressClient(res.getString("AdressClient"));
                client.setNumCIN(res.getString("NumCIN"));
                client.setSolde(res.getString("Solde"));
                client.setNumTel(res.getString("NumTel"));
                client.setEmail(res.getString("Email"));
                
                ClientData.add(client);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return ClientData;
    }
    
    public ObservableList<Client> searcheByNumCompt(String mot)
    {
        ObservableList<Client> ClientData = FXCollections.observableArrayList();
        String requete="SELECT * FROM `client` WHERE NumCompt like \"%"+mot+"%\" ;";
        
       
        try {
            res= stat.executeQuery(requete);
            
            while(res.next())
            {   
                Client client=new Client();
                    
                // affectation des données dans l'objet user 
                client.setNumCompt(res.getString("NumCompt"));
                client.setNom(res.getString("Nom"));
                client.setPrenom(res.getString("prenom"));
                client.setAdressClient(res.getString("AdressClient"));
                client.setNumCIN(res.getString("NumCIN"));
                client.setSolde(res.getString("Solde"));
                client.setNumTel(res.getString("NumTel"));
                client.setEmail(res.getString("Email"));
                
                ClientData.add(client);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ClientData;
    }
    
     public ObservableList<Retrait> searcheByNumCheque(String mot)
    {
        ObservableList<Retrait> RetraitData = FXCollections.observableArrayList();
        String requete="SELECT * FROM `retrais` WHERE NumCheque like \"%"+mot+"%\" ;";
        
       
        try {
            res= stat.executeQuery(requete);
            
            while(res.next())
            {   
                Retrait retrait=new Retrait();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                // affectation des données dans l'objet user 
                retrait.setNumCheque(res.getString("NumCheque"));
                retrait.setNumCompt(res.getString("NumCompt"));
                retrait.setDate(dateFormat.parse(res.getString("DateRetrait"))); 
                retrait.setMontantRetrait(res.getString("MontantRetrait"));
                
                RetraitData.add(retrait);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return RetraitData;
    }
     
    public ObservableList<Versement> searcheByNumBordereau(String mot)
    {
        ObservableList<Versement> VersementData = FXCollections.observableArrayList();
        String requete="SELECT * FROM `versement` WHERE NumBV like \"%"+mot+"%\" ;";
        
       
        try {
            res= stat.executeQuery(requete);
            
            while(res.next())
            {   
                Versement versement=new Versement();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                // affectation des données dans l'objet user 
                versement.setNumBV(res.getString("NumBV"));
                versement.setNumCompt(res.getString("NumCompt"));
                versement.setDate(dateFormat.parse(res.getString("DateVersement"))); 
                versement.setMontantVersement(res.getString("MontantVersement"));
                
                VersementData.add(versement);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return VersementData;
    }
    
    public ObservableList<Versement> searcheByNumComptVersement(String mot)
    {
        ObservableList<Versement> VersementData = FXCollections.observableArrayList();
        String requete="SELECT * FROM `versement` WHERE NumCompt like \"%"+mot+"%\" ;";
        
       
        try {
            res= stat.executeQuery(requete);
            
            while(res.next())
            {   
                Versement versement=new Versement();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                // affectation des données dans l'objet user 
                versement.setNumBV(res.getString("NumBV"));
                versement.setNumCompt(res.getString("NumCompt"));
                versement.setDate(dateFormat.parse(res.getString("DateVersement"))); 
                versement.setMontantVersement(res.getString("MontantVersement"));
                
                VersementData.add(versement);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return VersementData;
    }
    
    public ObservableList<Retrait> searcheByNumComptRetrait(String mot)
    {
        ObservableList<Retrait> RetraitData = FXCollections.observableArrayList();
        String requete="SELECT * FROM `retrais` WHERE NumCompt like \"%"+mot+"%\" ;";
        
       
        try {
            res= stat.executeQuery(requete);
            
            while(res.next())
            {   
                Retrait retrait=new Retrait();
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                // affectation des données dans l'objet user 
                retrait.setNumCheque(res.getString("NumCheque"));
                retrait.setNumCompt(res.getString("NumCompt"));
                retrait.setDate(dateFormat.parse(res.getString("DateRetrait"))); 
                retrait.setMontantRetrait(res.getString("MontantRetrait"));
                
                RetraitData.add(retrait);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return RetraitData;
    }
    
    public ObservableList<String> getAllNumCompte()
    {
        ObservableList<String> NumComptData = FXCollections.observableArrayList();
        
        try {
            
            res=stat.executeQuery("select NumCompt from Client ;");
            
            while(res.next())
            {   
                String Num=res.getString("NumCompt");
                
                NumComptData.add(Num);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return NumComptData;
    }
    
    public ObservableList<String> getAllNumCheque()
    {
        ObservableList<String> NumChequeData = FXCollections.observableArrayList();
        
        try {
            
            res=stat.executeQuery("select NumCheque from Retrais ;");
            
            while(res.next())
            {   
                String Num=res.getString("NumCheque");
                
                NumChequeData.add(Num);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return NumChequeData;
    }
    
    public ObservableList<String> getAllNumBordereau()
    {
        ObservableList<String> NumBVData = FXCollections.observableArrayList();
        
        try {
            
            res=stat.executeQuery("select NumBV from Versement ;");
            
            while(res.next())
            {   
                String Num=res.getString("NumBV");
                
                NumBVData.add(Num);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return NumBVData;
    }
     
}
