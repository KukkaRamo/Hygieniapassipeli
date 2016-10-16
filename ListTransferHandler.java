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
    final private String trashName;
    final private String mouldName;
    final private String floorName;
    final boolean isFinal;
    private final DataFlavor localObjectFlavor;
    private int[] indices = null;
    private JList source;
    
    /**
    * Create the transfer handler for lists in PlainFoodStorage structures.  
    * Each PlainFoodStorage has a food list, and all lists use the same handler.
    * 
    * @param trashname  Name of the JList that is a trashcan
    * @param mouldname  Name of the JList that is a compost and produces mould
    * @param floorname  Name of the JList that is a floor
    * @param pfinal Tells whether food that is heated during or after final
    * stage of production is stored and/or served in the place that the frame 
    * represents after that heating
    */
    public ListTransferHandler (String trashname, String mouldname, String floorname, boolean pfinal) {
        super();
        this.isFinal=pfinal;
        this.trashName=trashname;
        this.mouldName=mouldname;
        this.floorName=floorname;
        localObjectFlavor=new javax.activation.ActivationDataFlavor(Object[].class,DataFlavor.javaJVMLocalObjectMimeType,"Array of items");
    }
    
    /**
    * Init the source list variable for the transfer.  After the transfer, the
    * selection needs to be cleared.  If the transfer succeeds, the items
    * must be deleted from the source list (source cannot be the same as destination).
    * 
    * @param sourcelist The source list for the transfer
    */
    public void initSourceVariable (JList sourcelist) {
        this.source = sourcelist;
    }
    
    @Override
    protected Transferable createTransferable (JComponent c) {
        if (! (c instanceof JList)) return null;
        source = (JList) c;
        if (!source.getDragEnabled()) {
            source.clearSelection();
            return null;
        }
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

    /**
    * Checks that the food transfer is OK.  The transferred items are not known 
    * yet, but it may already be known that the transfer is illegal.  
    * The transfer from the source to the destination must not be against 
    * regulations about preserving food.  Also some warnings and instructions 
    * may be given here about food processing.
    * 
    * @return The boolean value about if the transfer is OK.
    */
    private boolean checkFoodTransfer (JList tsource, JList ttarget) {
        if (tsource==null || ttarget==null) return false;
        if (ttarget.equals(tsource)) return false;
        if (tsource.getName() != null && tsource.getName().equals(trashName) && 
                (ttarget.getName() != null && !ttarget.getName().equals(mouldName) )) {
            javax.swing.JOptionPane.showMessageDialog (null,"Roskia ei saa syödä!");
            return false;
        }
        return true;
    }
    
    /**
    * Checks that the transfer of a specific food item is OK.  The transfer 
    * from the source to the destination must not be against regulations about 
    * preserving food.  Also some warnings and instructions may be given here 
    * about food processing.
    * 
    * @return The boolean value about if the transfer is OK.
    */
    private boolean checkFoodItemTransfer (Object food, JList tsource, JList ttarget) {
        if (food==null || !(food instanceof Food)) return false;
        Food foodundertransfer = (Food) food;
        if ((ttarget.getName() != null && ttarget.getName().equals(floorName)) && foodundertransfer.isEasyToSpoil()) {
            javax.swing.JOptionPane.showMessageDialog (null,"Helposti pilaantuvaa ruokaa "
            + foodundertransfer.getName() +" ei saa säilyttää lattialla,\npaitsi jos sen pakkaus on tarkoitettu lattialla kuljetettavaksi.");
            return false;
        }
        if (isFinal && foodundertransfer.getProcessedFoodType().equals(ProcessedFoodType.KUUMENNETTURUOKA)) {
            javax.swing.JOptionPane.showMessageDialog(null, 
                "Kuumennettu ruoka on joko pidettävä vähintään 60 asteessa tai jäähdytettävä <=6 asteeseen.\nRuoka on jäähdytyksen jälkeen kuumennettava uudelleen 60 asteeseen.");
        }
        return true;
    }
    
    @Override
    public boolean importData (TransferSupport info) {
        JList target;
        if(!canImport(info)) return false;
        java.awt.Point dropPoint = info.getDropLocation().getDropPoint();
        java.awt.Component dc = javax.swing.SwingUtilities.getDeepestComponentAt
        (info.getComponent(),dropPoint.x, dropPoint.y);
        if ((target=GameUtilities.castToJList(dc)) == null) return false;
        if (!target.getDropMode().equals(javax.swing.DropMode.INSERT)) return false;
        if (!checkFoodTransfer(source, target)) return false;
        FoodListModel listModel = (FoodListModel)target.getModel();
        int index = target.locationToIndex(dropPoint);
        int max = listModel.getSize();
        if(index<0 || index>max) index = max;
        try {
          Object []values = (Object[])info.getTransferable().getTransferData(localObjectFlavor);
          for(int i=0; i<values.length; ++i)
            if (!checkFoodItemTransfer(values[i], source, target)) return false;
          for(int i=0; i<values.length; ++i, ++index) {
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
        }
        source.clearSelection();
    }
    
}
