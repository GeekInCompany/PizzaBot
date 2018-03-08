package de.geekincompany.pizzakurier.model;

public enum Ingredients {
    ANNANAS,
    ARTISCHOCKEN,
    BOHNEN,
    BROCOLI,
    CHAMPIGNONS,
    EI,
    GORGONZOLA,
    HACKFLEISCH,
    HUEHNERBRUST,
    JALAPENOS,
    KAPERN,
    KNOBLAUCH,
    KAESE,
    MAIS,
    MEERESFRUECHTE,
    MOZZARELLA,
    MUSCHELN,
    OLIVEN,
    PAPRIKA,
    PARMESANKAESE,
    PEPERONI,
    SALAMI,
    SARDELLEN,
    SCHAFSKAESE,
    SHRIMPS,
    SPARGEL,
    SPECK,
    SPINAT,
    THUNFISCH,
    TINTENFISCH,
    TOMATEN,
    VORDERSCHINKEN,
    ZWIEBELN;

    public static Ingredients valueOf(Integer i){
        return Ingredients.values()[i];
    }
}
