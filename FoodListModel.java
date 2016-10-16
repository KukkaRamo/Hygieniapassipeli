package hygieniapeli;

import java.util.ArrayList;

/**
 *
 * @author Kukka
 */
public class FoodListModel <Object> extends javax.swing.AbstractListModel<Object> {
     ArrayList<Object> foods = new ArrayList();
     @Override
     public int getSize() { return foods.size(); }
     @Override
     public Object getElementAt(int index) { return (index>=0 && index<foods.size()) ? foods.get(index) : null;}
     public void addFoodElement(Object element) {if (element != null) foods.add(element);}
     public void insertFoodElement(int index, Object element) {if (element != null && index > -1 && index <= foods.size()) foods.add(index, element);} 
     public void removeFoodElement(int index) {if (index>=0 && index<foods.size())foods.remove(index);}
     // public void removeElement(Food element) {foods.remove(element);}            
     public void update(int index) {
         this.fireContentsChanged(this, index, index);
     }
}
