package com.lector.util.tabular.document;

import com.lector.util.tabular.util.AnnotationUtil;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class TabularDocumentBuilder<T> {

    private Class<T> clazz;

    public TabularDocumentBuilder setClass(Class<T> clazz) {
        this.clazz = clazz;
        return this;
    }

    public TabularDocument<T> build() {
        final TabularDocument<T> tabularDocument = AnnotationUtil.getTabularDocument(clazz);
        AnnotationUtil.getTabularFields(clazz).forEach(tabularDocument::addField);
        return tabularDocument;
    }

}
