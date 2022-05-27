package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.DataHandler;
import ch.giuntini.goodstrainsignalling.model.Locomotive;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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
     * locomotives can be filtered an sorted by a attribute of the class in ascending and descending order
     *
     * @param filter for the series
     * @param sortBy a attribute of the class Locomotive
     * @param sort the parameter sortBy with "a" for ascending or "d" for descending
     * @return list of locomotives
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(
            @QueryParam("contains") String filter,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sort") String sort
    ) {
        List<Locomotive> locomotives = DataHandler.readAllLocomotives();
        List<Locomotive> copy = new ArrayList<>(locomotives);

        if (filter != null && !filter.isEmpty()) {
            for (int i = 0; i < copy.size(); i++) {
                copy.removeIf(
                        locomotive -> !locomotive.getSeries().contains(filter)
                );
            }
        }
        if (sortBy == null) {
            sortBy = "series";
        }
        if (sort == null) {
            sort = "a";
        }
        if (sort.isEmpty() || (!sort.equals("a") && !sort.equals("d"))) {
            return Response
                    .status(400)
                    .build();
        }
        if (sortBy.matches("series") || sortBy.isEmpty()) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(Locomotive::getSeries));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(Locomotive::getSeries)));
            }
        } else if (sortBy.matches("operationNumber")) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(Locomotive::getOperationNumber));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(Locomotive::getOperationNumber)));
            }
        } else if (sortBy.matches("railwayCompany")) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(Locomotive::getOperationNumber));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(Locomotive::getOperationNumber)));
            }
        } else if (sortBy.matches("commissioningDate")) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(Locomotive::getCommissioningDate));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(Locomotive::getCommissioningDate)));
            }
        } else {
            return Response
                    .status(400)
                    .build();
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
        Locomotive locomotive = DataHandler.readLocomotiveBySeriesAndProductionNumber(series, operationNumber);
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

    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response create(
            @FormParam("series") String series,
            @FormParam("operationNumber") Integer operationNumber,
            @FormParam("railwayCompany") String railwayCompany,
            @FormParam("commissioningDate") String commissioningDate,
            @FormParam("signalBoxTrackSection") String signalBoxTrackSection
    ) {
        int status = 200;

        try {
            Locomotive locomotive = new Locomotive();
            locomotive.setSeries(series);
            locomotive.setOperationNumber(operationNumber);
            locomotive.setRailwayCompany(railwayCompany);
            locomotive.setCommissioningDate(
                    LocalDate.parse(commissioningDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            );
            locomotive.setSignalBox(signalBoxTrackSection);
            if (!DataHandler.insertLocomotive(locomotive)) {
                status = 400;
            }
        } catch (DateTimeException e) {
            status = 400;
        }

        return Response
                .status(status)
                .entity("")
                .build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(
            @FormParam("series") String series,
            @FormParam("operationNumber") Integer operationNumber,
            @FormParam("railwayCompany") String railwayCompany,
            @FormParam("commissioningDate") String commissioningDate,
            @FormParam("signalBoxTrackSection") String signalBoxTrackSection
    ) {
        int status = 200;
        Locomotive locomotive = DataHandler.readLocomotiveBySeriesAndProductionNumber(series, operationNumber);
        if (locomotive != null) {
            try {
                locomotive.setRailwayCompany(railwayCompany);
                locomotive.setCommissioningDate(
                        LocalDate.parse(commissioningDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                );
                locomotive.setSignalBox(signalBoxTrackSection);
                DataHandler.updateLocomotive();
            } catch (DateTimeException e) {
                status = 400;
            }
        }

        return Response
                .status(status)
                .entity("")
                .build();
    }

    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response delete(
            @QueryParam("series") String series,
            @QueryParam("operationNumber") Integer operationNumber
    ) {
        int status = 200;
        if (!DataHandler.deleteLocomotive(series, operationNumber)) {
            status = 400;
        }

        return Response
                .status(status)
                .entity("")
                .build();
    }
}