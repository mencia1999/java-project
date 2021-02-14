/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LionKing.BankApp.Retrait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author DIAVOL'HARINOSY
 */
public class Retrait 
{
    private final StringProperty NumCheque;
    private final StringProperty NumCompt;
    private final StringProperty MontantRetrait;
    private final StringProperty DateRetrait;
    private Date dateR;
    
    /**
     * constructor.
     */
    public Retrait(){
        this(null,null,null,null);
    }

    
    public Retrait(String NumCheque, String NumCompt, String MontantRetrait, String DateRetrait)
    {
        this.NumCheque= new SimpleStringProperty(NumCheque);
        this.NumCompt= new SimpleStringProperty(NumCompt);
        this.MontantRetrait= new SimpleStringProperty(MontantRetrait);
        this.DateRetrait = new SimpleStringProperty(DateRetrait);
        this.dateR=null;
    }
    
    public Date getDate()
    {
        return this.dateR;
    }
    
    public void setDate(Date date)
    {
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.dateR=date;
        this.DateRetrait.set(df.format(date));
    }
    
    public String getNumCheque() {
        return NumCheque.get();
    }
    
    public String getNumCompt() {
        return NumCompt.get();
    }
    
    public String getMontantRetrait() {
        return MontantRetrait.get();
    }
    
    public String getDateRetrait() {
        return DateRetrait.get();
    }
    
    public void setNumCheque(String NumCheque) {
        this.NumCheque.set(NumCheque);
    }
    
    public void setNumCompt(String NumCompt) {
        this.NumCompt.set(NumCompt);
    }
    
    public void setMontantRetrait(String MontantRetrait) {
        this.MontantRetrait.set(MontantRetrait);
    }

    public void setDateRetrait(String DateRetrait) {
        this.DateRetrait.set(DateRetrait);
    }
    
    public StringProperty NumChequeProperty() {
        return NumCheque;
    }
    
    public StringProperty NumComptProperty() {
        return NumCompt;
    }
    
    public StringProperty MontantRetraitProperty() {
        return MontantRetrait;
    }

    public StringProperty DateRetraitProperty() {
        return DateRetrait;
    }
    
}