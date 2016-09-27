/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygieniapeli;

/**
 *
 * @author Kukka
 */
public class GameUtilities {
    public static javax.swing.JList castToJList (java.awt.Component c) {
        if (c==null || !(c instanceof javax.swing.JList)) return null;
        return (javax.swing.JList) c;
    }
}
