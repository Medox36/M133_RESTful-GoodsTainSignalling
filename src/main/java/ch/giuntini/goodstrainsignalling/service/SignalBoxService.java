package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.data.DataHandler;
import ch.giuntini.goodstrainsignalling.model.SignalBox;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("signalbox")
public class SignalBoxService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        List<SignalBox> signalBoxes = DataHandler.getInstance().readAllSignalBoxes();
        return Response
                .status(200)
                .entity(signalBoxes)
                .build();
    }

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
        return Response
                .status(200)
                .entity(signalBox)
                .build();
    }
}
