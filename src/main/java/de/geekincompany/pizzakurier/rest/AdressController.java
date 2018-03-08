package de.geekincompany.pizzakurier.rest;

import de.geekincompany.pizzakurier.model.Adress;
import de.geekincompany.pizzakurier.model.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/adress")
public class AdressController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Adress getAdress() {
        return DataStore.adress;
    }

    @POST
    @Path("set")
    @Produces(MediaType.APPLICATION_JSON)
    public Adress setAdress(@FormParam("zip") int zip,
                            @FormParam("street") String street,
                            @FormParam("name") String name,
                            @FormParam("sname") String sname,
                            @FormParam("phone") String phone,
                            @FormParam("email") String email,
                            @FormParam("type") int type) {
        if(DataStore.adress==null)
            DataStore.adress = new Adress();
        DataStore.adress.setZip(zip);
        DataStore.adress.setStreet(street);
        DataStore.adress.setName(name);
        DataStore.adress.setSname(sname);
        DataStore.adress.setPhone(phone);
        DataStore.adress.setEmail(email);
        DataStore.adress.setType(Adress.AdressType.valueOf(type));
        return DataStore.adress;
    }
}
