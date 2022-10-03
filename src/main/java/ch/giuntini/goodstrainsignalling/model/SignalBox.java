package ch.giuntini.goodstrainsignalling.model;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;

/**
 * Signal box responsible for a specific track section with a number of working signalmen
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.05.18
 * @version 1.4
 */
public class SignalBox {
    @FormParam("trackSection")
    @NotBlank
    @Size(min = 3, max = 120)
    private String trackSection;

    @FormParam("workingSignalmen")
    @NotNull
    @Min(0)
    @Max(255)
    private Integer workingSignalmen;



    // getters and setters
    /**
     * gets trackSection
     *
     * @return value of trackSection
     */
    public String getTrackSection() {
        return trackSection;
    }

    /**
     * sets traackSection
     *
     * @param trackSection the value to set
     */
    public void setTrackSection(String trackSection) {
        this.trackSection = trackSection;
    }

    /**
     * gets workingSignalmen
     *
     * @return value of workingSignalmen
     */
    public Integer getWorkingSignalmen() {
        return workingSignalmen;
    }

    /**
     * sets workingSignalmen
     *
     * @param workingSignalmen the value to set
     */
    public void setWorkingSignalmen(Integer workingSignalmen) {
        this.workingSignalmen = workingSignalmen;
    }
}
