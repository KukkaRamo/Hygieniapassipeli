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
public class FoodBuilder {
    Food builtFood;
    static int lowlimittemp = Food.lowlimittemp;
    static int highlimittemp = Food.highlimittemp;
    
    
    FoodBuilder createFood (String name, ProcessedFoodType type) {
        this.builtFood=new Food(name, type);
        return this;
    }
    
    boolean checkMe() {
        int high = this.builtFood.getTemphigh();
        int low = this.builtFood.getTemplow();
        if (low<lowlimittemp) return false;
        if (high<low || high >highlimittemp) return false;
        return true;
    }
    
    public Food getResult() {
        return (checkMe()) ? builtFood : null;
    } 
    
    public ProcessedFoodType getProcessedType() {
        return this.builtFood.getProcessedFoodType();
    }

    public boolean isPacked() {
        return this.builtFood.isPacked();
    }

    public FoodBuilder setPacked(boolean packed) {
        this.builtFood.setPacked(packed);
        return this;
    }

    public boolean isCanned() {
        return this.builtFood.isCanned();
    }

    public int getTemplow() {
        return this.builtFood.getTemplow();
    }

    public int getTemphigh() {
        return this.builtFood.getTemphigh();
    }
    
    public boolean isDried() { return builtFood.getDried();}
    
    public FoodBuilder canClosed() { builtFood.canClosed(); return this; }
    
    public FoodBuilder canOpen() { builtFood.canOpen(); return this;}
    
    public FoodBuilder dry() { builtFood.dry(); return this;}   

}
