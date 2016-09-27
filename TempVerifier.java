/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygieniapeli;
import java.awt.Component;
/**
 * Input verifier for temperature controllers.
 *
 * @author Kukka
 */
public class TempVerifier extends javax.swing.InputVerifier {
    
    javax.swing.JFormattedTextField myTextField;
    String defaultValue;
    
    TempVerifier(javax.swing.JFormattedTextField pMyTextField, Integer pDefaultValue) {
        myTextField = pMyTextField;
        defaultValue = pDefaultValue.toString().trim();
    }
    
    @Override
    public boolean verify (javax.swing.JComponent input) {
        if (input == null || ! (input instanceof javax.swing.JFormattedTextField)) return true;
        javax.swing.JFormattedTextField tf = (javax.swing.JFormattedTextField) input;
        try { int temp = Integer.parseInt(tf.getText()); 
            if (temp < Food.lowlimittemp || temp > Food.highlimittemp) {
                return false; // I would have defined temp outside the try-block
                // and put this if-statement right after the try- and catch-blocks,
                // but this stupid compiler then says that the if-statement is redundant
            }
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean shouldYieldFocus(javax.swing.JComponent input) {
        if (this.verify(input)) {
            return true;
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Anna tavanomainen lämpötila yksinkertaisena kokonaislukuna.");
            myTextField.setText(defaultValue);
            myTextField.repaint();
            return false; 
        }
    }
}
