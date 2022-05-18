package ch.giuntini.goodstrainsignalling.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("freightwagon")
public class FreightWagonService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();
    }

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@QueryParam("wn") String wagonNumber) {
        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();
    }
}
