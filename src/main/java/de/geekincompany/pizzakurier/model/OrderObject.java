package de.geekincompany.pizzakurier.model;

import java.util.HashMap;

public class OrderObject {
    private final HashMap<Ingredients, Integer> ingredients;
    private int variation;
    private Product product;

    public OrderObject(String id, int variation){
        this.product = ProductCache.findById(id);
        this.variation = variation;
        this.ingredients = new HashMap<>();
    }

    public OrderObject(String id, int variation, HashMap<Ingredients,Integer> ingredients){
        this.product = ProductCache.findById(id);
        this.variation = variation;
        this.ingredients = ingredients;
    }

    public HashMap<Ingredients, Integer> getIngredients() {
        return ingredients;
    }

    public int getVariation() {
        return variation;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        if(product != null)
        return product.getId() +")  V"
                + variation +" "+
                product.getVariationPrice(variation)+"€";
        return "NULL PRODUCT";
    }
}
