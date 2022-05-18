package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.DataHandler;
import ch.giuntini.goodstrainsignalling.model.Locomotive;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("locomotive")
public class LocomotiveService {

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        List<Locomotive> locomotives = DataHandler.getInstance().readAllLocomotives();
        return Response
                .status(200)
                .entity(locomotives)
                .build();
    }

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@QueryParam("series") String series, @QueryParam("opn") Integer operationNumber) {
        if (!series.matches("([A-Za-z]{1,5}) (([1-9]+[x]?[1-9]*/[1-9]+)|([0-9]{1,3})|(TEE))([\\^]?)([IV]{0,3})")
                || (operationNumber < 100 || operationNumber > 18841)) {
            return Response
                    .status(400)
                    .build();
        }
        Locomotive locomotive = DataHandler.getInstance()
                .readLocomotiveBySeriesAndProductionNumber(series, operationNumber);
        return Response
                .status(200)
                .entity(locomotive)
                .build();
    }
}
