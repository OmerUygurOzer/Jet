package com.omerozer.jetcore;

import java.util.*;

/**
 * Created by omerozer on 1/27/18.
 */

class JetDock {

    private Map<String, ArrayList<EventHandler>> jetEventMap;

    private JetFactoryInterface jetFactory;
    private JetEventIndexInterface jetEventIndex;

    JetDock(JetClassLoader jetClassLoader) {
        this.jetEventMap = new HashMap<String, ArrayList<EventHandler>>();
        this.jetFactory = new JetBusFactory(jetClassLoader);
        this.jetEventIndex = jetClassLoader.createEventIndex();
    }

    void dock(Object object) {
        EventHandler handler = jetFactory.create(object);
        for (String event : handler.getEvents()) {
            if (!jetEventMap.containsKey(event)) {
                jetEventMap.put(event, new ArrayList<EventHandler>());
            }
            jetEventMap.get(event).add(handler);
        }
    }

    void undock(Object object){
        for(String event : jetEventIndex.getEventsForParent(object)){
            Iterator<EventHandler> handlerIterator = jetEventMap.get(event).iterator();
            while (handlerIterator.hasNext()){
                handlerIterator.next().equals(object);
                handlerIterator.remove();
            }
        }
    }

    void fire(String event, Object message) {
        for (EventHandler handler : jetEventMap.get(event)) {
            handler.handleSuccess(event, message);
        }
    }

    void error(String event, Object message) {
        for (EventHandler handler : jetEventMap.get(event)) {
            handler.handleError(event, message);
        }
    }
}
