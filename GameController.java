/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygieniapeli;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JFormattedTextField;
import javax.swing.event.ChangeEvent;

/**
 * Initializes everything that is not initialized upon creation of views, and
 * calculates game results.
 * 
 * @author Kukka
 */
public class GameController {
    final RootForm mgf;
    final boolean selling;
    final Integer myDefaultTemp;
    final BasePanel bmgf;
    final ListTransferHandler lth;

    /**
    * Create the controller for this game
    *
    * @param pmgf   The root form of the game
    * @param inputFoodTable The list of the foods that may be in the list of
    * imported food elements.
    * @param numOfFood How many food elements to import to the game
    */
    public GameController(RootForm pmgf, ArrayList<Food> inputFoodTable, int numOfFood) {
        this.mgf = pmgf;
        this.selling = pmgf.isSelling(); // Apply regulations about either selling or storage temperatures
        this.myDefaultTemp = pmgf.getDefaultTemp();
        this.bmgf = (BasePanel) pmgf.getBasePanel1();
        this.lth = new ListTransferHandler(bmgf.getTrashName(),
            this.bmgf.getMouldName(), bmgf.getFloorName(), true);
        initControllersAndTemp();
        buildFoodBag(inputFoodTable, numOfFood); // Import foods to the restaurant, randomly from the available selection
    }

    /**
    * Init the list of the imported foods.
    * If you want to make changes to individual food elements (e.g. can or dry 
    * some while playing), make food elements cloneable.  For now, shallow
    * copy is sufficient.
    * You may also modify the numOfFood, which tells how many food elements
    * are imported to the frame.
    * 
    * @param FoodTable The table of foods that may be imported to the frame
    * @param numOfFood How many food elements to import to the game
    */
    private void buildFoodBag(ArrayList<Food> FoodTable, int numOfFood) {
        FoodListModel flm = (FoodListModel) bmgf.getPlainFoodStorageTuodut().getJListFood().getModel();
        int j = FoodTable.size();
        for (int i=0; i<numOfFood; ++i) {
            Food food = FoodTable.get((int) (j * Math.random()));
            flm.addFoodElement(new Food(food)); // Clone if you want to modify elements during the game
            flm.update(i);
        }
    }
    
    /**
    * Init the list by adding transfer handler and mouse listener.
    * 
    * @param l The list to be initialized
    */
    private void initList(javax.swing.JList l) {
        l.setTransferHandler(lth);
        l.addMouseListener(new MouseDragHandler());
    }
    
    /**
    * Init several controls and models.
    * Set controllers to the components of JPanelCabinets and PlainFoodStorages.
    * Also set temperature models to JPanelCabinets.
    * 
    */
    private void initControllersAndTemp() {
        for (java.awt.Component c : bmgf.getComponents())
            if (c != null && (c instanceof PlainFoodStorage))
                initList(((PlainFoodStorage) c).getJListFood());
        mgf.getMyCabinetPanels().entrySet().forEach((entry) -> {
            for (java.awt.Component c : entry.getValue().getComponents())
                if (c != null && (c instanceof JPanelCabinet)) {
                    JPanelCabinet jpc = (JPanelCabinet) c;
                    jpc.setTempValues(myDefaultTemp);
                    jpc.getJFormattedTextFieldT().setInputVerifier(new TempVerifier());
                    jpc.getJSliderT().addChangeListener (new SliderChangeListener());
                    jpc.getJFormattedTextFieldT().addFocusListener(new MyTextFieldFocusListener());
                    initList (jpc.getPlainFoodStorage().getJListFood());
                }
        });
    }
    
    /**
    * Calculates costs of spoiled food, energy waste, and wasting of food.
    * 
    */
    public void calculateResults() {
        final int[] costs = {30, 2, 1};
        int spoiled=0, energywaste=0;
        for (java.awt.Component c : mgf.getMyCabinetPanels().get("Säilytys").getComponents())
            if (c != null && (c instanceof JPanelCabinet)) {
                int thistemp = ((JPanelCabinet) c).getTempValue();
                javax.swing.JList l = ((JPanelCabinet) c).getPlainFoodStorage().getJListFood();
                if (l != null) {
                    FoodListModel calculatedModel = (FoodListModel) l.getModel();
                    for (Object o : calculatedModel.foods) {
                        Food f = (Food) o;
                        if (thistemp > -12) { // No freezing
                            int lowlimit = (f.isEasyToSpoil()) 
                            ? ((selling) ? f.templow : f.prodtemplow)
                            : myDefaultTemp;
                            if (thistemp < lowlimit)
                                energywaste += lowlimit - thistemp;
                        } // See also EY 853/2004: Fish freezing: T <=-18, 
                        // whole fish in saltwater, intended as canned food (conserve): <=-9
                        // certain fish species -20 degrees for 24 hours unless law permits an exception
                        // (raakana tai lähes raakana syötävät kalat, 
                        // kilohaili, makrilli, silli, Atlantin ja Tyynenmeren lohi, 
                        // marinoitu ja/tai suolattu kala)
                        if (thistemp < 60 && thistemp > ( 
                        (selling)? f.temphigh : f.prodtemphigh ) )
                            ++spoiled;
                    }
                }
            }
        // The result table is just like a printing paper: values are printed only here and no edits or changes allowed
        bmgf.updateMyResult(spoiled, 0, 1);
        bmgf.updateMyResult(energywaste, 1, 1);
        int lmsize = bmgf.getPlainFoodStorageRoskakori().getJListFood().getModel().getSize();
        bmgf.updateMyResult(lmsize,2,1);
        bmgf.updateMyResult(spoiled*costs[0], 0, 2);
        bmgf.updateMyResult(energywaste*costs[1], 1, 2);
        bmgf.updateMyResult(lmsize*costs[2], 2, 2);
        bmgf.validate();
        bmgf.repaint();
     }
    
    private class MyTextFieldFocusListener implements java.awt.event.FocusListener {
        @Override
        public void focusGained(java.awt.event.FocusEvent e) {}
        @Override
        public void focusLost(java.awt.event.FocusEvent e) {
            JFormattedTextField tf = (JFormattedTextField) e.getSource();
            tf.getInputVerifier().shouldYieldFocus(tf);
        }
    }
    
     private class SliderChangeListener implements javax.swing.event.ChangeListener {
        @Override
        public void stateChanged (ChangeEvent e) {
            javax.swing.JSlider source = (javax.swing.JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                ((JPanelCabinet) source.getParent()).tempValue = source.getValue();
            // The field has binding to the textfield but does not update model automatically.
            }
        }
    }
}
