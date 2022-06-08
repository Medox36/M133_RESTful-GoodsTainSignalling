package ch.giuntini.goodstrainsignalling.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;
import java.time.LocalDateTime;

/**
 * a FreightWagon that can pe pulled by a locomotive
 */
public class FreightWagon {
    @FormParam("waggonNumber")
    @NotBlank
    @Pattern(regexp = "([0-9]{2} ){2}[0-9]{4} [0-9]{3}-[0-9]")
    private String waggonNumber;

    @FormParam("lastMaintenance")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastMaintenance;

    @FormParam("handbrakeIsOn")
    @NotNull
    private Boolean handbrakeIsOn;



    // getters and setters
    /**
     * gets waggonNumber
     *
     * @return value of waggonNumber
     */
    public String getWaggonNumber() {
        return waggonNumber;
    }

    /**
     * sets waggonNumber
     *
     * @param waggonNumber the value to set
     */
    public void setWaggonNumber(String waggonNumber) {
        this.waggonNumber = waggonNumber;
    }

    /**
     * gets lastMaintenance
     *
     * @return value of lastMaintenance
     */
    public LocalDateTime getLastMaintenance() {
        return lastMaintenance;
    }

    /**
     * sets lastMaintenance
     *
     * @param lastMaintenance the value to set
     */
    public void setLastMaintenance(LocalDateTime lastMaintenance) {
        this.lastMaintenance = lastMaintenance;
    }

    /**
     * gets trackSection
     *
     * @return value of trackSection
     */
    public Boolean getHandbrakeIsOn() {
        return handbrakeIsOn;
    }

    /**
     * sets handbrakeIsOn
     *
     * @param handbrakeIsOn the value to set
     */
    public void setHandbrakeIsOn(Boolean handbrakeIsOn) {
        this.handbrakeIsOn = handbrakeIsOn;
    }
}