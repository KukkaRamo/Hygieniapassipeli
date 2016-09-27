/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygieniapeli;
import java.util.ArrayList;

/**
 *
 * @author Kukka
 */
public class HygieniaPeli {

 /**
 * Make available foods.
 * 
 * @author Kukka
 */
    static ArrayList<Food> BuildFoodList() {
        ArrayList<Food> foodList = new ArrayList<Food>();
        FoodBuilder myFoodBuilder = new FoodBuilder();
        Food p = myFoodBuilder.createFood("Raa'at perunat", ProcessedFoodType.KOKONAINENKASVI).getResult();
        if (p!=null) foodList.add(p);
        p = myFoodBuilder.createFood("Kylmäsavukala", ProcessedFoodType.KYLMASAVUSTETTUKALA).getResult();
        if (p!=null) foodList.add(p);
        p = myFoodBuilder.createFood("Säilykepersikat", ProcessedFoodType.PALOITELLUTKASVIKSET).canClosed().getResult();
        if (p!=null) foodList.add(p);
        p = myFoodBuilder.createFood("Pakattu porkkanaraaste", ProcessedFoodType.PALOITELLUTKASVIKSET).setPacked(true).getResult();
        if (p!=null) foodList.add(p);
        p = myFoodBuilder.createFood("Kuumana tilattu kaalikeitto", ProcessedFoodType.KUUMENNETTURUOKA).getResult();
        if (p != null) foodList.add(p);
        return foodList;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final ArrayList<Food> FoodTable = BuildFoodList();
        MainGameFrame mgf = new MainGameFrame();
        mgf.setVisible(true);
        mgf.setGameController (new GameController(mgf, FoodTable));
    }
    
}
