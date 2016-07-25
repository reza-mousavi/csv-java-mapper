package com.lector.util.excel.document;

import com.lector.util.excel.annotation.Row;
import com.lector.util.excel.exception.NoModelException;
import com.lector.util.excel.util.AnnotationUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class ExcelDocumentBuilder<T> {

    private static final Log logger = LogFactory.getLog(ExcelDocumentBuilder.class);

    private Class<T> clazz;

    public ExcelDocumentBuilder setClass(Class<T> clazz) {
        this.clazz = clazz;
        return this;
    }

    public TabularDocument<T> build() {
        if (!clazz.isAnnotationPresent(Row.class)) {
            throw new NoModelException(
                    "Class : " + clazz.getName() + " is not a valid model. " +
                            "It should have the annotation <Row> over it.");
        }

        final TabularDocument<T> tabularDocument = AnnotationUtil.getTabularDocument(clazz);
        AnnotationUtil.getTabularFields(clazz).forEach(tabularDocument::addExcelField);
        logger.debug("------------All fields has been read -----------.");
        return tabularDocument;
    }

}
