package ch.giuntini.goodstrainsignalling.data;

import ch.giuntini.goodstrainsignalling.model.FreightWagon;
import ch.giuntini.goodstrainsignalling.model.Locomotive;
import ch.giuntini.goodstrainsignalling.model.SignalBox;
import ch.giuntini.goodstrainsignalling.service.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Locomotive> locomotiveList;
    private List<FreightWagon> freightWagonList;
    private List<SignalBox> signalBoxList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setLocomotiveList(new ArrayList<>());
        readLocomotiveJSON();
        setFreightWagonList(new ArrayList<>());
        readFreightWagonJSON();
        setSignalBoxList(new ArrayList<>());
        readSignalBoxJSON();
    }

    /**
     * reads all locomotives
     *
     * @return list of locomotives
     */
    public List<Locomotive> readAllLocomotives() {
        return getLocomotiveList();
    }

    /**
     * reads a locomotive by its uuid
     *
     * @param series of the locomotive
     * @param operationNumber of the locomotive
     * @return the Publisher (null=not found)
     */
    public Locomotive readLocomotiveBySeriesAndProductionNumber(String series, Integer operationNumber) {
        Locomotive locomotive = null;
        for (Locomotive entry : getLocomotiveList()) {
            if (entry.getSeries().equals(series) && entry.getOperationNumber().equals(operationNumber)) {
                locomotive = entry;
            }
        }
        return locomotive;
    }

    /**
     * reads all freight wagons
     *
     * @return list of freight wagons
     */
    public List<FreightWagon> readAllFreightWagons() {
        return getFreightWagonList();
    }

    /**
     * reads a freight wagon by its waggon number
     *
     * @param waggonNumber of the freight waggon
     * @return the freight wagon (null=not found)
     */
    public FreightWagon readFreightWagonByWaggonNumber(String waggonNumber) {
        FreightWagon freightWagon = null;
        for (FreightWagon entry : getFreightWagonList()) {
            if (entry.getWaggonNumber().equals(waggonNumber)) {
                freightWagon = entry;
            }
        }
        return freightWagon;
    }

    /**
     * reads all signal boxes
     *
     * @return list of signal boxes
     */
    public List<SignalBox> readAllSignalBoxes() {
        return getSignalBoxList();
    }

    /**
     * reads a signal box ba its track section
     *
     * @param trackSection of the signalbox
     * @return the signal box
     */
    public SignalBox readSignalBoxByTrackSection(String trackSection) {
        SignalBox signalBox = null;
        for (SignalBox entry : getSignalBoxList()) {
            if (entry.getTrackSection().equals(trackSection)) {
                signalBox = entry;
            }
        }
        return signalBox;
    }

    /**
     * reads the locomotives from the JSON-file
     */
    private void readLocomotiveJSON() {
        try {
            String path = Config.getProperty("locomotiveJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Locomotive[] locomotives = objectMapper.readValue(jsonData, Locomotive[].class);
            for (Locomotive locomotive : locomotives) {
                getLocomotiveList().add(locomotive);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the freight wagons from the JSON-file
     */
    private void readFreightWagonJSON() {
        try {
            String path = Config.getProperty("freightWagonJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            FreightWagon[] freightWagons = objectMapper.readValue(jsonData, FreightWagon[].class);
            for (FreightWagon freightWagon : freightWagons) {
                getFreightWagonList().add(freightWagon);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the signal boxes from the JSON-file
     */
    private void readSignalBoxJSON() {
        try {
            String path = Config.getProperty("signalBoxJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            SignalBox[] signalBoxes = objectMapper.readValue(jsonData, SignalBox[].class);
            for (SignalBox signalBox : signalBoxes) {
                getSignalBoxList().add(signalBox);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * gets locomotiveList
     *
     * @return value of locomotiveList
     */
    private List<Locomotive> getLocomotiveList() {
        return locomotiveList;
    }

    /**
     * sets locomotiveList
     *
     * @param locomotiveList the value to set
     */
    private void setLocomotiveList(List<Locomotive> locomotiveList) {
        this.locomotiveList = locomotiveList;
    }

    /**
     * gets freightWagonList
     *
     * @return value of freightWagonList
     */
    private List<FreightWagon> getFreightWagonList() {
        return freightWagonList;
    }

    /**
     * sets freightWagonList
     *
     * @param freightWagonList the value to set
     */
    private void setFreightWagonList(List<FreightWagon> freightWagonList) {
        this.freightWagonList = freightWagonList;
    }

    /**
     * gets freightWagonList
     *
     * @return value of freightWagonList
     */
    private List<SignalBox> getSignalBoxList() {
        return signalBoxList;
    }

    /**
     * sets freightWagonList
     *
     * @param signalBoxList the value to set
     */
    private void setSignalBoxList(List<SignalBox> signalBoxList) {
        this.signalBoxList = signalBoxList;
    }

    /**
     * gets the only instance of this class
     *
     * @return only instance of this class
     */
    public static DataHandler getInstance() {
        if (instance == null) {
            instance = new DataHandler();
        }
        return instance;
    }
}
