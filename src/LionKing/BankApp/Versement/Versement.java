/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LionKing.BankApp.Versement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author DIAVOL'HARINOSY
 */
public class Versement 
{
    private final StringProperty NumBV;
    private final StringProperty NumCompt;
    private final StringProperty MontantVersement;
    private final StringProperty DateVersement;
    private Date dateV;
    
    /**
     * constructor.
     */
    public Versement(){
        this(null,null,null,null);
    }

    
    public Versement(String NumBV, String NumCompt, String MontantVersement, String DateVersement)
    {
        this.NumBV= new SimpleStringProperty(NumBV);
        this.NumCompt= new SimpleStringProperty(NumCompt);
        this.MontantVersement= new SimpleStringProperty(MontantVersement);
        this.DateVersement = new SimpleStringProperty(DateVersement);
        this.dateV=null;
    }
    
    public Date getDate()
    {
        return this.dateV;
    }
    
    public void setDate(Date date)
    {
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.dateV=date;
        this.DateVersement.set(df.format(date));
    }
            
    
    public String getNumBV() {
        return NumBV.get();
    }
    
    public String getNumCompt() {
        return NumCompt.get();
    }
    
    public String getMontantVersement() {
        return MontantVersement.get();
    }
    
    public String getDateVersement() {
        return DateVersement.get();
    }
    
    public void setNumBV(String NumBV) {
        this.NumBV.set(NumBV);
    }
    
    public void setNumCompt(String NumCompt) {
        this.NumCompt.set(NumCompt);
    }
    
    public void setMontantVersement(String MontantVersement) {
        this.MontantVersement.set(MontantVersement);
    }

    public void setDateVersement(String DateVersement) {
        this.DateVersement.set(DateVersement);
    }
    
    public StringProperty NumBVProperty() {
        return NumBV;
    }
    
    public StringProperty NumComptProperty() {
        return NumCompt;
    }
    
    public StringProperty MontantVersementProperty() {
        return MontantVersement;
    }

    public StringProperty DateVersementProperty() {
        return DateVersement;
    }
    
}
