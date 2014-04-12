package system;


public class Person {
    
    private static final int DICT_SIZE_ENTERTAINMENT = 597;
    private static final int DICT_SIZE_HEALTH        = 323;
    private static final int DICT_SIZE_MONEYWATCH    = 334;
    private static final int DICT_SIZE_POLITICS      = 478;
    private static final int DICT_SIZE_SPORTS        = 420;
    private static final int DICT_SIZE_TECH          = 582;
    private static final int DICT_SIZE_US            = 510;
    private static final int DICT_SIZE_WORLD         = 435;

    //
    // General dispositions for each possible article topic.
    // These can take on values in the range [0,1]
    private static float Entertainment_Disposition;
    private static float Health_Disposition;
    private static float MoneyWatch_Disposition;
    private static float Politics_Disposition;
    private static float Sports_Disposition;
    private static float Tech_Disposition;
    private static float US_Disposition;
    private static float World_Dispositon;

    //
    // Feature vectors for each article topic.
    // Each vector is the corresponding DICT_SIZE in length
    // and each entry is in the range [0, 1]
    private float[] Entertainment_Feature_Vector;
    private float[] Health_Feature_Vector;
    private float[] MoneyWatch_Feature_Vector;
    private float[] Politics_Feature_Vector;
    private float[] Sports_Feature_Vector;
    private float[] Tech_Feature_Vector;
    private float[] US_Feature_Vector;
    private float[] World_Feature_Vector;

    public Person() {
        Entertainment_Feature_Vector = new float[DICT_SIZE_ENTERTAINMENT];
        Health_Feature_Vector = new float[DICT_SIZE_HEALTH];
        MoneyWatch_Feature_Vector = new float[DICT_SIZE_MONEYWATCH];
        Politics_Feature_Vector = new float[DICT_SIZE_POLITICS]; 
        Sports_Feature_Vector = new float[DICT_SIZE_SPORTS]; 
        Tech_Feature_Vector = new float[DICT_SIZE_TECH];
        US_Feature_Vector = new float[DICT_SIZE_US];
        World_Feature_Vector = new float[DICT_SIZE_WORLD];
    }   
}
