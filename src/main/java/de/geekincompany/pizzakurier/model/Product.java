package de.geekincompany.pizzakurier.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Product {
    private int[] variationNumber;
    private Double[] variationPrice;
    private String[] variationJS;
    private String id;
    private String name;
    private static Pattern internalNumberPattern = Pattern.compile("\\((\\d*)(\\)|,\\d*\\))");

    public Product(String id, String name, String[][] variations) {
        this.name = name;
        this.id = id;

        this.variationJS = new String[variations.length];
        this.variationPrice = new Double[variations.length];
        this.variationNumber = new int[variations.length];
        for(int i = 0; i < variations.length; i++) {
            this.variationJS[i] = variations[i][1];
            this.variationPrice[i] = Double.parseDouble(variations[i][0]);
            Matcher m = internalNumberPattern.matcher(variations[i][1]);
            if(m.find())
                this.variationNumber[i] = Integer.parseInt(m.group(1));
        }
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder("[");
        for (int i = 0; i < variationPrice.length; i++) {
            tmp.append(variationPrice[i]).append("€:").append(variationJS[i]).append(" ");
        }
        tmp.append("]");
        return this.id + ") " + this.name + tmp;
    }

    public String getVariationJS(int v) {
        return this.variationJS[v];
        //Add(224,2)
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double[] getPrices(){
        return variationPrice;
    }

    public int[] getInternalNumbers(){
        return variationNumber;
    }

    public Double getVariationPrice(int v) {
        return this.variationPrice[v];
    }

    public int getVariationNumber(int v) {
        return this.variationNumber[v];
    }
}
