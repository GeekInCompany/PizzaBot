package de.geekincompany.pizzakurier;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import de.geekincompany.pizzakurier.browser.Browser;
import de.geekincompany.pizzakurier.model.Product;
import de.geekincompany.pizzakurier.model.ProductCache;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static final Object shutdown = new Object();
    public static final String USER_AGENT = "PizzaBot ©GeekInCompany 2018-03-09";

    public static void main(String[] args) {
        Browser browser = new Browser();
        ArrayList<Product> products = browser.getProducts();
        ProductCache.add(products);
        HttpServer server = null;
        try {
            server = HttpServerFactory.create( "http://0.0.0.0:3030/rest" );
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();

        synchronized (shutdown){
            try {
                shutdown.wait();
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Shutting Down");
        server.stop( 0 );
    }
}
