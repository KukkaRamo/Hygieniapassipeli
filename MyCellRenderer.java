package hygieniapeli;

/**
 * This is the cell renderer class used in lists of PlainFoodStorage panels.
 * Each list has its own cell renderer.
 * @author Kukka
 */
public class MyCellRenderer extends javax.swing.JLabel implements javax.swing.ListCellRenderer<Object> {

    @Override
    public MyCellRenderer getListCellRendererComponent(
     javax.swing.JList<?> list,           // the list
     Object value,            // value to display
     int index,               // cell index
     boolean isSelected,      // is the cell selected
     boolean cellHasFocus) {    // does the cell have focus
        Food f = (Food) value;
        String s = f.getName();
        setText(s);
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}    

