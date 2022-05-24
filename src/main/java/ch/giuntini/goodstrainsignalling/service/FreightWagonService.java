package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.DataHandler;
import ch.giuntini.goodstrainsignalling.model.FreightWagon;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * freight wagon service
 */
@Path("freightwagon")
public class FreightWagonService {

    /**
     * service to get all freight wagons
     *
     * freight wagons can fe filtered and sorted ascending and descending
     * by their wagon number
     *
     * @param filter
     * @param sort "a" for ascending or "d" for descending
     * @return list of freight wagons
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("contains") String filter, @QueryParam("sort") String sort) {
        List<FreightWagon> freightWagons = DataHandler.getInstance().readAllFreightWagons();
        List<FreightWagon> copy = new ArrayList<>(freightWagons.size());
        Collections.copy(copy, freightWagons);

        if (filter != null && !filter.isEmpty()) {
            for (int i = 0; i < copy.size(); i++) {
                copy.removeIf(
                        freightWagon -> !freightWagon.getWaggonNumber().contains(filter)
                );
            }
        }
        if (sort != null && !sort.isEmpty()) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(FreightWagon::getWaggonNumber));
            } else if (sort.equals("d")) {
                copy.sort(
                        (freightWagon, t1) -> t1.getWaggonNumber().compareToIgnoreCase(freightWagon.getWaggonNumber())
                );
            } else {
                return Response
                        .status(400)
                        .build();
            }
        }

        return Response
                .status(200)
                .entity(copy)
                .build();
    }

    /**
     * service to get a specific freight wagon by its operation number
     *
     * @param wagonNumber of the freight wagon
     * @return a freight wagon that matches the parameter if there is one
     */
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
        if (freightWagon == null) {
            return Response
                    .status(404)
                    .build();
        }
        return Response
                .status(200)
                .entity(freightWagon)
                .build();
    }
}