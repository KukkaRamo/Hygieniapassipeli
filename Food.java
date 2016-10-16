package hygieniapeli;
import java.lang.reflect.Field;

/**
 *
 * @author Kukka
 */
public class Food {
    static int lowlimittemp = -25;
    static int highlimittemp = 135;
    static int drycanmin = 18;
    String name = "";
    ProcessedFoodType processedType;
    boolean packed = false;
    private boolean dried = false;
    private boolean canned = false;
    boolean hasbeenserviced = false;
    int templow = 6;
    int temphigh = 6;
    int prodtemplow = 6;
    int prodtemphigh = 6;
    
    /**
    * Creates food.
    * 
    * @param pname Food name
    * @param pprocessedtype the type of the food
    */
    Food (String pname, ProcessedFoodType pprocessedtype) {
        this.name = pname;
        this.processedType = pprocessedtype;
        this.templow = pprocessedtype.getTempLow();
        this.temphigh = pprocessedtype.getTempHigh();
        this.prodtemplow = pprocessedtype.getProdTempLow();
        this.prodtemphigh = pprocessedtype.getProdTempHigh();
    }
    
    Food (Food f) { // Enums are not cloneable, so make a copy constructor
        try {
            for (Field field : f.getClass().getDeclaredFields())
                field.set(this, field.get(f));
            this.name = new String(f.name);
            this.processedType = f.processedType;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    public ProcessedFoodType getProcessedFoodType() {
        return processedType;
    }

    public boolean isPacked() {
        return packed;
    }

    public void setPacked(boolean packed) {
        this.packed = packed;
    }

    public boolean isHasbeenserviced() {
        return hasbeenserviced;
    }

    public void setHasbeenserviced(boolean hasbeenserviced) {
        this.hasbeenserviced = hasbeenserviced;
    }

    public int getTemplow() {
        return templow;
    }

    public int getTemphigh() {
        return temphigh;
    }
    
    public int getProdtemplow() {
        return prodtemplow;
    }

    public int getProdtemphigh() {
        return prodtemphigh;
    }
    String getName() {return this.name;}
    
    boolean isCanned() {return this.canned;}
    
    boolean getDried() {return this.dried;}
    
    private void setTempLow(int t) {
        if (t >= Food.lowlimittemp && t <= Food.highlimittemp)
           templow=t;
    }
    
    private void setTempHigh (int t) {
        if (t >= Food.lowlimittemp && t <= Food.highlimittemp)
           temphigh=t;
    }
    
    private void setProdTempLow(int t) {
        if (t >= Food.lowlimittemp && t <= Food.highlimittemp)
           prodtemplow=t;
    }
    
    private void setProdTempHigh (int t) {
        if (t >= Food.lowlimittemp && t <= Food.highlimittemp)
           prodtemphigh=t;
    }
    
    private void setCanned(boolean pcanned) {this.canned=pcanned;}

    private void setDried(boolean pdried) {this.dried=pdried;}
   
    boolean isEasyToSpoil() {
        if ((canned && packed) || dried) return false;
        return (this.getProcessedFoodType().getEasilySpoiling());
    }

    /**
    * Make the food canned with can closed.  The food will be packed.
    * 
    */
    public void closeCan() {
        this.setCanned(true); this.setPacked(true); 
        this.setTempLow(drycanmin); this.setTempHigh(highlimittemp);
        this.setProdTempLow(drycanmin); this.setProdTempHigh(highlimittemp);
    }
    
    /**
    * Make the food canned with can open.  The food will not be packed.
    * 
    */
    public void openCan() {
        this.setCanned(true); this.setPacked(false); 
        this.setTempLow(this.getProcessedFoodType().getTempLow());
        this.setTempHigh(this.getProcessedFoodType().getTempHigh());
        this.setProdTempLow(this.getProcessedFoodType().getProdTempLow());
        this.setProdTempHigh(this.getProcessedFoodType().getProdTempHigh());
    }
    
    /**
    * Make the food dried.
    *
    */
    public void dry() {
        this.setDried(true);
        this.setTempLow(drycanmin); this.setTempHigh(highlimittemp);
        this.setProdTempLow(drycanmin); this.setProdTempHigh(highlimittemp);
    }
    
}
