package com.lector.util.tabular;

import com.lector.util.tabular.io.TabularDocumentConsumer;
import com.lector.util.tabular.io.TabularDocumentProducer;

/**
 * Created by Reza Mousavi reza.mousavi@lector.dk on 7/18/2016
 */
public abstract class AbstractDocumentManipulator implements DocumentManipulator {

    protected TabularDocumentConsumer consumer;
    protected TabularDocumentProducer producer;

    public TabularDocumentConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(TabularDocumentConsumer consumer) {
        this.consumer = consumer;
    }

    public TabularDocumentProducer getProducer() {
        return producer;
    }

    public void setProducer(TabularDocumentProducer producer) {
        this.producer = producer;
    }
}
