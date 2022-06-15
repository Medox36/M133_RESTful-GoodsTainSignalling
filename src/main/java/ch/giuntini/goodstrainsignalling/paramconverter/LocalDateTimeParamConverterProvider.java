package ch.giuntini.goodstrainsignalling.paramconverter;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * provider for LocalDateTime parameter-converter
 * @see LocalDateTimeConverter
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.06.13
 * @version 1.1
 */
@Provider
public class LocalDateTimeParamConverterProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType,
                                              Annotation[] annotations) {
        if (rawType.equals(LocalDateTime.class))
            return (ParamConverter<T>) new LocalDateTimeConverter();
        return null;
    }
}