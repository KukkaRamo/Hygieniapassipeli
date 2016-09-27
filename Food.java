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
public class Food {
    static int lowlimittemp = -25;
    static int highlimittemp = 135;
    String name = "";
    ProcessedFoodType processedType;
    boolean packed = false;
    private boolean dried = false;
    private boolean canned = false;
    boolean hasbeenserviced = false;
    int templow = 6;
    int temphigh = 6;
   // private javax.swing.JList location = MainGameFrame.getJListTuodut();
    
    Food (String pname, ProcessedFoodType pprocessedtype) {
        this.name = pname;
        this.processedType = pprocessedtype;
        this.temphigh = pprocessedtype.getHighTemp();
        this.templow = pprocessedtype.getLowTemp();
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
    
    String getName() {return this.name;}
    
    boolean isCanned() {return this.canned;}
    
    boolean getDried() {return this.dried;}
       
    private void setTempHigh (int t) {
        if (t >= Food.lowlimittemp && t <= Food.highlimittemp)
           temphigh=t;
    }
    
    private void setTempLow(int t) {
        if (t >= Food.lowlimittemp && t <= Food.highlimittemp)
           templow=t;
    }
    
    private void setCanned(boolean pcanned) {this.canned=pcanned;}

    private void setDried(boolean pdried) {this.dried=pdried;}
   
    boolean easyToSpoil() {
        if (canned || dried) return false;
        return (this.getProcessedFoodType().getEasilySpoiling());
    }
   
    public void canClosed() {this.setCanned(true); this.setPacked(true); 
        this.setTempLow(lowlimittemp); this.setTempHigh(highlimittemp);}
    
    public void canOpen() {this.setCanned(true); this.setPacked(false); 
        this.setTempLow(this.getProcessedFoodType().getLowTemp());
        this.setTempHigh(this.getProcessedFoodType().getHighTemp());}
    
    public void dry() {this.setDried(true); this.setTempLow(lowlimittemp); this.setTempHigh(highlimittemp);}
    
}
