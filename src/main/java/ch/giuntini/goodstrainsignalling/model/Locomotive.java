package ch.giuntini.goodstrainsignalling.model;

import java.time.LocalDate;
import java.util.List;

/**
 * a Locomotive belonging to a railway company that can pull 0 or more freight wagons
 */
public class Locomotive {
    private String series;
    private Integer operationNumber;
    private String railwayCompany;
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