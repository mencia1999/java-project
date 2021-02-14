/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LionKing.Authentification.Model;

import java.sql.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author LionKing
 */
public class User 
{
    private final StringProperty pseudo;
    private final StringProperty pswd;
    private final StringProperty nom;
    private final StringProperty prenom;
    private final StringProperty email;
    private final StringProperty cin; // facultatif 
    private BooleanProperty isConnected;
    private BooleanProperty enabled;
    private int role=-1;
    
    
    /*
    *constructeur
    */
    public User()
    {
          this(null,null,null,null,null,null);
    }
    
    /*
    *surcharge de la constructeur
    */
    public User(String pseudo, String pswd, String nom , String prenom, String email, String cin)
    {
        this.pseudo=new SimpleStringProperty(pseudo);
        this.pswd=new SimpleStringProperty(pswd);
        this.nom=new SimpleStringProperty(nom);
        this.prenom=new SimpleStringProperty(prenom);
        this.email=new SimpleStringProperty(email);
        this.cin=new SimpleStringProperty(cin);
        this.isConnected=new SimpleBooleanProperty(false);
        this.enabled=new SimpleBooleanProperty(false);
    }
    
    /*
    *surcharge de la constructeur
    */
    public User(String pseudo, String pswd)
    {
        this.pseudo=new SimpleStringProperty(pseudo);
        this.pswd=new SimpleStringProperty(pswd);
        this.nom=null;
        this.prenom=null;
        this.email=null;
        this.cin=null;
        this.isConnected=new SimpleBooleanProperty(false);
        this.enabled=new SimpleBooleanProperty(false);
    }
    
    /**
     * test si l'utilisateur est identifier
     * 
     * return la valeur de isConnected
     */
    public Boolean isConnected()
    {
        return isConnected.get();
    }
    
    /**
     * test si le compte de l'utlisateur est activé 
     * 
     * return la valeur de Enabled
     */
    public Boolean isEnabled()
    {
        return enabled.get();
    }
    
    public int getRole()
    {
        return this.role;
    }
    
    public void setRole(int role)
    {
        this.role=role;
    }
    
    /**
     *getter de pseudo
     */
    public String getPseudo()
    {
        return (String)pseudo.get();
    }
    
    /**
     *getter de pswd
     */
    public String getPswd()
    {
        return pswd.get();
    }
    
    /**
     *getter de nom
     */
    public String getNom()
    {
        return nom.get();
    }
    
    /**
     *getter de prenom
     */
    public String getPrenom()
    {
        return prenom.get();
    }
    
    /**
     *getter de pseudo
     */
    public String getEmail()
    {
        return email.get();
    }
    
    /**
     *getter  de Cin
     */
    public String getCin()
    {
        return cin.get();
    }
    
    /**
     *setter  de pseudo
     */
    public void setPseudo(String pseudo)
    {
        this.pseudo.set(pseudo);
    }
    
    /**
     *setter  de pswd
     */
    public void setPswd(String pswd)
    {
        this.pswd.set(pswd);
    }
    
    /**
     *setter  de nom
     */
    public void setNom(String nom)
    {
        this.nom.set(nom);
    }
    
    /**
     *setter  de prenom
     */
    public void setPrenom(String prenom)
    {
        this.prenom.set(prenom);
    }
    
    /**
     *setter  de email
     */
    public void setEmail(String email)
    {
        this.email.set(email);
    }
    
    /**
     *setter  de cin
     */
    public void setCin(String cin)
    {
        this.cin.set(cin);
    }
    
    /**
     *modifie l'etat d'identification de l'utilsateur 
     */
    public void setIsConnected()
    {
        this.isConnected.set(true);
    }
    
    /**
     *modifie l'etat d'identification de l'utilsateur 
     */
    public void setIsConnectedValue(Boolean isConnected)
    {
        this.isConnected.set(isConnected);
    }
    
    /**
     *modifie l'etat du compte  de l'utilsateur 
     */
    public void setIsEnabled(Boolean val)
    {
        this.enabled.set(val);
    }
    
    public StringProperty PseudoProperty() {
        return pseudo;
    }
    
    public StringProperty PswdProperty() {
        return pswd;
    }
    
    public StringProperty NomProperty() {
        return nom;
    }
    
    public StringProperty PrenomProperty() {
        return prenom;
    }
    
    public StringProperty EmailProperty() {
        return email;
    }
    
    public StringProperty CinProperty() {
        return cin;
    }
    
    public BooleanProperty IsConnectedProperty() {
        return isConnected;
    }
    
    public BooleanProperty EnabledProperty() {
        return enabled;
    }
    
    public Boolean isAdmin()
    {
        Boolean rp=false;
            if(this.role==1)
                rp=true;
        return rp;
    }
    
    public Boolean isBanquier()
    {
        Boolean rp=false;
            if(this.role==2)
                rp=true;
        return rp;
    }
    
}
