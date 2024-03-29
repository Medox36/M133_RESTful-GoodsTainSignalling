package ch.giuntini.goodstrainsignalling.service;

import ch.giuntini.goodstrainsignalling.exceptionmapper.MyExceptionMapper;
import ch.giuntini.goodstrainsignalling.paramconverter.LocalDateParamConverterProvider;
import ch.giuntini.goodstrainsignalling.paramconverter.LocalDateTimeParamConverterProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * configure the web services and properties
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.05.11
 * @version 1.1
 */
@ApplicationPath("/resource")
public class Config extends Application {
    private static final String PROPERTIES_PATH = "/home/bzz/webapp/goodsTrainSignalling.properties";
    private static Properties properties = null;

    /**
     * define all provider classes
     *
     * @return set of classes
     */
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> providers = new HashSet<>();
        providers.add(TestService.class);
        providers.add(UserService.class);
        providers.add(LocomotiveService.class);
        providers.add(FreightWagonService.class);
        providers.add(SignalBoxService.class);
        providers.add(LocalDateParamConverterProvider.class);
        providers.add(LocalDateTimeParamConverterProvider.class);
        providers.add(MyExceptionMapper.class);

        return providers;
    }

    /**
     * Gets the value of a property
     *
     * @param property the key of the property to be read
     * @return the value of the property
     */
    public static String getProperty(String property) {
        if (Config.properties == null) {
            setProperties(new Properties());
            readProperties();
        }
        String value = Config.properties.getProperty(property);
        if (value == null) return "";
        return value;
    }

    /**
     * reads the properties file
     */
    private static void readProperties() {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(PROPERTIES_PATH);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Sets the properties
     *
     * @param properties the value to set
     */
    private static void setProperties(Properties properties) {
        Config.properties = properties;
    }
}
