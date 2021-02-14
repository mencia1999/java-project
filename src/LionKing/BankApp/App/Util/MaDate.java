/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LionKing.BankApp.App.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author LionKing
 */
public class MaDate {
    
    DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
    Date date=null;
    
    
    public MaDate()
    {
        date = new Date();
    }
    
    public MaDate(Date date)
    {
        this.date=date;
    }
    
}
