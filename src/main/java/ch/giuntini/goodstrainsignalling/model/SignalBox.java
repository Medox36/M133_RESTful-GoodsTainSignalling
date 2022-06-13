package ch.giuntini.goodstrainsignalling.model;

/**
 * Signal box responsible for a specific track section with a number of working signalmen
 */
public class SignalBox {
    private String trackSection;
    private Integer workingSignalmen;



    // getters and setters
    public String getTrackSection() {
        return trackSection;
    }

    public void setTrackSection(String trackSection) {
        this.trackSection = trackSection;
    }

    public Integer getWorkingSignalmen() {
        return workingSignalmen;
    }

    public void setWorkingSignalmen(Integer workingSignalmen) {
        this.workingSignalmen = workingSignalmen;
    }
}