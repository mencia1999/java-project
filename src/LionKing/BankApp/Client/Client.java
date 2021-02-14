/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LionKing.BankApp.Client;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



/**
 *
 * @author DIAVOL'HARINOSY
 */
public class Client {
  
    private final StringProperty NumCompt;
    private final StringProperty Nom;
    private final StringProperty Prenom;
    private final StringProperty AdressClient;
    private final StringProperty NumCIN;
    private final StringProperty Solde;
    private final StringProperty NumTel;
    private final StringProperty Email;
    
    /**
     * constructor.
     */
    public Client(){
        this(null,null,null,null);
    }
    
    public Client(String NumCompt,String Nom, String Prenom, String Solde ){
        this.NumCompt= new SimpleStringProperty(NumCompt);
        this.Nom= new SimpleStringProperty(Nom);
        this.Prenom= new SimpleStringProperty(Prenom);
        this.AdressClient= new SimpleStringProperty("");
        this.NumCIN= new SimpleStringProperty("");
        this.Solde= new SimpleStringProperty(Solde);
        this.NumTel= new SimpleStringProperty("");
        this.Email= new SimpleStringProperty("");
    }
    
    public String getNumCompt() {
        return NumCompt.get();
    }
    
    public String getNom() {
        return Nom.get();
    }
    
    public String getPrenom() {
        return Prenom.get();
    }
    
    public String getAdressClient() {
        return AdressClient.get();
    }
    
    public String getNumCIN() {
        return NumCIN.get();
    }
    
    public String getSolde() {
        return Solde.get();
    }
    
    public String getNumTel() {
        return NumTel.get();
    }
    
    public String getEmail() {
        return Email.get();
    }

    public void setNumCompt(String NumCompt) {
        this.NumCompt.set(NumCompt);
    }
    
    public void setNom(String Nom) {
        this.Nom.set(Nom);
    }
    
    public void setPrenom(String Prenom) {
        this.Prenom.set(Prenom);
    }
    
    public void setAdressClient(String AdressClient) {
        this.AdressClient.set(AdressClient);
    }
    
    public void setNumCIN(String NumCIN) {
        this.NumCIN.set(NumCIN);
    }
    
    public void setSolde(String Solde) {
        this.Solde.set(Solde);
    }
    
    public void setNumTel(String NumTel) {
        this.NumTel.set(NumTel);
    }
    
    public void setEmail(String Email) {
        this.Email.set(Email);
    }
    
    public StringProperty NumComptProperty() {
        return NumCompt;
    }
    
    public StringProperty NomProperty() {
        return Nom;
    }
    
    public StringProperty PrenomProperty() {
        return Prenom;
    }
    
    public StringProperty AdressClientProperty() {
        return AdressClient;
    }
    
    public StringProperty NumCINProperty() {
        return NumCIN;
    }
    
    public StringProperty SoldeProperty() {
        return Solde;
    }
    
    public StringProperty NumTelProperty() {
        return NumTel;
    }
    
    public StringProperty EmailProperty() {
        return Email;
    }
    
}
