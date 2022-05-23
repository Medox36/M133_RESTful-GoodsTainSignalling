package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.DataHandler;
import ch.giuntini.goodstrainsignalling.model.SignalBox;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        if (filter != null && !filter.isEmpty()) {
            for (int i = 0; i < signalBoxes.size(); i++) {
                signalBoxes.removeIf(
                        freightWagon -> !freightWagon.getTrackSection().toUpperCase().contains(filter.toUpperCase())
                );
            }
        }
        if (sort != null && !sort.isEmpty()) {
            if (!sort.equals("a") && !sort.equals("d")) {
                return Response
                        .status(400)
                        .build();
            }
            if (sort.equals("a")) {
                signalBoxes.sort(Comparator.comparing(SignalBox::getTrackSection));
            } else if (sort.equals("d")) {
                signalBoxes.sort(
                        (freightWagon, t1) -> t1.getTrackSection().compareToIgnoreCase(freightWagon.getTrackSection())
                );
            }
        }
        return Response
                .status(200)
                .entity(signalBoxes)
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