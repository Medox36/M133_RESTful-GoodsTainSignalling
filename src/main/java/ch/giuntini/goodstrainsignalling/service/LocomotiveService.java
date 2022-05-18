package ch.giuntini.goodstrainsignalling.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("locomotive")
public class LocomotiveService {

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();
    }

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@QueryParam("series") String series, @QueryParam("opn") Integer operationNumber) {
        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();
    }
}
