package ch.giuntini.goodstrainsignalling.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

/**
 * a FreightWagon that can pe pulled by a locomotive
 */
public class FreightWagon {
    private String waggonNumber;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastMaintenance;
    private Boolean handbrakeIsOn;



    // getters and setters
    public String getWaggonNumber() {
        return waggonNumber;
    }

    public void setWaggonNumber(String waggonNumber) {
        this.waggonNumber = waggonNumber;
    }

    public LocalDateTime getLastMaintenance() {
        return lastMaintenance;
    }

    public void setLastMaintenance(LocalDateTime lastMaintenance) {
        this.lastMaintenance = lastMaintenance;
    }

    public Boolean getHandbrakeIsOn() {
        return handbrakeIsOn;
    }

    public void setHandbrakeIsOn(Boolean handbrakeIsOn) {
        this.handbrakeIsOn = handbrakeIsOn;
    }
}