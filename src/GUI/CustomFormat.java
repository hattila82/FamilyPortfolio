/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 *
 * @author HorvathAttila
 */
public class CustomFormat extends AbstractFormatter {

    @Override
    public Object stringToValue(String text) throws ParseException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value!=null){
        Calendar cal = (Calendar) value; 
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(cal.getTime());
       
        return strDate;}
        else return"";
    }
    
}
