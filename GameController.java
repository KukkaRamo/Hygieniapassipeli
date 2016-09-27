/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygieniapeli;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFormattedTextField;
/**
 *
 * @author Kukka
 */
public class GameController {
    MainGameFrame mgf;
    HashMap<String, Integer> temperatures = new HashMap();

    public GameController(MainGameFrame pmgf, ArrayList<Food> inputFoodTable) {
        this.mgf = pmgf;
        buildFoodBag(inputFoodTable); // Import foods to the restaurant, randomly from the available selection
        buildTempHash();
    }

    private void buildFoodBag(ArrayList<Food> FoodTable) {
        FoodListModel<Food> MyModel = new FoodListModel();
        final int numOfFood = 20;
        for (int i=0; i<numOfFood; ++i) {
            int j = FoodTable.size();
            MyModel.addFoodElement(FoodTable.get((int) (j * Math.random())));
        }
        mgf.getJListTuodut().setModel(MyModel);
    }
    
    private void buildTempHash() {
        for (java.awt.Component c : mgf.getJPanelSailytys().getComponents())
            if (c != null && (c instanceof javax.swing.JPanel)) {
                javax.swing.JPanel p = (javax.swing.JPanel) c;
                for (java.awt.Component cc: p.getComponents())
                    if (cc instanceof javax.swing.JFormattedTextField)
                        temperatures.put(cc.getName(), Integer.parseInt(((JFormattedTextField) cc).getText()));
            }
        JFormattedTextField cc = mgf.getJFormattedTextFieldTarjolla1T();
        temperatures.put(cc.getName(), Integer.parseInt(((JFormattedTextField) cc).getText()));
        cc = mgf.getJFormattedTextFieldTarjolla2T();
        temperatures.put(cc.getName(), Integer.parseInt(((JFormattedTextField) cc).getText()));
    }
    
    public void calculateResults() {
        final int[] costs = {30, 2, 1};
        final int defaultTemp = mgf.getDefaultTemp();
        int spoiled=0, energywaste=0;
        for (java.awt.Component c : mgf.getJPanelSailytys().getComponents())
            if (c != null && (c instanceof javax.swing.JPanel)) {
                javax.swing.JPanel p = (javax.swing.JPanel) c;
                javax.swing.JList l = null;
                int thistemp=defaultTemp;
                for (java.awt.Component cc: p.getComponents()) {
                    if (cc instanceof javax.swing.JFormattedTextField)
                        thistemp = temperatures.get(cc.getName());
                    else if (cc instanceof javax.swing.JScrollPane) {
                        javax.swing.JScrollPane jc = (javax.swing.JScrollPane) cc;
                        l = (javax.swing.JList) jc.getViewport().getView();
                    }
                }
                if (l != null) {
                    FoodListModel calculatedModel = (FoodListModel) l.getModel();
                    for (Object o : calculatedModel.foods) {
                        Food f = (Food) o;
                        if (thistemp > -12) { // No freezing
                            int lowlimit = (f.easyToSpoil()) ? f.templow : defaultTemp;
                            if (thistemp < lowlimit)
                                energywaste += lowlimit - thistemp;
                        }
                        if (thistemp < 60 && thistemp > f.temphigh) ++spoiled;
                        // if (l.getName().equals(TrashName)) ++waste;
                    }
                }
            }

        mgf.getJTableResults().getModel().setValueAt(spoiled, 0, 1);
        mgf.getJTableResults().getModel().setValueAt(energywaste, 1, 1);
        FoodListModel lm = (FoodListModel) mgf.getJListRoskis().getModel();
        mgf.getJTableResults().getModel().setValueAt(lm.getSize(),2,1);
        mgf.getJTableResults().getModel().setValueAt(spoiled*costs[0],0,2);
        mgf.getJTableResults().getModel().setValueAt(energywaste*costs[1],1,2);
        mgf.getJTableResults().getModel().setValueAt(lm.getSize()*costs[2],2,2);
     }
    
}
