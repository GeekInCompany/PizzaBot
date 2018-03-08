package de.geekincompany.pizzakurier.rest;

import de.geekincompany.pizzakurier.browser.Browser;
import de.geekincompany.pizzakurier.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/order")
public class OrderController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Order listOrder(){
        return DataStore.order;
    }

    @POST
    @Path("new")
    @Produces(MediaType.APPLICATION_JSON)
    public Order newOrder(){
        DataStore.order = new Order();
        return DataStore.order;
    }

    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean addProduct(@FormParam("product") String product, @FormParam("variation") Integer variation){
        if(variation == null)
            variation = 0;
        Product prod = ProductCache.findById(product);
        if(prod != null){
            if(DataStore.order == null)
                    DataStore.order = new Order();
            DataStore.order.addObject(new OrderObject(product,variation));
            return true;
        }
        return false;
    }

    @POST
    @Path("modify")
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyObject(@FormParam("id") Integer id, @FormParam("ingredient") Integer ingredient, @FormParam("value") Integer value){
        if(id!=null&&ingredient!=null&&value!=null){
            OrderObject obj = DataStore.order.getObjects().get(id);
            obj.getIngredients().put(Ingredients.valueOf(ingredient),value);
            return Response.ok(DataStore.order.getObjects().get(id)).build();
        }
        return Response.status(400).build();
    }

    @POST
    @Path("submit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response submit(){
        if(!DataStore.order.isSubmitted()) {
            Browser browser = new Browser();
            boolean result = browser.order(DataStore.order);
            //browser.close();
            if(result)
                return Response.ok(DataStore.order).build();
            return Response.status(500).entity(false).build();
        }else{
            return Response.status(406).entity(false).build();
        }
    }

    @POST
    @Path("takeaway")
    @Produces(MediaType.APPLICATION_JSON)
    public Order setTakeaway(@FormParam("takeaway") Boolean takeaway){
        if(takeaway == null)
            takeaway = true;
        DataStore.order.setTakeaway(takeaway);
        return DataStore.order;
    }
}
