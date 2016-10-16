package hygieniapeli;

/**
 * Starting the mouse drag from one list to another list.  Both lists have to 
 * have a (different) PlainFoodStorage panel as an ancestor.  There is one list 
 * per each such panel.  Drag is not allowed in other components in the game.
 * There is one mouse drag handler per list.
 * If drag will be allowed in some other components, modify the if-statements in
 * the handler.
 * 
 * @author Kukka
 */
public class MouseDragHandler extends java.awt.event.MouseAdapter {
    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        java.awt.Component c = (java.awt.Component) e.getSource();
        javax.swing.JList lc;
        if ((lc=GameUtilities.castToJList(c))==null) return;
        javax.swing.TransferHandler th;
        if (lc.getDragEnabled() && (th = lc.getTransferHandler()) instanceof ListTransferHandler) {
            ListTransferHandler mylth = (ListTransferHandler) th;
            mylth.initSourceVariable(lc);
            mylth.exportAsDrag(lc, e, javax.swing.TransferHandler.MOVE);
        }
    }
}
