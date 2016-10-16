package hygieniapeli;

/**
 * Subtypes for food.  Storing and transporting temperatures depend on food subtype.
 * 
 * @author Kukka
 * 
 * Asetus 1367/2011 for selling
 * Latter numbers for production as follows
 * EU 853/2004 liitteet: teurastamoissa omat määräykset
 * Tuotanto: siipikarja 4, muut osat 3, muu liha 7, jauheliha 2, raakalihavalmisteet 4
 * mekaanisesti erotettu liha 2
 * Raakamaito: päivittäin jäähdytys 8, ei päivittäin jäähdytys 6, perillä 10
 *      vastaanottopaikassa jalostukseen asti 6 jos ei käsitellä 4h sisällä ja poikkeusluvat mahdollisia
 * Rikottu muna: 4
 * Sulatettava eläinrasva ennen sulatusta 7, 
 *     sulatuksen jälkeen jos enintään 70 asteessa tai kosteus vähintään 10 prosenttia 7
 * Suolaamattomat kuivaamattomat mahat ja rakot ja suolet 3
 */
public enum ProcessedFoodType {
    RAAKAKALA (FoodType.FISH, "Raaka kala", 0, 0, 0, 0, true),
    KEITETYTAYRIAISET (FoodType.FISH, "Keitetyt äyriäiset", 0, 0, 0, 0, true),
    KEITETYTNILVIAISET (FoodType.FISH, "Keitetyt nilviäiset", 0, 0, 0, 0, true),
    RAAKASULATETTUKALA (FoodType.FISH, "Raaka sulatettu kala", 0, 0, 0, 0, true),
    KYLMASAVUSTETTUKALA (FoodType.FISH, "Kylmäsavustettu kala", 0, 3, 0, 3, true),
    TUORESUOLATTUKALA (FoodType.FISH, "Tuoresuolattu kala", 0, 3, 0, 3, true),
    TYHJIOPAKAATTUKALATUOTE (FoodType.FISH, "Tyhjiöpakattu jalostettu kalatuote", 0, 3, 0, 3, true),
    SUOJAKAASUPAKAATTUKALATUOTE (FoodType.FISH, "Suojakaasupakattu jalostettu kalatuote", 0, 3, 0, 3, true),
    KALAPUOLISAILYKE (FoodType.FISH, "Kalapuolisäilyke", 6, 6, 6, 6, true),
    JAUHELIHA (FoodType.MEAT, "Jauheliha", 0, 4, 0, 2, true),
    JAUHEMAKSA (FoodType.MEAT, "Jauhettu maksa", 0, 4, 0, 2, true),
    JAUHEKANA (FoodType.BIRD, "Jauhetut siipikarjatuotteet", 0, 4, 0, 2, true),
    RAAKAKANA (FoodType.BIRD, "Raaka kana", 6, 6, 4, 4, true),
    RAAKALIHAVALMISTEET (FoodType.MEAT, "Raakalihavalmisteet", 6, 6, 4, 4, true),
    LIHAVALMISTEET (FoodType.MEAT, "Lihavalmisteet", 6, 6, 4, 4, true),
    ELIMET (FoodType.MEAT, "Sisäelimet", 6, 6, 3, 3, true),
    ELAINRASVA (FoodType.MEAT, "Eläimestä sulatettu rasva", 6, 6, 7, 7, true),
    KOKONAINENKANANMUNA (FoodType.EGG, "Kokonainen kananmuna", 18, Food.highlimittemp, 18, Food.highlimittemp, false),
    RIKOTTUKANANMUNA (FoodType.EGG, "Rikottu kananmuna", 6, 6, 4, 4, true),
    ISKUKUUMENNETTUMAITOTUOTE (FoodType.MILK, "Iskukuumennettu maitotuote", 18, Food.highlimittemp, 18, Food.highlimittemp, false),
    PASTOROITUMAITOTUOTE (FoodType.MILK, "Pastoroitu tai vastaavalla tavalla kasitelty maitotuote", 8, 8, 8, 8, true),
    MAITO (FoodType.MILK, "Maito", 6, 6, 6, 6, true),
    PASTOROIMATONMAITOTUOTE (FoodType.MILK, "Käsittelemätön maitotuote", 6, 6, 6, 6, true),
    IDUT (FoodType.PLANT, "Idut", 6, 6, 6, 6, true),
    PALOITELLUTKASVIKSET (FoodType.MILK, "Paloitellut kasvikset", 6, 6, 6, 6, true),
    ELÄVÄTNILVIÄISET (FoodType.FISH, "Elävät simpukat", 6, 6, 6, 6, true),
    SUSHI (FoodType.FISH, "Sushi", 6, 6, 6, 6, true),
    // Asetus7§;5: Kokonaisia kalakukkoja voi säilyttää huoneenlämmössä myyntipäivänä ja sitten +6
    // Pakastimesta sulatettuja kuumennettuja kalakukkoja voi säilyttää huoneenlämmössä myyntipäivän jos sitten hävitetään
    KOKONAINENKALAKUKKO (FoodType.BREAD, "Kalakukko", Food.drycanmin, Food.highlimittemp, Food.drycanmin, Food.highlimittemp, true),
    VOILEIPA (FoodType.BREAD, "Voileipä", Food.drycanmin, Food.highlimittemp, Food.drycanmin, Food.highlimittemp, true),
    LEIPA (FoodType.BREAD, "Leipä", Food.drycanmin, Food.highlimittemp, Food.drycanmin, Food.highlimittemp, false),
    // Asetus 7§;5: Kuumentamalla valmistettuja helposti pilaantuvia leipomotuotteita voidaan säilyttää vastaanottopäivänä huoneenlämmössä, jos sitten hävitetään
    TUORELEIVOS (FoodType.BREAD, "Helposti pilaantuva leivos", Food.drycanmin, Food.highlimittemp, Food.drycanmin, Food.highlimittemp, true),
    KUIVALEIVOS (FoodType.BREAD, "Kuiva leivos", Food.drycanmin, Food.highlimittemp, Food.drycanmin, Food.highlimittemp, false),
    KOKONAINENKASVI (FoodType.PLANT, "Kokonainen kasvi", Food.drycanmin, Food.highlimittemp, Food.drycanmin, Food.highlimittemp, false),
    KASVISSALAATTI (FoodType.PLANT, "Kasvissalaatti", 6, 6, 6, 6, true),
    KASVISPUOLISAILYKE (FoodType.PLANT, "Kasvispuolisäilyke", 6, 6, 6, 6, false),
    SEKASALAATTI (FoodType.PLANT, "Sekasalaatti", 6, 6, 6, 6, true),
    RUOKAOLJY (FoodType.PLANT, "Ruokaöljy", 10, 14, 10, 14, true),
    SIENI (FoodType.PLANT, "Sieni", 6, 6, 6, 6, true),
    KUUMENNETTURUOKA (FoodType.PLANT, "Kuumennettu ruoka", 6, 6, 6, 6, true); // Tai kuumana 60 asteessa 
    
    final FoodType foodType;
    final String content;
    int templow; // Higher temperature usually means saving energy
    int temphigh; // Säilytys- ja myyntilämpötilat: ministeriön asetus 1367/2011 7§, plus oil
    int prodtemplow;
    int prodtemphigh;
    boolean easilySpoiling;
    
    ProcessedFoodType (FoodType pfood, String pcontent, int ptemplow, 
    int ptemphigh, int pprodtemplow, int pprodtemphigh, boolean peasilyspoiling) {
        this.foodType = pfood;
        this.content = pcontent;
        this.templow = ptemplow;
        this.temphigh = ptemphigh;
        this.prodtemplow = pprodtemplow;
        this.prodtemphigh = pprodtemphigh;
        this.easilySpoiling = peasilyspoiling;
    }
    
    public FoodType getFoodType() {return this.foodType; }
    public int getTempLow() {return this.templow; }
    public int getTempHigh() {return this.temphigh; }
    public int getProdTempLow() {return this.prodtemplow; }
    public int getProdTempHigh() {return this.prodtemphigh; }
    public boolean getEasilySpoiling() {return this.easilySpoiling; }
}
