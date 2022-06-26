package ch.giuntini.goodstrainsignalling.model;

import ch.giuntini.goodstrainsignalling.data.DataHandler;
import ch.giuntini.goodstrainsignalling.util.LocalDateDeserializer;
import ch.giuntini.goodstrainsignalling.util.LocalDateSerializer;

import ch.giuntini.goodstrainsignalling.util.SignalBoxDeserializer;
import ch.giuntini.goodstrainsignalling.util.SignalBoxSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.time.LocalDate;
import java.util.List;

/**
 * a Locomotive belonging to a railway company that can pull 0 or more freight wagons
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.05.18
 * @version 1.4
 */
public class Locomotive {
    @FormParam("series")
    @Pattern(regexp = "([A-Za-z]{1,5}) (([1-9]+[x]?[1-9]*/[1-9]+)|([0-9]{1,3})|(TEE))([\\^]?)([IV]{0,3})",
            message = "Must be a series of the SBB CFF FFS naming standard")
    @NotBlank
    private String series;

    @FormParam("operationNumber")
    @NotNull
    @Min(101)
    @Max(18841)
    private Integer operationNumber;

    @FormParam("railwayCompany")
    @NotBlank
    @Size(min = 2, max = 10)
    private String railwayCompany;

    @FormParam("commissioningDate")
    @Past
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate commissioningDate;

    @JsonSerialize(using = SignalBoxSerializer.class)
    @JsonDeserialize(using = SignalBoxDeserializer.class)
    private SignalBox signalBox;

    private List<FreightWagon> freightWagons;



    // getters and setters
    /**
     * gets series
     *
     * @return value of series
     */
    public String getSeries() {
        return series;
    }

    /**
     * sets series
     *
     * @param series the value to set
     */
    public void setSeries(String series) {
        this.series = series;
    }

    /**
     * gets operationNumber
     *
     * @return value of operationNumber
     */
    public Integer getOperationNumber() {
        return operationNumber;
    }

    /**
     * sets operationNumber
     *
     * @param operationNumber the value to set
     */
    public void setOperationNumber(Integer operationNumber) {
        this.operationNumber = operationNumber;
    }

    /**
     * gets railwayCompany
     *
     * @return value of railwayCompany
     */
    public String getRailwayCompany() {
        return railwayCompany;
    }

    /**
     * sets railwayCompany
     *
     * @param railwayCompany the value to set
     */
    public void setRailwayCompany(String railwayCompany) {
        this.railwayCompany = railwayCompany;
    }

    /**
     * gets commissioningDate
     *
     * @return value of commissioningDate
     */
    public LocalDate getCommissioningDate() {
        return commissioningDate;
    }

    /**
     * sets commissioningDate
     *
     * @param commissioningDate the value to set
     */
    public void setCommissioningDate(LocalDate commissioningDate) {
        this.commissioningDate = commissioningDate;
    }

    /**
     * gets signalBox
     *
     * @return value of signalBox
     */
    public SignalBox getSignalBox() {
        return signalBox;
    }

    /**
     * sets signalBox
     *
     * @param signalBox the value to set
     */
    public void setSignalBox(SignalBox signalBox) {
        this.signalBox = signalBox;
    }

    /**
     * sets signalBox
     *
     * @param trackSection the value to set
     */
    @JsonIgnore
    public void setSignalBox(String trackSection) {
        setSignalBox(new SignalBox());
        SignalBox signalBox = DataHandler.readSignalBoxByTrackSection(trackSection);
        getSignalBox().setTrackSection(trackSection);
        getSignalBox().setWorkingSignalmen(signalBox.getWorkingSignalmen());
    }

    /**
     * gets freightWagons
     *
     * @return value of freightWagons
     */
    public List<FreightWagon> getFreightWagons() {
        return freightWagons;
    }

    /**
     * sets freightWagons
     *
     * @param freightWagons the value to set
     */
    public void setFreightWagons(List<FreightWagon> freightWagons) {
        this.freightWagons = freightWagons;
    }
}