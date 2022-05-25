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
public final class DataHandler {
    private static List<Locomotive> locomotiveList;
    private static List<FreightWagon> freightWagonList;
    private static List<SignalBox> signalBoxList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
    }

    /**
     * reads all locomotives
     *
     * @return list of locomotives
     */
    public static List<Locomotive> readAllLocomotives() {
        if (locomotiveList == null) {
            setLocomotiveList(new ArrayList<>());
            readLocomotiveJSON();
        }
        return getLocomotiveList();
    }

    /**
     * reads a locomotive by its uuid
     *
     * @param series of the locomotive
     * @param operationNumber of the locomotive
     * @return the Publisher (null=not found)
     */
    public static Locomotive readLocomotiveBySeriesAndProductionNumber(String series, Integer operationNumber) {
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
    public static List<FreightWagon> readAllFreightWagons() {
        if (freightWagonList == null) {
            setFreightWagonList(new ArrayList<>());
            readFreightWagonJSON();
        }
        return getFreightWagonList();
    }

    /**
     * reads a freight wagon by its waggon number
     *
     * @param waggonNumber of the freight waggon
     * @return the freight wagon (null=not found)
     */
    public static FreightWagon readFreightWagonByWaggonNumber(String waggonNumber) {
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
    public static List<SignalBox> readAllSignalBoxes() {
        if (signalBoxList == null) {
            setSignalBoxList(new ArrayList<>());
            readSignalBoxJSON();
        }
        return getSignalBoxList();
    }

    /**
     * reads a signal box ba its track section
     *
     * @param trackSection of the signalbox
     * @return the signal box
     */
    public static SignalBox readSignalBoxByTrackSection(String trackSection) {
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
    private static void readLocomotiveJSON() {
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
    private static void readFreightWagonJSON() {
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
    private static void readSignalBoxJSON() {
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
    private static List<Locomotive> getLocomotiveList() {
        return locomotiveList;
    }

    /**
     * sets locomotiveList
     *
     * @param locomotiveList the value to set
     */
    private static void setLocomotiveList(List<Locomotive> locomotiveList) {
        DataHandler.locomotiveList = locomotiveList;
    }

    /**
     * gets freightWagonList
     *
     * @return value of freightWagonList
     */
    private static List<FreightWagon> getFreightWagonList() {
        return freightWagonList;
    }

    /**
     * sets freightWagonList
     *
     * @param freightWagonList the value to set
     */
    private static void setFreightWagonList(List<FreightWagon> freightWagonList) {
        DataHandler.freightWagonList = freightWagonList;
    }

    /**
     * gets freightWagonList
     *
     * @return value of freightWagonList
     */
    private static List<SignalBox> getSignalBoxList() {
        return signalBoxList;
    }

    /**
     * sets freightWagonList
     *
     * @param signalBoxList the value to set
     */
    private static void setSignalBoxList(List<SignalBox> signalBoxList) {
        DataHandler.signalBoxList = signalBoxList;
    }
}
