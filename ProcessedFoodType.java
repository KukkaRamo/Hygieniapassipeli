/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygieniapeli;

/**
 * Subtypes for food.  Storing and transporting temperatures depend on food subtype.
 * 
 * @author Kukka
 */
public enum ProcessedFoodType {
    RAAKAKALA (FoodType.FISH, "Raaka kala", 0, 0, true),
    KEITETYTAYRIAISET (FoodType.FISH, "Keitetyt äyriäiset", 0, 0, true),
    KEITETYTNILVIAISET (FoodType.FISH, "Keitetyt nilviäiset", 0, 0, true),
    RAAKASULATETTUKALA (FoodType.FISH, "Raaka sulatettu kala", 0, 0, true),
    KYLMASAVUSTETTUKALA (FoodType.FISH, "Kylmäsavustettu kala", 0, 3, true),
    TUORESUOLATTUKALA (FoodType.FISH, "Tuoresuolattu kala", 0, 3, true),
    TYHJIOPAKAATTUKALATUOTE (FoodType.FISH, "Tyhjiöpakattu jalostettu kalatuote", 0, 3, true),
    SUOJAKAASUPAKAATTUKALATUOTE (FoodType.FISH, "Suojakaasupakattu jalostettu kalatuote", 0, 3, true),
    KALAPUOLISAILYKE (FoodType.FISH, "Kalapuolisäilyke", 6, 6, true),
    JAUHELIHA (FoodType.MEAT, "Jauheliha", 0, 4, true),
    JAUHEMAKSA (FoodType.MEAT, "Jauhettu maksa", 0, 4, true),
    ISKUKUUMENNETTUMAITOTUOTE (FoodType.MILK, "Iskukuumennettu maitotuote", Food.lowlimittemp, Food.highlimittemp, false),
    PASTOROITUMAITOTUOTE (FoodType.MILK, "Pastoroitu tai vastaavalla tavalla kasitelty maitotuote", 8, 8, true),
    MAITO (FoodType.MILK, "Maito", 6, 6, true),
    PASTOROIMATONMAITOTUOTE (FoodType.MILK, "Käsittelemätön maitotuote", 6, 6, true),
    IDUT (FoodType.MILK, "Idut", 6, 6, true),
    PALOITELLUTKASVIKSET (FoodType.MILK, "Paloitellut kasvikset", 6, 6, true),
    ELÄVÄTNILVIÄISET (FoodType.MILK, "Elävät simpukat", 6, 6, true),
    SUSHI (FoodType.MILK, "Sushi", 6, 6, true),
    KOKONAINENKALAKUKKO (FoodType.BREAD, "Kalakukko", 18, Food.highlimittemp, true),
    VOILEIPA (FoodType.BREAD, "Voileipä", 18, Food.highlimittemp, true),
    LEIPA (FoodType.BREAD, "Leipä", 18, Food.highlimittemp, false),
    TUORELEIVOS (FoodType.BREAD, "Helposti pilaantuva leivos", 18, Food.highlimittemp, true),
    KUIVALEIVOS (FoodType.BREAD, "Kuiva leivos", 18, Food.highlimittemp, false),
    KOKONAINENKASVI (FoodType.PLANT, "Kokonainen kasvi", 18, Food.highlimittemp, false),
    KASVISSALAATTI (FoodType.PLANT, "Kasvissalaatti", 6, 6, true),
    KASVISPUOLISAILYKE (FoodType.PLANT, "Kasvispuolisäilyke", 6, 6, false),
    SEKASALAATTI (FoodType.PLANT, "Sekasalaatti", 6, 6, true),
    RUOKAOLJY (FoodType.PLANT, "Ruokaöljy", 10, 14, true),
    SIENI (FoodType.PLANT, "Sieni", 6, 6, true),
    KUUMENNETTURUOKA (FoodType.PLANT, "Kuumennettu ruoka", 6, 6, true);  
    
    final FoodType foodType;
    final String content;
    int lowTemp; // Higher temperature usually means saving energy
    int highTemp; // Säilytys- ja myyntilämpötilat: ministeriön asetus 1367/2011 7§, plus oil
    boolean easilySpoiling;
    
    ProcessedFoodType (FoodType pfood, String pcontent, int plowtemp, int phightemp, boolean peasilyspoiling) {
        this.foodType = pfood;
        this.content = pcontent;
        this.lowTemp = plowtemp;
        this.highTemp = phightemp;
        this.easilySpoiling = peasilyspoiling;
    }
    
    public FoodType getFoodType() {return this.foodType; }
    public int getLowTemp() {return this.lowTemp; }
    public int getHighTemp() {return this.highTemp; }
    public boolean getEasilySpoiling() {return this.easilySpoiling; }
}
