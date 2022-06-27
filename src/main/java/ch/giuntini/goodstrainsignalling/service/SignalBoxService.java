package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.DataHandler;
import ch.giuntini.goodstrainsignalling.model.SignalBox;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * signal box service
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.05.18
 * @version 1.3
 */
@Path("signalbox")
public class SignalBoxService {

    /**
     * service to get all signal boxes
     *
     * signal boxes can be filtered and sorted by a attribute of the class in ascending and descending order
     *
     * @param filter for the track section
     * @param sortBy a attribute in the class SignalBox
     * @param sort the parameter sortBy with "a" for ascending or "d" for descending
     * @return list of signal boxes
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(
            @QueryParam("contains")
            String filter,

            @QueryParam("sortBy")
            @DefaultValue("trackSection")
            String sortBy,

            @QueryParam("sort")
            @DefaultValue("a")
            String sort,

            @CookieParam("auth") String auth,
            @CookieParam("userRole") String role
    ) {
        int status = 200;
        List<SignalBox> list = null;

        if (UserService.isUserAuthorized(role, auth)) {
            if ((sort.isEmpty() || (!sort.equals("a") && !sort.equals("d")))
                    || (!sortBy.matches("trackSection") && !sortBy.matches("workingSignalmen"))) {
                status = 400;
            }
            list = DataHandler.readAllSignalBoxesWithFilterAndSort(filter, sortBy, sort);
        } else {
            status = 401;
        }

        return Response
                .status(status)
                .entity(list)
                .build();
    }

    /**
     * service to get a specific signal box by its track section
     *
     * @param trackSection of the signal box
     * @return a signal box that matches the parameter if there is one
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(
            @QueryParam("trackSection")
            @Size(min = 3, max = 120)
            @NotBlank
            String trackSection,

            @CookieParam("auth") String auth,
            @CookieParam("userRole") String role
    ) {
        int status = 200;
        SignalBox signalBox = null;
        if (UserService.isUserAuthorized(role, auth)) {
            signalBox = DataHandler.readSignalBoxByTrackSection(trackSection);
            if (signalBox == null) {
                status = 404;
            }
        } else {
            status = 401;
        }
        return Response
                .status(status)
                .entity(signalBox)
                .build();
    }

    /**
     * inserts a new signal box
     *
     * @param signalBox to be inserted
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response create(
            @Valid @BeanParam SignalBox signalBox,
            @CookieParam("auth") String auth,
            @CookieParam("userRole") String role
    ) {
        int status = 200;

        if (UserService.isUserAuthorizedAdmin(role, auth)) {
            if (!DataHandler.insertSignalBox(signalBox)) {
                status = 400;
            }
        } else {
            status = 401;
        }

        return Response
                .status(status)
                .entity("")
                .build();
    }

    /**
     * updates a signal box
     *
     * @param signalBox the updated signal box
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(
            @Valid @BeanParam SignalBox signalBox,
            @CookieParam("auth") String auth,
            @CookieParam("userRole") String role
    ) {
        int status = 200;

        if (UserService.isUserAuthorizedAdmin(role, auth)) {
            SignalBox oldSignalBox = DataHandler.readSignalBoxByTrackSection(signalBox.getTrackSection());
            if (oldSignalBox != null) {
                oldSignalBox.setWorkingSignalmen(signalBox.getWorkingSignalmen());
                DataHandler.updateSignalBox();
            } else {
                status = 410;
            }
        } else {
            status = 401;
        }

        return Response
                .status(status)
                .entity("")
                .build();
    }

    /**
     * deletes a signal box identified by its track section
     *
     * @param trackSection of the signal box
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response delete(
            @QueryParam("trackSection")
            @NotBlank
            @Size(min = 3, max = 120)
            String trackSection,

            @CookieParam("auth") String auth,
            @CookieParam("userRole") String role
    ) {
        int status = 200;

        if (UserService.isUserAuthorizedAdmin(role, auth)) {
            if (!DataHandler.deleteSignalBox(trackSection)) {
                status = 400;
            }
        } else {
            status = 401;
        }

        return Response
                .status(status)
                .entity("")
                .build();
    }
}