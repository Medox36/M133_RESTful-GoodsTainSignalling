package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.DataHandler;
import ch.giuntini.goodstrainsignalling.model.SignalBox;

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
 * signal box service
 */
@Path("signalbox")
public class SignalBoxService {

    /**
     * service to get all signal boxes
     *
     * signal boxes can be filtered and sorted ascending and descending
     * by their track section
     *
     * @param filter
     * @param sort "a" for ascending or "d" for descending
     * @return list of signal boxes
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("contains") String filter, @QueryParam("sort") String sort) {
        List<SignalBox> signalBoxes = DataHandler.getInstance().readAllSignalBoxes();
        List<SignalBox> copy = new ArrayList<>(signalBoxes.size());
        Collections.copy(copy, signalBoxes);

        if (filter != null && !filter.isEmpty()) {
            for (int i = 0; i < copy.size(); i++) {
                copy.removeIf(
                        signalBox -> !signalBox.getTrackSection().contains(filter)
                );
            }
        }
        if (sort != null && !sort.isEmpty()) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(SignalBox::getTrackSection));
            } else if (sort.equals("d")) {
                copy.sort(
                        (signalBox, t1) -> t1.getTrackSection().compareToIgnoreCase(signalBox.getTrackSection())
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
     * service to get a specific signal box by its track section
     *
     * @param trackSection of the signal box
     * @return a signal box that matches the parameter if there is one
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@QueryParam("section") String trackSection) {
        if (trackSection.isEmpty()) {
            return Response
                    .status(400)
                    .build();
        }
        SignalBox signalBox = DataHandler.getInstance().readSignalBoxByTrackSection(trackSection);
        if (signalBox == null) {
            return Response
                    .status(404)
                    .build();
        }
        return Response
                .status(200)
                .entity(signalBox)
                .build();
    }
}