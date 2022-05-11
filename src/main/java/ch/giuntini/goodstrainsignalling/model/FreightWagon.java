package ch.giuntini.goodstrainsignalling.model;

import java.time.LocalDateTime;

/**
 * a FreightWagon that can pe pulled by a locomotive
 */
public class FreightWagon {
    private String waggonNumber;
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