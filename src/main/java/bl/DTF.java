/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author elisc
 */
public enum DTF {
    
    STANDARD_DATE(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        
    private DateTimeFormatter dtf;
    
    private DTF(DateTimeFormatter dtf){
        this.dtf = dtf;
    }
    
    public DateTimeFormatter value(){
        return dtf;
    }
    
}
