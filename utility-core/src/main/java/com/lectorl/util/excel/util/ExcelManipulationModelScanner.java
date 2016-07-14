package com.lectorl.util.excel.util;

import com.lectorl.util.excel.annotation.Row;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;

import java.util.Set;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/8/2016
 */
public class ExcelManipulationModelScanner {

    private static final Log logger = LogFactory.getLog(ExcelManipulationModelScanner.class);

    public static Set<Class<?>> scan(String path) {
        logger.info("Searching classpath for models ...");
        final Reflections reflections = new Reflections(path);
        final Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Row.class);
        typesAnnotatedWith
                .stream()
                .map(s-> "Annotated model found -> <" + s.getName() + ">.")
                .forEach(logger::info);
        logger.debug("Searching has finished.");
        return typesAnnotatedWith;
    }

}
