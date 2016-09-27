/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygieniapeli;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;
/**
 *
 * @author Kukka
 */
public class ListTransferHandler extends TransferHandler {
    private final DataFlavor localObjectFlavor;
    private int[] indices = null;
    private JList source;
    final private String trashName;
    final private String waitingName;
    final private String mouldName;
    final private String floorName;
    
    public ListTransferHandler (String trashname, String waitingname, String mouldname, String floorname) {
        super();
        this.trashName=trashname;
        this.waitingName=waitingname;
        this.mouldName=mouldname;
        this.floorName=floorname;
        localObjectFlavor=new javax.activation.ActivationDataFlavor(Object[].class,DataFlavor.javaJVMLocalObjectMimeType,"Array of items");
    }
    
    public void initSourceVariable (JList sourcelist) {
        this.source = sourcelist;
    }
    
    @Override
    protected Transferable createTransferable (JComponent c) {
        if (! (c instanceof JList)) return null;
        source = (JList) c;
        if (!source.getDragEnabled()) return null;
        indices = source.getSelectedIndices();
        if (indices.length <= 0) return null;
        Object[] transferedObjects = source.getSelectedValues();
        return new javax.activation.DataHandler(transferedObjects, localObjectFlavor.getMimeType());
    }

    @Override
    public boolean canImport (TransferSupport info) {
        if (!info.isDataFlavorSupported(localObjectFlavor)) {
            System.out.println("Flavor not supported "+localObjectFlavor.toString());
            return false;
        }
        if (source==null) return false;
        return (source.getDragEnabled() && info.isDrop());
    }

    @Override
    public int getSourceActions (JComponent c) {
        return MOVE; //TransferHandler.COPY_OR_MOVE copy not allowed
    }

    private boolean checkTransfer (JList tsource, JList ttarget) {
        if (tsource==null || ttarget==null) return false;
        if (ttarget.equals(tsource)) return false;
        if (tsource.getName() != null && tsource.getName().equals(trashName) && 
                (ttarget.getName() != null && !ttarget.getName().equals(mouldName) )) {
            //    && !ttarget.getName().equals(trashName))) {
            javax.swing.JOptionPane.showMessageDialog (null,"Roskia ei saa syödä!");
            return false;
        }
        return true;
    }
    
    boolean checkFoodTransfer (Object food, JList tsource, JList ttarget) {
        if (food==null || !(food instanceof Food)) return false;
        Food foodundertransfer = (Food) food;
        if ((ttarget.getName() != null && ttarget.getName().equals(floorName)) && foodundertransfer.easyToSpoil()) {
            javax.swing.JOptionPane.showMessageDialog (null,"Helposti pilaantuvaa ruokaa ei saa säilyttää lattialla.");
            return false;
        }
        if (foodundertransfer.getProcessedFoodType().equals(ProcessedFoodType.KUUMENNETTURUOKA)) {
            javax.swing.JOptionPane.showMessageDialog(null, 
                "Kuumennettu ruoka on joko pidettävä vähintään 60 asteessa tai jäähdytettävä <=6 asteeseen.  Ruoka on jäähdytyksen jälkeen kuumennettava uudelleen 60 asteeseen.");
        }
        return true;
    }
    
    @Override
    public boolean importData (TransferSupport info) {
        JList target;
        if(!canImport(info)) return false;
        java.awt.Component dc = javax.swing.SwingUtilities.getDeepestComponentAt
        (info.getComponent(),info.getDropLocation().getDropPoint().x, info.getDropLocation().getDropPoint().y);
        if ((target=GameUtilities.castToJList(dc)) == null) return false;
        if (!target.getDropMode().equals(javax.swing.DropMode.INSERT)) return false;
        if (!checkTransfer(source, target)) return false;
        FoodListModel listModel = (FoodListModel)target.getModel();
        int index = target.locationToIndex(info.getDropLocation().getDropPoint());
        int max = listModel.getSize();
        if(index<0 || index>max) index = max;
        try {
          Object []values = (Object[])info.getTransferable().getTransferData(localObjectFlavor);
          for(int i=0; i<values.length; ++i, ++index) {
            if (!checkFoodTransfer(values[i], source, target)) return false;
            listModel.insertFoodElement(index, values[i]);
            listModel.update(index);
          }
          target.clearSelection();
          target.validate();
          target.repaint();
          return true;
        } catch(UnsupportedFlavorException | IOException e) {
          e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void exportDone (JComponent c, Transferable data, int action) {
        if(action == MOVE && indices != null) {
            FoodListModel model = (FoodListModel)source.getModel();
            for(int i=indices.length-1; i>=0; i--) {
                model.removeFoodElement(indices[i]);
                model.update(indices[i]);
            }
            indices  = null;
            source.clearSelection();
        }
    }
    
}
