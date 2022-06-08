package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.DataHandler;
import ch.giuntini.goodstrainsignalling.model.FreightWagon;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * freight wagon service
 */
@Path("freightwagon")
public class FreightWagonService {

    /**
     * service to get all freight wagons
     *
     * freight wagons can fe filtered and sorted by a attribute of the class in ascending and descending order
     *
     * @param filter for the wagon Number
     * @param sortBy a attribute of the class FreightWagon
     * @param sort the parameter sortBy with"a" for ascending or "d" for descending
     * @return list of freight wagons
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(
            @QueryParam("contains")
            String filter,

            @QueryParam("sortBy")
            @DefaultValue("waggonNumber")
            String sortBy,

            @QueryParam("sort")
            @DefaultValue("a")
            String sort
    ) {
        if ((sort.isEmpty() || (!sort.equals("a") && !sort.equals("d")))
                || (!sortBy.matches("waggonNumber") && !sortBy.matches("lastMainenance"))) {
            return Response
                    .status(400)
                    .build();
        }
        List<FreightWagon> list = DataHandler.readAllFreightWagonsWithFilterAndSort(filter, sortBy, sort);

        return Response
                .status(200)
                .entity(list)
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
    public Response read(
            @QueryParam("wn")
            @Pattern(regexp = "([0-9]{2} ){2}[0-9]{4} [0-9]{3}-[0-9]")
            String wagonNumber
    ) {
        FreightWagon freightWagon = DataHandler.readFreightWagonByWaggonNumber(wagonNumber);
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

    /**
     * inserts a new freight wagon
     *
     * @param freightWagon to be inserted
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response create(@Valid @BeanParam FreightWagon freightWagon) {
        int status = 200;
        if (!DataHandler.insertFreightWagon(freightWagon)) {
            status = 400;
        }
        return Response
                .status(status)
                .entity("")
                .build();
    }

    /**
     * updates a freight wagon
     *
     * @param freightWagon the updated freight wagon
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(@Valid @BeanParam FreightWagon freightWagon) {
        int status = 200;

        FreightWagon oldFreightWagon = DataHandler.readFreightWagonByWaggonNumber(freightWagon.getWaggonNumber());
        if (oldFreightWagon != null) {
            oldFreightWagon.setLastMaintenance(freightWagon.getLastMaintenance());
            oldFreightWagon.setHandbrakeIsOn(freightWagon.getHandbrakeIsOn());

            DataHandler.updateFreightWagon();
        } else {
            status = 410;
        }

        return Response
                .status(status)
                .entity("")
                .build();
    }

    /**
     * deletes a freight wagon identified by its waggon number
     *
     * @param waggonNumber of the freight wagon
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response delete(
            @QueryParam("waggonNumber")
            @Pattern(regexp = "([0-9]{2} ){2}[0-9]{4} [0-9]{3}-[0-9]")
            @NotBlank
            String waggonNumber
    ) {
        int status = 200;
        if (!DataHandler.deleteFreightWagon(waggonNumber)) {
            status = 400;
        }

        return Response
                .status(status)
                .entity("")
                .build();
    }
}