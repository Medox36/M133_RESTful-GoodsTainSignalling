package ch.giuntini.goodstrainsignalling.exceptionmapper;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * ExceptionMapper to assure the custom messages from own constraints are being sent to the client
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.06.13
 * @version 1.0
 */
@Provider
public class MyExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .type("text/plain")
                .build();
    }

    private String prepareMessage(ConstraintViolationException exception) {
        StringBuilder msg = new StringBuilder();
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            msg.append(cv.getPropertyPath()).append(" ").append(cv.getMessage()).append("\n");
        }
        return msg.toString();
    }
}