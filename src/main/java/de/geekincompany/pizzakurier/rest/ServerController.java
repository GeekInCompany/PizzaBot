package de.geekincompany.pizzakurier.rest;

import de.geekincompany.pizzakurier.Main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/server")
public class ServerController {
    @GET
    @Path("stop")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean stop(){
        synchronized (Main.shutdown) {
            Main.shutdown.notifyAll();
        }
        return true;
    }
}
