package ch.giuntini.goodstrainsignalling.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * test service
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.05.17
 * @version 1.0
 */
@Path("test")
public class TestService {

    /**
     * confirms the application runs
     *
     * @return  message
     */
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {
        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();
    }
}