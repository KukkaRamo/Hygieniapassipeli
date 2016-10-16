package hygieniapeli;
import java.util.ArrayList;

/**
 *
 * @author Kukka
 */
public class HygieniaPeli {

 /**
 * Create available foods.  Build your food selection here.  The software imports 
 * a random bag from the food selection to your frame.  Food may also be canned,
 * packed, and/or dried.
 * 
 * @author Kukka
 */
    static ArrayList<Food> BuildFoodList() {
        ArrayList<Food> foodList = new ArrayList();
        FoodBuilder myFoodBuilder = new FoodBuilder();
        // Maybe you want to add many more!
        Food p = myFoodBuilder.createFood("Raa'at perunat", ProcessedFoodType.KOKONAINENKASVI).getResult();
        if (p!=null) foodList.add(p);
        p = myFoodBuilder.createFood("Kylmäsavukala", ProcessedFoodType.KYLMASAVUSTETTUKALA).getResult();
        if (p!=null) foodList.add(p);
        p = myFoodBuilder.createFood("Säilykepersikat", ProcessedFoodType.PALOITELLUTKASVIKSET).closeCan().getResult();
        if (p!=null) foodList.add(p);
        p = myFoodBuilder.createFood("Pakattu porkkanaraaste", ProcessedFoodType.PALOITELLUTKASVIKSET).setPacked(true).getResult();
        if (p!=null) foodList.add(p);
        p = myFoodBuilder.createFood("Kuumana tilattu kaalikeitto", ProcessedFoodType.KUUMENNETTURUOKA).getResult();
        if (p != null) foodList.add(p);
        return foodList;
    }
    
    /**
     * @param args the command line arguments.
     * Create a frame with food table and default temperature.  The temperature
     * is the default temperature for each cabinet.  Currently the frame must be
     * restaurant frame or storage frame.  For example, an outdoors place to 
     * offer food is a restaurant.  The third parameter to the frame is whether 
     * the frame is a place to sell food (true) or a place to store food (false).
     * The fourth parameter is the desired number of imported food elements.
     */
    public static void main(String[] args) {
        try {
            final ArrayList<Food> FoodTable = BuildFoodList();
            // Now there are RestaurantFrame (sell) and StorageFrame (store),
            // So build either of those
            RestaurantFrame myFrame = new RestaurantFrame(FoodTable, 20, false, 20);
            // StorageFrame myFrame = new StorageFrame(FoodTable, 20, false, 20);
            myFrame.setVisible(true);
        } catch (java.lang.Exception e) {e.printStackTrace();}
    }
    
}
