package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.DataHandler;
import ch.giuntini.goodstrainsignalling.model.FreightWagon;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("freightwagon")
public class FreightWagonService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        List<FreightWagon> freightWagons = DataHandler.getInstance().readAllFreightWagons();
        return Response
                .status(200)
                .entity(freightWagons)
                .build();
    }

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@QueryParam("wn") String wagonNumber) {
        if (!wagonNumber.matches("([0-9]{2} ){2}[0-9]{4} [0-9]{3}-[0-9]{1}")) {
            return Response
                    .status(400)
                    .build();
        }
        FreightWagon freightWagon = DataHandler.getInstance().readFreightWagonByWaggonNumber(wagonNumber);
        return Response
                .status(200)
                .entity(freightWagon)
                .build();
    }
}