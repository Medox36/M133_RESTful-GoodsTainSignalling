package ch.giuntini.goodstrainsignalling.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.List;

/**
 * a Locomotive belonging to a railway company that can pull 0 or more freight wagons
 */
public class Locomotive {
    private String series;
    private Integer operationNumber;
    private String railwayCompany;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate commissioningDate;
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