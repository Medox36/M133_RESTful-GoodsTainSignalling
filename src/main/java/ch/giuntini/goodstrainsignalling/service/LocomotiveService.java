package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.DataHandler;
import ch.giuntini.goodstrainsignalling.model.Locomotive;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * locomotive service
 */
@Path("locomotive")
public class LocomotiveService {

    /**
     * service to get all locomotives
     *
     * locomotives can be filtered an sorted ascending and descending
     * by their series
     *
     * @param filter for the series
     * @param sort "a" for ascending or "d" for descending
     * @return list of locomotives
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("contains") String filter, @QueryParam("sort") String sort) {
        List<Locomotive> locomotives = DataHandler.getInstance().readAllLocomotives();
        List<Locomotive> copy = new ArrayList<>(locomotives);

        if (filter != null && !filter.isEmpty()) {
            for (int i = 0; i < copy.size(); i++) {
                copy.removeIf(
                        locomotive -> !locomotive.getSeries().contains(filter)
                );
            }
        }
        if (sort != null && !sort.isEmpty()) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(Locomotive::getSeries));
            } else if (sort.equals("d")) {
                copy.sort(
                        (locomotive, t1) -> t1.getSeries().compareToIgnoreCase(locomotive.getSeries())
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
     * service to get a specific locomotive by its series and operation number
     *
     * @param series of the locomotive
     * @param operationNumber of the locomotive
     * @return a locomotive that matches the parameters if there is one
     */
    @GET
    @Path("read")
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
        if (locomotive == null) {
            return Response
                    .status(404)
                    .build();
        }
        return Response
                .status(200)
                .entity(locomotive)
                .build();
    }
}