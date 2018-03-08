package de.geekincompany.pizzakurier.rest;

import de.geekincompany.pizzakurier.model.ProductCache;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/product")
public class ProductController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listProducts(){
        return Response.ok(ProductCache.all()).build();
    }
}
