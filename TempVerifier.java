package hygieniapeli;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

/**
 * Input verifier for temperature controllers.
 *
 * @author Kukka
 */
public class TempVerifier extends javax.swing.InputVerifier {
    
    private void putTempToModel (JComponent input) { // Input OK, controller changes the model and the view values.
        ((JPanelCabinet)input.getParent()).setTempValues(
            Integer.parseInt(((JFormattedTextField)input).getText())); //MPC pattern here
    }
    
    private void getTempFromModel (JComponent input) {
        Integer value = ((JPanelCabinet) input.getParent()).getTempValue();
        // Controller changes the view to the old value after bad input.
        ((JFormattedTextField) input).setValue(value);
    }
    
    @Override
    public boolean verify (javax.swing.JComponent input) {
        if (input == null || ! (input instanceof JFormattedTextField)) return true;
        JFormattedTextField tf = (JFormattedTextField) input;
        int temp;
        try {
            temp = Integer.parseInt(tf.getText()); 
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
        return (temp >= Food.lowlimittemp && temp <= Food.highlimittemp);
    }
    
    @Override
    public boolean shouldYieldFocus(JComponent input) {
        if (this.verify(input)) {
            if (input != null && (input instanceof JFormattedTextField))
               putTempToModel(input);
            return true;
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Anna tavanomainen lämpötila yksinkertaisena kokonaislukuna.");
            getTempFromModel(input);
            ((JFormattedTextField) input).repaint();
            return false; 
        }
    }

}
