package ch.giuntini.goodstrainsignalling.data;

import ch.giuntini.goodstrainsignalling.model.FreightWagon;
import ch.giuntini.goodstrainsignalling.model.Locomotive;
import ch.giuntini.goodstrainsignalling.model.SignalBox;
import ch.giuntini.goodstrainsignalling.service.Config;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
     * reads all locomotives, filters and sorts them
     *
     * @param filter for the series
     * @param sortBy a attribute of the class Locomotive
     * @param sort the parameter sortBy with "a" for ascending or "d" for descending
     * @return list of locomotives
     */
    public static List<Locomotive> readAllLocomotivesWithFilterAndSort(String filter, String sortBy, String sort) {
        List<Locomotive> copy = new ArrayList<>(readAllLocomotives());

        if (filter != null && !filter.isEmpty()) {
            for (int i = 0; i < copy.size(); i++) {
                copy.removeIf(
                        locomotive -> !locomotive.getSeries().contains(filter)
                );
            }
        }

        if (sortBy.matches("series")) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(Locomotive::getSeries));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(Locomotive::getSeries)));
            }
        } else if (sortBy.matches("operationNumber")) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(Locomotive::getOperationNumber));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(Locomotive::getOperationNumber)));
            }
        } else if (sortBy.matches("railwayCompany")) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(Locomotive::getOperationNumber));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(Locomotive::getOperationNumber)));
            }
        } else if (sortBy.matches("commissioningDate")) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(Locomotive::getCommissioningDate));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(Locomotive::getCommissioningDate)));
            }
        }

        return copy;
    }

    /**
     * reads a locomotive by its series ans operation number
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
     * inserts a new locomotive into the locomotiveList
     *
     * @param locomotive the locomotive to be saved
     * @return true if insert is successful otherwise false
     */
    public static boolean insertLocomotive(Locomotive locomotive) {
        if (!locomotiveExists(locomotive)) {
            getLocomotiveList().add(locomotive);
            writeLocomotiveJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if there is already a locomotive with a given series and operationNumber
     *
     * @param locomotive to be checked before adding
     * @return true if there is already a locomotive with the given series and operationNumber otherwise false
     */
    private static boolean locomotiveExists(Locomotive locomotive) {
        for (Locomotive lo : getLocomotiveList()) {
            if (locomotive.getSeries().equalsIgnoreCase(lo.getSeries())
                    && locomotive.getOperationNumber().equals(lo.getOperationNumber())) {
                return true;
            }
        }
        return false;
    }

    /**
     * updates the locomotiveList
     */
    public static void updateLocomotive() {
        writeLocomotiveJSON();
    }

    /**
     * deletes a locomotive identified by the series and operationNumber
     *
     * @param series of the locomotive
     * @param operationNumber of the locomotive
     * @return success=true/false
     */
    public static boolean deleteLocomotive(String series, Integer operationNumber) {
        Locomotive locomotive = readLocomotiveBySeriesAndProductionNumber(series, operationNumber);
        if (locomotive != null) {
            getLocomotiveList().remove(locomotive);
            writeLocomotiveJSON();
            return true;
        } else {
            return false;
        }
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
     * reads all freight wagons, filters and sorts them
     *
     * @param filter for the wagon Number
     * @param sortBy a attribute of the class FreightWagon
     * @param sort the parameter sortBy with"a" for ascending or "d" for descending
     * @return ist of freight wagons
     */
    public static List<FreightWagon> readAllFreightWagonsWithFilterAndSort(String filter, String sortBy, String sort) {
        List<FreightWagon> copy = new ArrayList<>(readAllFreightWagons());

        if (filter != null && !filter.isEmpty()) {
            for (int i = 0; i < copy.size(); i++) {
                copy.removeIf(
                        freightWagon -> !freightWagon.getWaggonNumber().contains(filter)
                );
            }
        }

        if (sortBy.matches("waggonNumber")) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(FreightWagon::getWaggonNumber));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(FreightWagon::getWaggonNumber)));
            }
        } else if (sortBy.matches("lastMainenance")) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(FreightWagon::getLastMaintenance));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(FreightWagon::getLastMaintenance)));
            }
        }

        return copy;
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
     * inserts a new freight wagon into the freightWagonList
     *
     * @param freightWagon the freight wagon to be saved
     * @return true if insert is successful otherwise false
     */
    public static boolean insertFreightWagon(FreightWagon freightWagon) {
        if (!freightWagonExists(freightWagon)) {
            getFreightWagonList().add(freightWagon);
            writeFreightWagonJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if there is already a freight wagon with a given waggon number
     *
     * @param freightWagon to be checked before adding
     * @return true if there is already a freight wagon with the given waggonNumber otherwise false
     */
    private static boolean freightWagonExists(FreightWagon freightWagon) {
        for (FreightWagon fW : getFreightWagonList()) {
            if (freightWagon.getWaggonNumber().equalsIgnoreCase(fW.getWaggonNumber())) {
                return true;
            }
        }
        return false;
    }

    /**
     * updates the freightWagonList
     */
    public static void updateFreightWagon() {
        writeFreightWagonJSON();
    }

    /**
     * deletes a freight wagon identified by the waggonNumber
     *
     * @param waggonNumber of the freightWagon
     * @return success=true/false
     */
    public static boolean deleteFreightWagon(String waggonNumber) {
        FreightWagon freightWagon = readFreightWagonByWaggonNumber(waggonNumber);
        if (freightWagon != null) {
            getFreightWagonList().remove(freightWagon);
            writeFreightWagonJSON();
            return true;
        } else {
            return false;
        }
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
     * reads all signal boxes, filters and sorts them
     *
     * @param filter for the track section
     * @param sortBy a attribute in the class SignalBox
     * @param sort the parameter sortBy with "a" for ascending or "d" for descending
     * @return list of signal boxes
     */
    public static List<SignalBox> readAllSignalBoxesWithFilterAndSort(String filter, String sortBy, String sort) {
        List<SignalBox> copy = new ArrayList<>(readAllSignalBoxes());

        if (filter != null && !filter.isEmpty()) {
            for (int i = 0; i < copy.size(); i++) {
                copy.removeIf(
                        signalBox -> !signalBox.getTrackSection().contains(filter)
                );
            }
        }

        if (sortBy.matches("trackSection")) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(SignalBox::getTrackSection));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(SignalBox::getTrackSection)));
            }
        } else if (sortBy.matches("workingSignalmen")) {
            if (sort.equals("a")) {
                copy.sort(Comparator.comparing(SignalBox::getWorkingSignalmen));
            } else {
                copy.sort(Collections.reverseOrder(Comparator.comparing(SignalBox::getWorkingSignalmen)));
            }
        }

        return copy;
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
     * inserts a new signal box into the signalBoxList
     *
     * @param signalBox the signal box to be saved
     * @return true if insert is successful otherwise false
     */
    public static boolean insertSignalBox(SignalBox signalBox) {
        if (!signalBoxExists(signalBox)) {
            getSignalBoxList().add(signalBox);
            writeSignalBoxJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if there is already a signal box with a given track section
     *
     * @param signalBox to be checked before adding
     * @return true if there is already a signal box with the given track section otherwise false
     */
    private static boolean signalBoxExists(SignalBox signalBox) {
        for (SignalBox sB : getSignalBoxList()) {
            if (signalBox.getTrackSection().equalsIgnoreCase(sB.getTrackSection())) {
                return true;
            }
        }
        return false;
    }

    /**
     * updates the signalBoxList
     */
    public static void updateSignalBox() {
        writeSignalBoxJSON();
    }

    /**
     * deletes a signal box identified by the trackSection
     *
     * @param trackSection of the signalBox
     * @return success=true/false
     */
    public static boolean deleteSignalBox(String trackSection) {
        SignalBox signalBox = readSignalBoxByTrackSection(trackSection);
        if (signalBox != null) {
            getSignalBoxList().remove(signalBox);
            writeSignalBoxJSON();
            return true;
        } else {
            return false;
        }
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
     * writes the locomotiveList to the JSON-file
     */
    private static void writeLocomotiveJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream;
        Writer fileWriter;

        String bookPath = Config.getProperty("locomotiveJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getLocomotiveList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the freightWagonList to the JSON-file
     */
    private static void writeFreightWagonJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream;
        Writer fileWriter;

        String bookPath = Config.getProperty("freightWagonJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getFreightWagonList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the signalBoxList to the JSON-file
     */
    private static void writeSignalBoxJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream;
        Writer fileWriter;

        String bookPath = Config.getProperty("signalBoxJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getSignalBoxList());
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
