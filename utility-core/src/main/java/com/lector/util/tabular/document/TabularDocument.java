package com.lector.util.tabular.document;

import com.lector.util.tabular.util.AnnotationUtil;

import java.util.*;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/5/2016
 */
public class TabularDocument<T> {

    public static TabularDocument<Object> EMPTY = new TabularDocument<>(null);

    private TabularRow<T> tabularRow;

    private SortedSet<TabularField> tabularFields;

    public TabularDocument(TabularRow<T> tabularRow) {
        this.tabularRow = tabularRow;
        this.tabularFields = new TreeSet<>(Comparator.comparing(TabularField::getPosition));
    }

    public TabularRow<T> getTabularRow() {
        return this.tabularRow;
    }

    public Set<TabularField> getTabularFields() {
        return this.tabularFields;
    }

    public void addField(TabularField tabularField) {
        this.tabularFields.add(tabularField);
    }

    public T newInstance() {
        final TabularRow<T> tabularRow = getTabularRow();
        final Class<T> clazz = tabularRow.getClazz();
        return AnnotationUtil.createNewInstance(clazz);
    }

}
