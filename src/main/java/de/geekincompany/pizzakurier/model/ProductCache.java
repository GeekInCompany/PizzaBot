package de.geekincompany.pizzakurier.model;

import java.util.HashMap;
import java.util.List;

public class ProductCache {
    private static HashMap<String, Product> cache = new HashMap<>();

    public static void add(Product product){
        cache.put(product.getId(),product);
    }

    public static void add(List<Product> products){
        for(Product product: products){
            add(product);
        }
    }

    public static Product findById(String id) {
        return cache.get(id);
    }

    public static Product[] all() {
        Product[] temp = new Product[cache.size()];
        int i = 0;
        for (HashMap.Entry<String, Product> entry : cache.entrySet()) {
            temp[i++] = entry.getValue();
        }
        return temp;
    }
}
