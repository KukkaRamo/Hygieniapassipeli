package hygieniapeli;
/**
 *
 * @author Kukka
 */
public class FoodBuilder {
    Food builtFood;
    static int lowlimittemp = Food.lowlimittemp;
    static int highlimittemp = Food.highlimittemp;
    
 /**
 * Create food.
 * 
 * @param name Name of the food
 * @param type Type of the processed food
 * @return a food builder for piping
 */
    FoodBuilder createFood (String name, ProcessedFoodType type) {
        this.builtFood=new Food(name, type);
        return this;
    }
    
 /**
 * Check the validity of the food.  The limits for the selling and production 
 * temperatures must have valid values.
 *
 * @return a boolean variable whether the food is valid
 */
    boolean checkMe() {
        int low = this.builtFood.getTemplow();
        int high = this.builtFood.getTemphigh();
        int prodlow = this.builtFood.getProdtemplow();
        int prodhigh = this.builtFood.getProdtemphigh();     
        if (low<lowlimittemp || high >highlimittemp || low>high) return false;
        return (prodlow >= lowlimittemp && prodhigh <= highlimittemp
        && prodlow <= prodhigh);
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
    
    /**
    * Make the food canned with can closed.  The food will be packed.
    *
    * @return the food builder for piping
    */
    public FoodBuilder closeCan() { builtFood.closeCan(); return this; }
    
    /**
    * Make the food canned with can open.  The food will not be packed.
    * 
    * @return the food builder for piping
    */
    public FoodBuilder openCan() { builtFood.openCan(); return this;}
    
    /**
    * Make the food dried.
    *
    * @return the food builder for piping
    */
    public FoodBuilder dry() { builtFood.dry(); return this;}   

}
