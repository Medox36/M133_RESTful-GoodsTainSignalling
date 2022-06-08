package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.DataHandler;
import ch.giuntini.goodstrainsignalling.model.SignalBox;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * signal box service
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
            @QueryParam("contains") String filter,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sort") String sort
    ) {
        List<SignalBox> signalBoxes = DataHandler.readAllSignalBoxes();
        List<SignalBox> copy = new ArrayList<>(signalBoxes);

        if (filter != null && !filter.isEmpty()) {
            for (int i = 0; i < copy.size(); i++) {
                copy.removeIf(
                        signalBox -> !signalBox.getTrackSection().contains(filter)
                );
            }
        }
        if (sortBy == null) {
            sortBy = "trackSection";
        }
        if (sort == null) {
            sort = "a";
        }
        if (sort.isEmpty() || (!sort.equals("a") && !sort.equals("d"))) {
            return Response
                    .status(400)
                    .build();
        }
        if (sortBy.matches("trackSection") || sortBy.isEmpty()) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(SignalBox::getTrackSection));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(SignalBox::getTrackSection)));
            }
        } else if (sortBy.matches("workingSignalmen")) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(SignalBox::getWorkingSignalmen));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(SignalBox::getWorkingSignalmen)));
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
     * service to get a specific signal box by its track section
     *
     * @param trackSection of the signal box
     * @return a signal box that matches the parameter if there is one
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(
            @QueryParam("section")
            @Size(min = 3, max = 120)
            @NotBlank
            String trackSection
    ) {
        int status = 200;
        SignalBox signalBox = DataHandler.readSignalBoxByTrackSection(trackSection);
        if (signalBox == null) {
            status = 404;
        }
        return Response
                .status(status)
                .entity(signalBox)
                .build();
    }

    /**
     * inserts a new book
     *
     * @param signalBox to be inserted
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response create(@Valid @BeanParam SignalBox signalBox) {
        int status = 200;
        if (!DataHandler.insertSignalBox(signalBox)) {
            status = 400;
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
    public Response update(@Valid @BeanParam SignalBox signalBox) {
        int status = 200;
        SignalBox oldSignalBox = DataHandler.readSignalBoxByTrackSection(signalBox.getTrackSection());
        if (oldSignalBox != null) {
            oldSignalBox.setWorkingSignalmen(signalBox.getWorkingSignalmen());
            DataHandler.updateSignalBox();
        } else {
            status = 410;
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
            @Size(min = 3, max = 120)
            String trackSection
    ) {
        int status = 200;
        if (!DataHandler.deleteSignalBox(trackSection)) {
            status = 400;
        }

        return Response
                .status(status)
                .entity("")
                .build();
    }
}