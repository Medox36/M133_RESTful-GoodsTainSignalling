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
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Integer getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(Integer operationNumber) {
        this.operationNumber = operationNumber;
    }

    public String getRailwayCompany() {
        return railwayCompany;
    }

    public void setRailwayCompany(String railwayCompany) {
        this.railwayCompany = railwayCompany;
    }

    public LocalDate getCommissioningDate() {
        return commissioningDate;
    }

    public void setCommissioningDate(LocalDate commissioningDate) {
        this.commissioningDate = commissioningDate;
    }

    public SignalBox getSignalBox() {
        return signalBox;
    }

    public void setSignalBox(SignalBox signalBox) {
        this.signalBox = signalBox;
    }

    public List<FreightWagon> getFreightWagons() {
        return freightWagons;
    }

    public void setFreightWagons(List<FreightWagon> freightWagons) {
        this.freightWagons = freightWagons;
    }
}